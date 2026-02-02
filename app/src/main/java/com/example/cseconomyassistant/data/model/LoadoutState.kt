package com.example.cseconomyassistant.data.model

data class LoadoutState(
    val pistols: List<Weapon?> = List(5) { null },
    val midTier: List<Weapon?> = List(5) { null },
    val rifles: List<Weapon?> = List(5) { null }
) {
    fun allSelectedWeapons(): List<Weapon> =
        (pistols + midTier + rifles).filterNotNull()

}


