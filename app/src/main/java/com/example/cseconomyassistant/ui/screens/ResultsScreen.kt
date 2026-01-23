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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cseconomyassistant.data.model.EquipmentSlot
import com.example.cseconomyassistant.ui.components.homeScreen.ScreenTitle
import com.example.cseconomyassistant.ui.viewmodel.RoundViewModel

@Composable
fun ResultsScreen(
    navController: NavController,
    roundViewModel: RoundViewModel,
    onBack: () -> Unit
) {
    val recommendation = roundViewModel.calculateBuy()
    val prediction = roundViewModel.calculatePrediction()

    if (recommendation == null || prediction == null) {
        Text("No data received")
        return
    }

    val equipment = recommendation.equipment

    // -----------------------------
    // 🛡 Armor detection
    // -----------------------------
    val hasKevlar = equipment.any {
        it.name.contains("kevlar", ignoreCase = true)
    }

    val hasHelmet = equipment.any {
        it.name.contains("helmet", ignoreCase = true)
    }

    val armorText = when {
        hasKevlar && hasHelmet -> "Kevlar + Helmet"
        hasKevlar -> "Kevlar"
        else -> "No armor"
    }

    // -----------------------------
    // 💣 Utility (grenades)
    // -----------------------------
    val grenades = equipment.filter {
        it.equipmentSlot == EquipmentSlot.UTILITY
    }

    val utilityCost = grenades.sumOf { it.price }

    // -----------------------------
    // 🧰 Defuse kit (CT only)
    // -----------------------------
    val hasDefuseKit = equipment.any {
        it.name.contains("defuse", ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        ScreenTitle(
            title = "Loadout Recommendation",
            subtitle = "Based on your round setup"
        )

        // -----------------------------
        // 🛒 Buy summary
        // -----------------------------
        Text(
            text = "Buy type: ${recommendation.buyType}",
            fontSize = 18.sp
        )

        Text(
            text = "Weapon: ${recommendation.weapon?.name ?: "Starting pistol"}"
        )

        Text("Armor: $armorText")

        if (hasDefuseKit) {
            Text("Defuse Kit: Yes")
        }

        // -----------------------------
        // 💣 Utility display
        // -----------------------------
        if (grenades.isNotEmpty()) {
            Text("Utility:")

            grenades.forEach { grenade ->
                Text("• ${grenade.name} ($${grenade.price})")
            }

            Text("Utility total cost: $$utilityCost")
        } else {
            Text("Utility: None")
        }

        // -----------------------------
        // 💰 Money summary
        // -----------------------------
        Text("Total spend: $${recommendation.totalCost}")
        Text("Remaining money: $${recommendation.remainingMoney}")

        // -----------------------------
        // 🔮 Prediction
        // -----------------------------
        Text(
            text = "Next round prediction (worst case)",
            fontSize = 16.sp
        )

        Text("If you lose: minimum $${prediction.minMoneyIfLose}")
        Text("If you win: minimum $${prediction.minMoneyIfWin}")

        // -----------------------------
        // 🔙 Navigation
        // -----------------------------
        Button(
            onClick = { onBack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}
