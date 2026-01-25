package com.example.cseconomyassistant.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.cseconomyassistant.data.database.weapons
import com.example.cseconomyassistant.data.model.LoadoutState
import kotlinx.coroutines.flow.first
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore by preferencesDataStore(name = "loadouts")

class LoadoutDataStore(private val context: Context) {

    private val ctKey = stringPreferencesKey("ct_loadout")
    private val tKey = stringPreferencesKey("t_loadout")

    suspend fun saveCtLoadout(loadout: LoadoutState) {
        context.dataStore.edit { prefs ->
            prefs[ctKey] = encode(loadout)
        }
    }

    suspend fun saveTLoadout(loadout: LoadoutState) {
        context.dataStore.edit { prefs ->
            prefs[tKey] = encode(loadout)
        }
    }

    suspend fun loadCtLoadout(): LoadoutState? {
        val prefs = context.dataStore.data.first()
        return prefs[ctKey]?.let { decode(it) }
    }

    suspend fun loadTLoadout(): LoadoutState? {
        val prefs = context.dataStore.data.first()
        return prefs[tKey]?.let { decode(it) }
    }

    private fun encode(loadout: LoadoutState): String {
        val ids = listOf(
            loadout.pistols.map { it?.id },
            loadout.midTier.map { it?.id },
            loadout.rifles.map { it?.id }
        )
        return Json.encodeToString(ids)
    }

    private fun decode(raw: String): LoadoutState {
        val ids: List<List<String?>> = Json.decodeFromString(raw)

        fun map(list: List<String?>) =
            list.map { id ->
                id?.let { weapons.firstOrNull { w -> w.id == it } }
            }

        return LoadoutState(
            pistols = map(ids.getOrNull(0) ?: List(5) { null }),
            midTier = map(ids.getOrNull(1) ?: List(5) { null }),
            rifles = map(ids.getOrNull(2) ?: List(5) { null })
        )
    }
}
