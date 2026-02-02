package com.example.cseconomyassistant.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cseconomyassistant.data.model.Equipment
import com.example.cseconomyassistant.data.model.EquipmentCategory
import com.example.cseconomyassistant.data.model.Weapon
import com.example.cseconomyassistant.ui.components.equipmentScreen.EquipmentSection
import com.example.cseconomyassistant.ui.components.homeScreen.ScreenTitle
import com.example.cseconomyassistant.ui.components.equipmentScreen.EquipmentSelection
import com.example.cseconomyassistant.ui.components.equipmentScreen.WeaponSection

@Composable
fun EquipmentScreen(
    onWeaponClick: (Weapon) -> Unit,
    onEquipmentClick: (Equipment) -> Unit
) {
    var equipmentCategory by remember { mutableStateOf(EquipmentCategory.WEAPONS) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ScreenTitle(
            title = "Equipment",
            subtitle = "Weapons, utility and gear overview"
        )
        EquipmentSelection(
            equipmentCategory = equipmentCategory,
            onEquipmentSelected = { newEquipmentCategory ->
                equipmentCategory = newEquipmentCategory
            }
        )

        Box(modifier = Modifier.weight(1f)) {
            when (equipmentCategory) {
                EquipmentCategory.WEAPONS -> WeaponSection(onWeaponClick = onWeaponClick)
                EquipmentCategory.EQUIPMENT -> EquipmentSection(onEquipmentClick = onEquipmentClick)
            }
        }
    }
}