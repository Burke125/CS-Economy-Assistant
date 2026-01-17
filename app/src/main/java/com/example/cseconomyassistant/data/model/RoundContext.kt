package com.example.cseconomyassistant.data.model

data class RoundContext(
    val side: Side,
    val isPistolRound: Boolean,
    val currentMoney: Int,
    val lossStreak: Int,
    val savedWeapon: Weapon?,
    val teamAverageMoney: Int
)

