package com.example.cseconomyassistant.data.model

import com.example.cseconomyassistant.data.database.weapons

@kotlinx.serialization.Serializable
data class PersistedLoadout(
    val pistols: List<String?>,
    val midTier: List<String?>,
    val rifles: List<String?>
)

@kotlinx.serialization.Serializable
data class PersistedLoadouts(
    val ct: PersistedLoadout,
    val t: PersistedLoadout
)

fun LoadoutState.toPersisted() = PersistedLoadout(
    pistols = pistols.map { it?.id },
    midTier = midTier.map { it?.id },
    rifles = rifles.map { it?.id }
)

fun PersistedLoadout.toLoadoutState(): LoadoutState {
    fun map(ids: List<String?>): List<Weapon?> =
        ids.map { id -> weapons.firstOrNull { it.id == id } }

    return LoadoutState(
        pistols = map(pistols),
        midTier = map(midTier),
        rifles = map(rifles)
    )
}