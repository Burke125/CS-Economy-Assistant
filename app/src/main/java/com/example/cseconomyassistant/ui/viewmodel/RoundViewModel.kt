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
        reloadLoadouts()
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

    // ----------------------------------------------------
    // LOADOUT HELPERS
    // ----------------------------------------------------

    private fun weaponsFromLoadout(side: Side): List<Weapon> {
        val state = if (side == Side.CT) ctLoadout.value else tLoadout.value
        return (state?.pistols.orEmpty() +
                state?.midTier.orEmpty() +
                state?.rifles.orEmpty())
            .filterNotNull()
    }

    private fun startingPistol(side: Side): Weapon? {
        val state = if (side == Side.CT) ctLoadout.value else tLoadout.value
        return state?.pistols?.firstOrNull()
    }

    // ----------------------------------------------------
    // EQUIPMENT HELPERS
    // ----------------------------------------------------

    private fun equipmentForSide(side: Side): List<Equipment> =
        equipment.filter { it.side == side || it.side == Side.BOTH }

    private fun isKevlar(e: Equipment) =
        e.name.contains("kevlar", true)

    private fun isHelmet(e: Equipment) =
        e.name.contains("helmet", true)

    private fun isDefuse(e: Equipment) =
        e.name.contains("defuse", true)

    private fun isDecoy(e: Equipment) =
        e.name.contains("decoy", true)

    // ----------------------------------------------------
    // SMART WEAPON SELECTION
    // ----------------------------------------------------

    private fun selectWeapon(
        money: Int,
        buyType: BuyType,
        side: Side,
        savedWeapon: Weapon?,
        useSavedWeapon: Boolean
    ): Weapon? {

        if (useSavedWeapon && savedWeapon != null) return savedWeapon

        val available = weaponsFromLoadout(side)
            .filter { it.price <= money }

        if (available.isEmpty()) return null

        val minReserveForFullBuy = 1500 // armor + 2 utility

        return when (buyType) {

            BuyType.FULL_BUY -> {
                available
                    .filter { it.type == WeaponType.RIFLE }
                    .filter {
                        // penaliziraj AUG / SG
                        !it.name.contains("aug", true) &&
                                !it.name.contains("sg", true)
                    }
                    .filter { money - it.price >= minReserveForFullBuy }
                    .maxByOrNull { it.price }
                    ?: available
                        .filter { it.type == WeaponType.SNIPER }
                        .filter { money - it.price >= 2000 }
                        .maxByOrNull { it.price }
            }

            BuyType.FORCE_BUY -> {
                available
                    .filter {
                        it.type == WeaponType.RIFLE ||
                                it.type == WeaponType.SMG ||
                                it.type == WeaponType.PISTOL
                    }
                    .maxByOrNull { it.price }
            }

            BuyType.ECO -> {
                available
                    .filter { it.type == WeaponType.PISTOL }
                    .maxByOrNull { it.price }
            }
        }
    }

    // ----------------------------------------------------
    // MAIN BUY CALCULATION
    // ----------------------------------------------------

    fun calculateBuy(): BuyRecommendation? {
        val context = roundContext.value ?: return null
        val side = context.side

        val loadout = if (side == Side.CT) ctLoadout.value else tLoadout.value
        if (loadout == null) return null

        var moneyLeft = context.currentMoney
        val teamAvg = context.teamAverageMoney

        val buyType = when {
            teamAvg < 3000 && moneyLeft < 5000 -> BuyType.ECO
            moneyLeft >= 4000 && teamAvg >= 4000 -> BuyType.FULL_BUY
            moneyLeft >= 2000 -> BuyType.FORCE_BUY
            else -> BuyType.ECO
        }

        // -----------------
        // WEAPON
        // -----------------
        val weapon = selectWeapon(
            moneyLeft,
            buyType,
            side,
            context.savedWeapon,
            context.useSavedWeapon
        ) ?: startingPistol(side)

        if (weapon != null && weapon != context.savedWeapon) {
            moneyLeft -= weapon.price
        }

        val boughtEquipment = mutableListOf<Equipment>()

        // -----------------
        // ARMOR
        // -----------------
        // -----------------
// ARMOR (FIXED)
// -----------------
        val armor = equipmentForSide(side)
            .filter { it.equipmentSlot == EquipmentSlot.NONE }

        val kevlarOnly = armor.firstOrNull {
            isKevlar(it) && !isHelmet(it)
        }

        val kevlarHelmet = armor.firstOrNull {
            isKevlar(it) && isHelmet(it)
        }

        if (kevlarHelmet != null && moneyLeft >= kevlarHelmet.price) {
            boughtEquipment += kevlarHelmet
            moneyLeft -= kevlarHelmet.price
        } else if (kevlarOnly != null && moneyLeft >= kevlarOnly.price) {
            boughtEquipment += kevlarOnly
            moneyLeft -= kevlarOnly.price
        }

        // -----------------
        // UTILITY (T & CT)
        // -----------------
        val grenades = equipmentForSide(side)
            .filter {
                it.equipmentSlot == EquipmentSlot.UTILITY &&
                        !isDecoy(it)
            }

        val orderedUtility = listOf(
            grenades.firstOrNull { it.name.contains("flash", true) },
            grenades.firstOrNull { it.name.contains("smoke", true) },
            grenades.firstOrNull { it.name.contains("he", true) },
            grenades.firstOrNull {
                it.name.contains("molotov", true) ||
                        it.name.contains("incendiary", true)
            }
        ).filterNotNull()

        for (g in orderedUtility) {
            if (moneyLeft >= g.price) {
                boughtEquipment += g
                moneyLeft -= g.price
            }
        }

        // -----------------
        // DEFUSE (CT LAST)
        // -----------------
        if (side == Side.CT && buyType != BuyType.ECO) {
            val defuse = armor.firstOrNull { isDefuse(it) }
            if (defuse != null && moneyLeft >= defuse.price) {
                boughtEquipment += defuse
                moneyLeft -= defuse.price
            }
        }

        return BuyRecommendation(
            buyType = buyType,
            weapon = weapon,
            equipment = boughtEquipment,
            totalCost = context.currentMoney - moneyLeft,
            remainingMoney = moneyLeft
        )
    }

    fun calculatePrediction(): RoundPrediction? {
        val buy = calculateBuy() ?: return null

        val lossBonus = when (roundContext.value?.lossStreak) {
            0 -> 1400
            1 -> 1900
            2 -> 2400
            3 -> 2900
            else -> 3400
        }

        return RoundPrediction(
            minMoneyIfLose = buy.remainingMoney + lossBonus,
            minMoneyIfWin = buy.remainingMoney + 3250
        )
    }
}
