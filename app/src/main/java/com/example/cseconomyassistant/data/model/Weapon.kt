package com.example.cseconomyassistant.data.model

import androidx.annotation.DrawableRes

data class Weapon(
    @DrawableRes val image: Int,
    val id: String,
    val name: String,
    val description: String,
    val price: Int,
    val type: WeaponType,
    val equipmentSlot: EquipmentSlot,
    val side: Side,
    val magazineSize: Int,
    val ammoReserve: Int,
    val damage: Int,
    val headshotMultiplier: Float,
    val killAward: Int
)
