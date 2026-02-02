package com.example.cseconomyassistant.data.model

import androidx.annotation.DrawableRes

data class GameMap(
    val images: List<MapVisual>,
    val gallery: List<Int>,
    val id: String,
    val name: String,
    val ctWinRate: Float,
    val tWinRate: Float,
    val description: String,
    val location: String,
    val tip: String
)
data class MapVisual(
    val label: String,
    @DrawableRes val image: Int
)
