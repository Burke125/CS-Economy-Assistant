package com.example.cseconomyassistant.ui.components.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.data.database.weapons
import com.example.cseconomyassistant.data.model.Weapon
import com.example.cseconomyassistant.data.model.WeaponType
import com.example.cseconomyassistant.ui.theme.TextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedWeaponDropdown(
    hasSavedWeapon: Boolean,
    selectedWeapon: Weapon?,
    onSavedWeaponToggle: (Boolean) -> Unit,
    onWeaponSelected: (Weapon?) -> Unit
) {
    Column {
        // Toggle row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Saved weapon from last round",
                color = TextPrimary,
                fontSize = 14.sp
            )

            Switch(
                checked = hasSavedWeapon,
                onCheckedChange = {
                    onSavedWeaponToggle(it)
                    if (!it) onWeaponSelected(null)
                }
            )
        }

        // Dropdown only if toggle is ON
        if (hasSavedWeapon) {
            Spacer(modifier = Modifier.height(8.dp))

            // Filter primary weapons only
            val primaryWeapons = weapons.filter {
                it.type != WeaponType.PISTOL &&
                        it.type != WeaponType.OTHER
            }

            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedWeapon?.name ?: "Select saved weapon",
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth().menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    // "None" option
                    DropdownMenuItem(
                        text = { Text("No saved weapon") },
                        onClick = {
                            onWeaponSelected(null)
                            expanded = false
                        }
                    )

                    Divider()

                    // All primary weapons
                    primaryWeapons.forEach { weapon ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(weapon.name)
                                    Spacer(modifier = Modifier.weight(1f))
                                    Text("\$${weapon.price}", fontSize = 12.sp)
                                }
                            },
                            onClick = {
                                onWeaponSelected(weapon)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}