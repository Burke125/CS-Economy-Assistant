package com.example.cseconomyassistant.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.data.database.gameMaps
import com.example.cseconomyassistant.data.model.GameMap
import com.example.cseconomyassistant.ui.components.mapScreen.MapImageCarousel
import com.example.cseconomyassistant.ui.theme.TextPrimary

@Composable
fun MapDetailScreen(
    gameMap: GameMap,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = gameMap.name,
            fontSize = 24.sp,
            color = TextPrimary
        )

        MapImageCarousel(
            visuals = gameMap.images,
            modifier = Modifier
        )

        Text(
            text = "Description:\n${gameMap.description}",
            color = TextPrimary,
            fontSize = 16.sp,
            lineHeight = 22.sp
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "CT winrate: ${gameMap.ctWinRate}%", color = TextPrimary, fontSize = 16.sp)
            Text(text = "T winrate: ${gameMap.tWinRate}%", color = TextPrimary, fontSize = 16.sp)
            Text(text = "Location: ${gameMap.location}", color = TextPrimary, fontSize = 16.sp)
        }

        Text(
            text = "Tip:\n${gameMap.tip}",
            color = TextPrimary,
            fontSize = 16.sp,
            lineHeight = 22.sp
        )

        Button(
            onClick = { onBack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back")
        }
    }
}

@Preview
@Composable
fun MapDetailScreenPreview(){
    val maps = gameMaps.first()
    MapDetailScreen(
        gameMap = maps,
        onBack = {}
    )
}
