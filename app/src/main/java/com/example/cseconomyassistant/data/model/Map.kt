package com.example.cseconomyassistant.data.model

import androidx.annotation.DrawableRes

data class Map(
    val images: List<MapVisual>,
    val id: String,
    val name: String,
    val ctWinRate: Float,
    val tWinRate: Float,
    val description: String,
    val location: String,
    val tip: String
)
