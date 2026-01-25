package com.example.cseconomyassistant.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cseconomyassistant.data.database.equipment
import com.example.cseconomyassistant.data.model.*
import com.example.cseconomyassistant.data.repository.LoadoutRepository
import kotlinx.coroutines.launch

class RoundViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val loadoutRepository =
        LoadoutRepository(application.applicationContext)
    var roundContext = mutableStateOf<RoundContext?>(null)
        private set

    private val ctLoadout = mutableStateOf<LoadoutState?>(null)
    private val tLoadout = mutableStateOf<LoadoutState?>(null)

    init {
        viewModelScope.launch {
            ctLoadout.value = loadoutRepository.loadCt()
            tLoadout.value = loadoutRepository.loadT()
        }
    }

    fun reloadLoadouts() {
        viewModelScope.launch {
            ctLoadout.value = loadoutRepository.loadCt()
            tLoadout.value = loadoutRepository.loadT()
        }
    }


    fun setContext(context: RoundContext) {
        roundContext.value = context
    }

    fun clearContext() {
        roundContext.value = null
    }

    // -------------------------
    // LOADOUT HELPERS
    // -------------------------

    private fun weaponsFromLoadout(side: Side): List<Weapon> {
        val state = if (side == Side.CT) ctLoadout.value else tLoadout.value

        return (state?.pistols.orEmpty() +
                state?.midTier.orEmpty() +
                state?.rifles.orEmpty())
            .filterNotNull()
    }

    private fun startingPistolFromLoadout(side: Side): Weapon? {
        val state = if (side == Side.CT) ctLoadout.value else tLoadout.value
        return state?.pistols?.firstOrNull()
    }

    // -------------------------
    // EQUIPMENT
    // -------------------------

    private fun equipmentForSide(side: Side): List<Equipment> =
        equipment.filter { it.side == side || it.side == Side.BOTH }

    private fun isKevlar(e: Equipment) =
        e.name.contains("kevlar", ignoreCase = true)

    private fun isHelmet(e: Equipment) =
        e.name.contains("helmet", ignoreCase = true)

    private fun isDefuse(e: Equipment) =
        e.name.contains("defuse", ignoreCase = true)

    private fun selectWeapon(
        money: Int,
        buyType: BuyType,
        side: Side,
        savedWeapon: Weapon?,
        useSavedWeapon: Boolean
    ): Weapon? {

        if (useSavedWeapon && savedWeapon != null) {
            return savedWeapon
        }

        val loadoutWeapons = weaponsFromLoadout(side)
            .filter { it.price <= money }

        return when (buyType) {

            BuyType.FULL_BUY -> {
                loadoutWeapons
                    .filter {
                        it.type == WeaponType.RIFLE ||
                                it.type == WeaponType.SNIPER
                    }
                    .maxByOrNull { it.price }
            }

            BuyType.FORCE_BUY -> {
                loadoutWeapons
                    .filter {
                        it.type == WeaponType.SMG ||
                                it.type == WeaponType.PISTOL
                    }
                    .maxByOrNull { it.price }
            }

            BuyType.ECO -> {
                loadoutWeapons
                    .filter { it.type == WeaponType.PISTOL }
                    .maxByOrNull { it.price }
            }
        }
    }


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

                result += grenades.take(4)
            }

            BuyType.FORCE_BUY -> {
                kevlar?.let { result += it }

                if (side == Side.CT) {
                    defuse?.let { result += it }
                }

                result += grenades.take(2)
            }

            BuyType.ECO -> {
            }
        }

        return result
    }

    // -------------------------
    // MAIN CALCULATION
    // -------------------------

    fun calculateBuy(): BuyRecommendation? {
        val context = roundContext.value ?: return null

        val side = context.side

        // Ako loadout još nije učitan – nemoj računati
        val state = if (side == Side.CT) ctLoadout.value else tLoadout.value
        if (state == null) return null

        val money = context.currentMoney
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
            savedWeapon = savedWeapon,
            useSavedWeapon = context.useSavedWeapon
        ) ?: startingPistolFromLoadout(side)

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
