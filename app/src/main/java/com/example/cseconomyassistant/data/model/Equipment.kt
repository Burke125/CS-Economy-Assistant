package com.example.cseconomyassistant.data.model

import androidx.annotation.DrawableRes

data class Equipment(
    @DrawableRes val image: Int,
    val id: String,
    val name: String,
    val price: Int,
    val side: Side,
    val description: String
)
