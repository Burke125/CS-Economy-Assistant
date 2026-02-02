package com.example.cseconomyassistant.data.model

data class BuyHistoryEntry(
    val timestamp: Long = System.currentTimeMillis(),

    val side: String = "",
    val buyType: String = "",

    val weaponId: String? = null,
    val weaponName: String? = null,

    val armor: String = "",
    val hasDefuse: Boolean = false,

    val utility: List<String> = emptyList(),

    val totalCost: Int = 0,
    val remainingMoney: Int = 0,

    val myMoney: Int = 0,
    val teamMoney: Int = 0,
    val lossStreak: Int = 0
)

