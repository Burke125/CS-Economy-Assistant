package com.example.cseconomyassistant.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cseconomyassistant.ui.components.homeScreen.ScreenTitle
import com.example.cseconomyassistant.ui.components.homeScreen.SideSelection
import com.example.cseconomyassistant.ui.components.loadoutScreen.CTLoadoutSection
import com.example.cseconomyassistant.ui.components.loadoutScreen.TLoadoutSection
import com.example.cseconomyassistant.ui.viewmodel.LoadoutViewModel
import com.example.cseconomyassistant.data.model.Side
import com.example.cseconomyassistant.ui.theme.BorderSubtle
import com.example.cseconomyassistant.ui.theme.TextPrimary
import com.example.cseconomyassistant.ui.viewmodel.RoundViewModel
import kotlinx.coroutines.launch


@Composable
fun LoadoutScreen(
    loadoutViewModel: LoadoutViewModel = viewModel(),
    roundViewModel: RoundViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val selectedSide = loadoutViewModel.selectedSide.value
    val loadout = loadoutViewModel.currentLoadout()

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    containerColor = BorderSubtle,
                    contentColor = TextPrimary,
                    actionColor = TextPrimary,
                    shape = RoundedCornerShape(12.dp),
                    snackbarData = data
                )
            }
        }
    ) { padding ->

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
                onSideSelected = { loadoutViewModel.changeSide(it) }
            )

            when (selectedSide) {
                Side.CT -> {
                    CTLoadoutSection(
                        loadout = loadout,
                        viewModel = loadoutViewModel,
                        onLoadoutChange = {
                            loadoutViewModel.updateLoadout(it)
                        }
                    )
                }

                Side.T -> {
                    TLoadoutSection(
                        loadout = loadout,
                        viewModel = loadoutViewModel,
                        onLoadoutChange = {
                            loadoutViewModel.updateLoadout(it)
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
                        scope.launch {
                            try {
                                loadoutViewModel.saveLoadouts()
                                roundViewModel.reloadLoadouts()
                                snackbarHostState.showSnackbar(
                                    message = "Loadouts saved",
                                    duration = SnackbarDuration.Short,
                                    actionLabel = "X"
                                )

                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar(
                                    message = "Failed to save loadout",
                                    duration = SnackbarDuration.Short,
                                    actionLabel = "X"
                                )
                            }
                        }
                    }
                ) {
                    Text("Save loadouts")
                }

                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        loadoutViewModel.resetLoadouts()
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Loadouts reset",
                                duration = SnackbarDuration.Short,
                                actionLabel = "X"
                            )
                        }
                    }
                ) {
                    Text("Reset to default")
                }
            }
        }
    }
}
