package com.example.cseconomyassistant.ui.components.loadoutScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cseconomyassistant.data.model.LoadoutClass
import com.example.cseconomyassistant.data.model.LoadoutState
import com.example.cseconomyassistant.ui.viewmodel.LoadoutViewModel

@Composable
fun TLoadoutSection(
    loadout: LoadoutState,
    viewModel: LoadoutViewModel,
) {

    var pickerState by remember {
        mutableStateOf<Pair<LoadoutClass, Int>?>(null)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text("Starting Pistol")

        LoadoutSlotCard(
            title = "Starting Pistol (Glock-18)",
            weapon = loadout.pistols[0],
            enabled = false,
            onClick = {}
        )

        Text("Other Pistols")

        repeat(4) { index ->
            val slotIndex = index + 1

            LoadoutSlotCard(
                title = "Pistol Slot $slotIndex",
                weapon = loadout.pistols[slotIndex],
                onClick = {
                    pickerState = LoadoutClass.PISTOLS to slotIndex
                }
            )
        }

        Text("Mid-Tier")

        repeat(5) { index ->
            LoadoutSlotCard(
                title = "Mid Tier Slot ${index + 1}",
                weapon = loadout.midTier[index],
                onClick = {
                    pickerState = LoadoutClass.MID_TIER to index
                }
            )
        }

        Text("Rifles")

        repeat(5) { index ->
            LoadoutSlotCard(
                title = "Rifle Slot ${index + 1}",
                weapon = loadout.rifles[index],
                onClick = {
                    pickerState = LoadoutClass.RIFLES to index
                }
            )
        }
    }

    pickerState?.let { (clazz, index) ->

        val weapons = viewModel.getWeaponsForSlot(clazz, index)

        WeaponPickerDialog(
            weapons = weapons,
            isWeaponDisabled = { weapon ->
                viewModel.isWeaponAlreadyUsed(weapon)
            },
            onDismiss = { pickerState = null },
            onWeaponSelected = { weapon ->
                viewModel.replaceWeapon(clazz, index, weapon)
            }
        )
    }
}
