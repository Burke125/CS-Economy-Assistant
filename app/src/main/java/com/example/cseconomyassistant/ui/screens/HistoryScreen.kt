package com.example.cseconomyassistant.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cseconomyassistant.data.model.BuyHistoryEntry
import com.example.cseconomyassistant.data.repository.HistoryRepository
import com.example.cseconomyassistant.ui.components.historyScreen.HistoryCard
import com.example.cseconomyassistant.ui.components.homeScreen.ScreenTitle

@Composable
fun HistoryScreen() {

    val repository = remember { HistoryRepository() }
    var history by remember {
        mutableStateOf<List<Pair<String, BuyHistoryEntry>>>(emptyList())
    }

    LaunchedEffect(Unit) {
        repository.observeHistory {
            history = it
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ScreenTitle(
            title = "Loadout Recommendation History",
            subtitle = "View your previous round setups"
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = history,
                key = { it.first }
            ) { (id, entry) ->
                HistoryCard(
                    id = id,
                    entry = entry,
                    onDelete = { repository.delete(it) }
                )
            }

        }
    }
}
