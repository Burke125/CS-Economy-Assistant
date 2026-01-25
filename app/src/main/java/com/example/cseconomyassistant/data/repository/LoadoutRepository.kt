package com.example.cseconomyassistant.data.repository

import android.content.Context
import com.example.cseconomyassistant.data.datastore.LoadoutDataStore
import com.example.cseconomyassistant.data.model.LoadoutState

class LoadoutRepository(context: Context) {

    private val loadoutDataStore = LoadoutDataStore(context)

    suspend fun save(ct: LoadoutState, t: LoadoutState) {
        loadoutDataStore.saveCtLoadout(ct)
        loadoutDataStore.saveTLoadout(t)
    }

    suspend fun loadCt(): LoadoutState? {
        return loadoutDataStore.loadCtLoadout()
    }

    suspend fun loadT(): LoadoutState? {
        return loadoutDataStore.loadTLoadout()
    }
}
