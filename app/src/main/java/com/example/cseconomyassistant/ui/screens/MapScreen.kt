package com.example.cseconomyassistant.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cseconomyassistant.ui.components.homeScreen.ScreenTitle
import com.example.cseconomyassistant.ui.components.mapScreen.MapSection
import com.example.cseconomyassistant.ui.navigation.Screen

@Composable
fun MapScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ScreenTitle(
            title = "Maps",
            subtitle = "Layouts, win rates and tips"
        )

        MapSection(
            onMapClick = { gameMap ->
                navController.navigate(
                    Screen.MapDetail.createRoute(gameMap.id)
                )
            }
        )
    }
}