package com.example.cseconomyassistant.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cseconomyassistant.data.model.Side
import com.example.cseconomyassistant.data.model.Weapon
import com.example.cseconomyassistant.ui.components.homeScreen.LossStreakInput
import com.example.cseconomyassistant.ui.components.homeScreen.MoneyInput
import com.example.cseconomyassistant.ui.components.homeScreen.PistolRoundToggle
import com.example.cseconomyassistant.ui.components.homeScreen.SavedWeaponDropdown
import com.example.cseconomyassistant.ui.components.homeScreen.ScreenTitle
import com.example.cseconomyassistant.ui.components.homeScreen.SideSelection

@Composable
fun HomeScreen(navController: NavController) {
    Modifier.padding(15.dp)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ScreenTitle("Round Setup", "Configure your current round context")

        var selectedSide by remember { mutableStateOf(Side.CT) }
        SideSelection(
            selectedSide = selectedSide,
            onSideSelected = { newSide -> selectedSide = newSide }
        )

        var isPistolRound by remember { mutableStateOf(false)}
        PistolRoundToggle(
            isPistolRound = isPistolRound,
            onToggle = { isPistolRound = it }
        )

        var currentMoney by remember { mutableIntStateOf(800) }
        MoneyInput(
            currentMoney = currentMoney,
            onMoneyChange = { newMoney -> currentMoney = newMoney }
        )

        var lossStreak by remember { mutableIntStateOf(0) }
        LossStreakInput(
            selectedLoss = lossStreak,
            onLossSelected = { lossStreak = it }
        )

        var hasSavedWeapon by remember { mutableStateOf(false) }
        var savedWeapon by remember { mutableStateOf<Weapon?>(null) }

        SavedWeaponDropdown(
            hasSavedWeapon = hasSavedWeapon,
            selectedWeapon = savedWeapon,
            onSavedWeaponToggle = { hasSavedWeapon = it },
            onWeaponSelected = { savedWeapon = it }
        )

        Button(
            onClick = { navController.navigate("calculator_results") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Loadout")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()

    com.example.cseconomyassistant.ui.theme.CSEconomyAssistantTheme(darkTheme = true) {
        HomeScreen(navController = navController)
    }
}