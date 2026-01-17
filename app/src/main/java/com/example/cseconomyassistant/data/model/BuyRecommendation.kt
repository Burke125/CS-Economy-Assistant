package com.example.cseconomyassistant.data.model

data class BuyRecommendation(
    val buyType: BuyType,
    val weapon: Weapon?,
    val equipment: List<Equipment>,
    val totalCost: Int,
    val remainingMoney: Int
)

