package com.example.cseconomyassistant.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.cseconomyassistant.data.database.equipment
import com.example.cseconomyassistant.data.model.*
import com.example.cseconomyassistant.data.repository.HistoryRepository
import com.example.cseconomyassistant.data.repository.LoadoutRepository
import kotlinx.coroutines.launch

class RoundViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val minTFullBuy = 3300
    private val minCTFullBuy = 3500

    private val ecoMaxSpend = 0

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
/*
    fun clearContext() {
        roundContext.value = null
    }
*/
    private fun weaponsFromLoadout(side: Side): List<Weapon> {
        val state = if (side == Side.CT) ctLoadout.value else tLoadout.value

        return (state?.pistols.orEmpty()
                + state?.midTier.orEmpty()
                + state?.rifles.orEmpty())
            .filterNotNull()
    }

    private fun startingPistol(side: Side): Weapon? {
        val state = if (side == Side.CT) ctLoadout.value else tLoadout.value
        return state?.pistols?.firstOrNull()
    }

    private fun isPistolRound(context: RoundContext): Boolean {
        return context.isPistolRound
    }

    private fun equipmentForSide(side: Side): List<Equipment> =
        equipment.filter { it.side == side || it.side == Side.BOTH }

    private fun isKevlar(e: Equipment) =
        e.name.contains("kevlar", true) &&
                !e.name.contains("helmet", true)

    private fun isHelmet(e: Equipment) =
        e.name.contains("helmet", true)

    private fun isDefuse(e: Equipment) =
        e.name.contains("defuse", true)

    private fun isDecoy(e: Equipment) =
        e.name.contains("decoy", true)

    private fun determineBuyType(
        money: Int,
        teamAverage: Int,
        lossStreak: Int,
        side: Side
    ): BuyType {

        val minFullBuy =
            if (side == Side.T) minTFullBuy else minCTFullBuy

        val lossBonus = when (lossStreak) {
            0 -> 1400
            1 -> 1900
            2 -> 2400
            3 -> 2900
            else -> 3400
        }

        val nextRoundMoneyIfLose = money + lossBonus

        val canFullBuyNow =
            money >= minFullBuy && teamAverage >= minFullBuy

        val canFullBuyNextRound =
            nextRoundMoneyIfLose > minFullBuy && (teamAverage + lossBonus) > minFullBuy

        return when {

            canFullBuyNow ->
                BuyType.FULL_BUY

            canFullBuyNextRound ->
                BuyType.HALF_BUY

            lossStreak >= 3 && money >= 1500 ->
                BuyType.FORCE_BUY

            else ->
                BuyType.ECO
        }
    }

    private fun selectWeapon(
        money: Int,
        maxSpend: Int,
        buyType: BuyType,
        side: Side,
        savedWeapon: Weapon?,
        useSavedWeapon: Boolean
    ): Weapon? {

        if (useSavedWeapon && savedWeapon != null) {
            return savedWeapon
        }

        val available = weaponsFromLoadout(side)
            .filter { it.price <= money }
            .filter { it.price <= maxSpend }

        if (available.isEmpty()) return null

        return when (buyType) {

            BuyType.FULL_BUY -> {
                available
                    .filter { it.type == WeaponType.RIFLE }
                    .sortedWith(
                        compareBy<Weapon> {
                            it.name.contains("aug", true) ||
                                    it.name.contains("sg", true)
                        }.thenByDescending { it.price }
                    )
                    .firstOrNull()
            }

            BuyType.FORCE_BUY -> {
                available
                    .filter {
                        it.type == WeaponType.RIFLE ||
                                it.type == WeaponType.SMG ||
                                it.type == WeaponType.SHOTGUN
                    }
                    .maxByOrNull { it.price }
            }

            BuyType.HALF_BUY -> {
                available
                    .filter {
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

    fun calculateBuy(): BuyRecommendation? {
        val context = roundContext.value ?: return null
        val side = context.side

        val loadout = if (side == Side.CT) ctLoadout.value else tLoadout.value
        if (loadout == null) return null

        var moneyLeft = context.currentMoney


        val lossBonus = when (context.lossStreak) {
            0 -> 1400
            1 -> 1900
            2 -> 2400
            3 -> 2900
            else -> 3400
        }

        val buyType = determineBuyType(
            money = context.currentMoney,
            teamAverage = context.teamAverageMoney,
            lossStreak = context.lossStreak,
            side = side
        )

        val minFullBuy =
            if (side == Side.T) minTFullBuy else minCTFullBuy

        val maxSpend = when (buyType) {

            BuyType.ECO -> {
                if (isPistolRound(context)) {
                    moneyLeft
                } else {
                    ecoMaxSpend
                }
            }

            BuyType.HALF_BUY ->
                moneyLeft + lossBonus - minFullBuy

            else ->
                moneyLeft
        }

        val boughtEquipment = mutableListOf<Equipment>()

        if (buyType != BuyType.ECO) {
            val kevlar = equipmentForSide(side).firstOrNull { isKevlar(it) }
            val helmet = equipmentForSide(side).firstOrNull { isHelmet(it) }

            if (
                kevlar != null &&
                moneyLeft >= kevlar.price
            ) {
                boughtEquipment += kevlar
                moneyLeft -= kevlar.price

                if(buyType == BuyType.FULL_BUY){
                    if (
                        helmet != null &&
                        moneyLeft >= helmet.price &&
                        (context.currentMoney - moneyLeft + (helmet.price - kevlar.price)) <= maxSpend
                    ) {
                        boughtEquipment += helmet
                        moneyLeft -= (helmet.price - kevlar.price)
                    }
                }
            }
        }

        val weapon = selectWeapon(
            money = moneyLeft,
            maxSpend = maxSpend,
            buyType = buyType,
            side = side,
            savedWeapon = context.savedWeapon,
            useSavedWeapon = context.useSavedWeapon
        ) ?: startingPistol(side)

        if (
            weapon != null &&
            !(context.useSavedWeapon && weapon == context.savedWeapon) &&
            !(weapon.name.contains("USP-S") || weapon.name.contains("P2000")  ||
            weapon.name.contains("Glock-18"))
        ) {
            moneyLeft -= weapon.price
        }

        val grenades = equipmentForSide(side)
            .filter {
                it.equipmentSlot == EquipmentSlot.UTILITY &&
                        !isDecoy(it)
            }
            .sortedBy {
                when {
                    it.name.contains("flash", true) -> 0
                    it.name.contains("smoke", true) -> 1
                    it.name.contains("he", true) -> 2
                    else -> 3
                }
            }

        for (g in grenades) {
            if (
                moneyLeft >= g.price &&
                (context.currentMoney - moneyLeft + g.price) <= maxSpend
            ) {
                boughtEquipment += g
                moneyLeft -= g.price
            }
        }

        if (side == Side.CT && buyType != BuyType.ECO ) {
            val defuse = equipmentForSide(side).firstOrNull { isDefuse(it) }
            if (defuse != null && moneyLeft >= defuse.price && (context.currentMoney - moneyLeft + defuse.price) <= maxSpend) {
                boughtEquipment += defuse
                moneyLeft -= defuse.price
            }
        }

        val totalCost = context.currentMoney - moneyLeft

        return BuyRecommendation(
            buyType = buyType,
            weapon = weapon,
            equipment = boughtEquipment,
            totalCost = totalCost,
            remainingMoney = moneyLeft
        )
    }

    fun calculatePrediction(): RoundPrediction? {
        val context = roundContext.value ?: return null
        val buy = calculateBuy() ?: return null

        val lossBonus = when (context.lossStreak) {
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

    fun saveToHistory(
        roundViewModel: RoundViewModel,
        recommendation: BuyRecommendation
    ) {
        val context = roundViewModel.roundContext.value ?: return

        val entry = BuyHistoryEntry(
            side = context.side.name,
            buyType = recommendation.buyType.name,

            weaponId = recommendation.weapon?.id,
            weaponName = recommendation.weapon?.name,

            armor = when {
                recommendation.equipment.any { it.name.contains("helmet", true) } ->
                    "Kevlar + Helmet"
                recommendation.equipment.any { it.name.contains("kevlar", true) } ->
                    "Kevlar"
                else -> "None"
            },

            hasDefuse = recommendation.equipment.any {
                it.name.contains("defuse", true)
            },

            utility = recommendation.equipment
                .filter { it.equipmentSlot == EquipmentSlot.UTILITY }
                .map { it.name },

            totalCost = recommendation.totalCost,
            remainingMoney = recommendation.remainingMoney,

            myMoney = context.currentMoney,
            teamMoney = context.teamAverageMoney,
            lossStreak = context.lossStreak
        )

        HistoryRepository().save(entry)
    }

}
