package com.example.cseconomyassistant.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cseconomyassistant.ui.components.homeScreen.ScreenTitle
import com.example.cseconomyassistant.ui.components.homeScreen.SideSelection
import com.example.cseconomyassistant.ui.components.loadoutScreen.CTLoadoutSection
import com.example.cseconomyassistant.ui.components.loadoutScreen.TLoadoutSection
import com.example.cseconomyassistant.ui.viewmodel.LoadoutViewModel
import com.example.cseconomyassistant.data.model.Side

@Composable
fun LoadoutScreen(
    viewModel: LoadoutViewModel = viewModel()
) {
    val selectedSide = viewModel.selectedSide.value
    val loadout = viewModel.currentLoadout()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ScreenTitle(
            title = "Loadout setup",
            subtitle = "Configure your current loadouts"
        )

        SideSelection(
            selectedSide = selectedSide,
            onSideSelected = { viewModel.changeSide(it) }
        )

        when (selectedSide) {
            Side.CT -> {
                CTLoadoutSection(
                    loadout = loadout,
                    viewModel = viewModel,
                    onLoadoutChange = { updated ->
                        viewModel.updateLoadout(updated)
                    }
                )
            }

            Side.T -> {
                TLoadoutSection(
                    loadout = loadout,
                    viewModel = viewModel,
                    onLoadoutChange = { updated ->
                        viewModel.updateLoadout(updated)
                    }
                )
            }

            else -> {}
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.saveCurrentLoadout()
                }
            ) {
                Text("Save Loadout")
            }

            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    viewModel.resetCurrentLoadout()
                }
            ) {
                Text("Reset")
            }
        }
    }
}
