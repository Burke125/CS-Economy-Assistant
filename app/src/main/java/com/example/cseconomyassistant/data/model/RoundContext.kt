package com.example.cseconomyassistant.data.model

data class RoundContext(
    val side: Side,
    val isPistolRound: Boolean,
    val currentMoney: Int,
    val teamAverageMoney: Int,
    val lossStreak: Int,
    val savedWeapon: Weapon?,
    val useSavedWeapon: Boolean
)
