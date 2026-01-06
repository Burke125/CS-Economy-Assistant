package com.example.cseconomyassistant.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cseconomyassistant.data.model.Side
import com.example.cseconomyassistant.data.model.Weapon
import com.example.cseconomyassistant.ui.components.homeScreen.LossStreakInput
import com.example.cseconomyassistant.ui.components.homeScreen.MoneyInputSlider
import com.example.cseconomyassistant.ui.components.homeScreen.PistolRoundToggle
import com.example.cseconomyassistant.ui.components.homeScreen.SavedWeaponDropdown
import com.example.cseconomyassistant.ui.components.homeScreen.ScreenTitle
import com.example.cseconomyassistant.ui.components.homeScreen.SideSelection

@Composable
fun HomeScreen(navController: NavController) {
    var selectedSide by remember { mutableStateOf(Side.CT) }
    var isPistolRound by remember { mutableStateOf(false)}
    var currentMoney by remember { mutableIntStateOf(800) }
    var lossStreak by remember { mutableIntStateOf(0) }
    var hasSavedWeapon by remember { mutableStateOf(false) }
    var savedWeapon by remember { mutableStateOf<Weapon?>(null) }

    val inputsLocked = isPistolRound

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ScreenTitle(
            title = "Round Setup",
            subtitle = "Configure your current round context"
        )

        SideSelection(
            selectedSide = selectedSide,
            onSideSelected = { newSide -> selectedSide = newSide }
        )

        PistolRoundToggle(
            isPistolRound = isPistolRound,
            onToggle = { enabled -> isPistolRound = enabled
                if(enabled){
                    currentMoney = 800
                    lossStreak = 1
                    hasSavedWeapon = false
                    savedWeapon = null
                }else{
                    lossStreak = 0
                }
            }
        )

        MoneyInputSlider(
            currentMoney = currentMoney,
            onMoneyChange = { currentMoney = it },
            locked = inputsLocked
        )

        LossStreakInput(
            selectedLoss = lossStreak,
            onLossSelected = { lossStreak = it },
            locked = inputsLocked
        )

        SavedWeaponDropdown(
            hasSavedWeapon = hasSavedWeapon,
            selectedWeapon = savedWeapon,
            onSavedWeaponToggle = { hasSavedWeapon = it },
            onWeaponSelected = { savedWeapon = it },
            locked = inputsLocked
        )

        Button(
            onClick = { navController.navigate("calculator_results") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Loadout")
        }
    }
}