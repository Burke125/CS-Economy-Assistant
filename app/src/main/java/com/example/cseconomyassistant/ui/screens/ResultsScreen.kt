package com.example.cseconomyassistant.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cseconomyassistant.ui.components.homeScreen.ScreenTitle
import com.example.cseconomyassistant.ui.viewmodel.RoundViewModel
import com.example.cseconomyassistant.ui.components.resultsScreen.ArmorSection
import com.example.cseconomyassistant.ui.components.resultsScreen.DefuseSection
import com.example.cseconomyassistant.ui.components.resultsScreen.UtilitySection
import com.example.cseconomyassistant.ui.components.resultsScreen.WeaponSection
import com.example.cseconomyassistant.ui.theme.BorderSubtle
import com.example.cseconomyassistant.ui.theme.SurfaceVariant


@Composable
fun ResultsScreen(
    roundViewModel: RoundViewModel,
    onBack: () -> Unit
) {
    val recommendation = roundViewModel.calculateBuy()
    val prediction = roundViewModel.calculatePrediction()

    if (recommendation == null || prediction == null) return

    LaunchedEffect(Unit) {
        roundViewModel.saveToHistory(roundViewModel, recommendation)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ScreenTitle(
            title = "Loadout Recommendation",
            subtitle = "Based on your round setup"
        )

        WeaponSection(
            weapon = recommendation.weapon
        )

        ArmorSection(
            equipment = recommendation.equipment
        )

        UtilitySection(
            equipment = recommendation.equipment
        )

        DefuseSection(
            equipment = recommendation.equipment
        )

        Text("Total:")

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SurfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 2.dp,
                    color = BorderSubtle,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Text("Total spend: $${recommendation.totalCost}")
            Text("Remaining money: $${recommendation.remainingMoney}")
        }

        Text("Next round prediction:")

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SurfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 2.dp,
                    color = BorderSubtle,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
        ) {
            Text("If you lose: minimum $${prediction.minMoneyIfLose}")
            Text("If you win: minimum $${prediction.minMoneyIfWin}")
        }

        Button(
            onClick = { onBack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}
