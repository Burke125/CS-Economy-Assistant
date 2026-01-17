package com.example.cseconomyassistant.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cseconomyassistant.data.database.equipment
import com.example.cseconomyassistant.data.database.weapons
import com.example.cseconomyassistant.data.model.*

class RoundViewModel : ViewModel() {

    // -----------------------------
    // STATE
    // -----------------------------

    var roundContext = mutableStateOf<RoundContext?>(null)
        private set

    fun setContext(context: RoundContext) {
        roundContext.value = context
    }

    fun clearContext() {
        roundContext.value = null
    }

    // -----------------------------
    // DATA FILTER HELPERS
    // -----------------------------

    private fun weaponsForSide(side: Side): List<Weapon> =
        weapons.filter { it.side == side || it.side == Side.BOTH }

    private fun equipmentForSide(side: Side): List<Equipment> =
        equipment.filter { it.side == side || it.side == Side.BOTH }

    private fun primaryWeapons(side: Side) =
        weapons.filter {
            it.equipmentSlot == EquipmentSlot.PRIMARY &&
                    (it.side == side || it.side == Side.BOTH)
        }

    private fun pistols(side: Side) =
        weapons.filter {
            it.equipmentSlot == EquipmentSlot.SECONDARY &&
                    (it.side == side || it.side == Side.BOTH)
        }

    private fun utility() =
        equipment.filter { it.equipmentSlot == EquipmentSlot.UTILITY }

    private fun armorAndKits() =
        equipment.filter { it.equipmentSlot == EquipmentSlot.NONE }

    // -----------------------------
    // EQUIPMENT HELPERS
    // -----------------------------

    private fun isKevlar(e: Equipment) =
        e.name.contains("kevlar", ignoreCase = true)

    private fun isHelmet(e: Equipment) =
        e.name.contains("helmet", ignoreCase = true)

    private fun isDefuse(e: Equipment) =
        e.name.contains("defuse", ignoreCase = true)

    private fun startingPistol(side: Side): Weapon? =
        pistols(side).firstOrNull { it.price == 0 }

    // -----------------------------
    // WEAPON SELECTION
    // -----------------------------

    private fun selectWeapon(
        money: Int,
        buyType: BuyType,
        side: Side,
        savedWeapon: Weapon?
    ): Weapon? {

        // Ako igrač već ima weapon – ne kupuj novi
        if (savedWeapon != null) return savedWeapon

        val availableWeapons = weaponsForSide(side)
            .filter { it.price <= money }

        return when (buyType) {

            BuyType.FULL_BUY -> {
                availableWeapons
                    .filter {
                        it.type == WeaponType.RIFLE ||
                                it.type == WeaponType.SNIPER
                    }
                    .maxByOrNull { it.price }
            }

            BuyType.FORCE_BUY -> {
                availableWeapons
                    .filter {
                        it.type == WeaponType.SMG ||
                                it.type == WeaponType.PISTOL
                    }
                    .maxByOrNull { it.price }
            }

            BuyType.ECO -> {
                availableWeapons
                    .filter { it.type == WeaponType.PISTOL }
                    .maxByOrNull { it.price }
            }
        }
    }

    // -----------------------------
    // EQUIPMENT SELECTION
    // -----------------------------

    private fun selectEquipment(
        side: Side,
        buyType: BuyType
    ): List<Equipment> {

        val available = equipmentForSide(side)

        val armorItems = available.filter { it.equipmentSlot == EquipmentSlot.NONE }
        val grenades = available.filter { it.equipmentSlot == EquipmentSlot.UTILITY }

        val kevlar = armorItems.firstOrNull { isKevlar(it) }
        val helmet = armorItems.firstOrNull { isHelmet(it) }
        val defuse = armorItems.firstOrNull { isDefuse(it) }

        val result = mutableListOf<Equipment>()

        when (buyType) {

            BuyType.FULL_BUY -> {
                kevlar?.let { result += it }
                helmet?.let { result += it }

                if (side == Side.CT) {
                    defuse?.let { result += it }
                }

                // Max 4 grenade
                result += grenades.take(4)
            }

            BuyType.FORCE_BUY -> {
                kevlar?.let { result += it }

                if (side == Side.CT) {
                    defuse?.let { result += it }
                }

                // Max 2 grenade
                result += grenades.take(2)
            }

            BuyType.ECO -> {
                // Ne kupujemo ništa
            }
        }

        return result
    }

    // -----------------------------
    // MAIN BUY CALCULATION
    // -----------------------------

    fun calculateBuy(): BuyRecommendation? {
        val context = roundContext.value ?: return null

        val money = context.currentMoney
        val side = context.side
        val savedWeapon = context.savedWeapon

        val teamAverageMoney = context.teamAverageMoney

        val buyType = when {

            teamAverageMoney < 3000 && money < 5000 ->
                BuyType.ECO

            money >= 4000 && teamAverageMoney >= 4000 ->
                BuyType.FULL_BUY

            money >= 3500 && teamAverageMoney < 4000 ->
                BuyType.FORCE_BUY

            money >= 2000 ->
                BuyType.FORCE_BUY

            else ->
                BuyType.ECO
        }

        val selectedWeapon = selectWeapon(
            money = money,
            buyType = buyType,
            side = side,
            savedWeapon = savedWeapon
        ) ?: startingPistol(side)

        val selectedEquipment = selectEquipment(
            side = side,
            buyType = buyType
        )

        val weaponCost =
            if (savedWeapon != null) 0 else (selectedWeapon?.price ?: 0)

        val equipmentCost = selectedEquipment.sumOf { it.price }
        val totalCost = weaponCost + equipmentCost
        val remaining = (money - totalCost).coerceAtLeast(0)

        return BuyRecommendation(
            buyType = buyType,
            weapon = selectedWeapon,
            equipment = selectedEquipment,
            totalCost = totalCost,
            remainingMoney = remaining
        )
    }

    // -----------------------------
    // NEXT ROUND PREDICTION
    // -----------------------------

    fun calculatePrediction(): RoundPrediction? {

        val buy = calculateBuy() ?: return null

        val remainingMoney = buy.remainingMoney

        val lossBonus = when (roundContext.value?.lossStreak) {
            0 -> 1400
            1 -> 1900
            2 -> 2400
            3 -> 2900
            else -> 3400
        }

        val minMoneyIfLose = remainingMoney + lossBonus
        val minMoneyIfWin = remainingMoney + 3250

        return RoundPrediction(
            minMoneyIfLose = minMoneyIfLose,
            minMoneyIfWin = minMoneyIfWin
        )
    }

}
