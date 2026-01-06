package com.example.cseconomyassistant.data.model

data class GameMap(
    val images: List<MapVisual>,
    val id: String,
    val name: String,
    val ctWinRate: Float,
    val tWinRate: Float,
    val description: String,
    val location: String,
    val tip: String
)
