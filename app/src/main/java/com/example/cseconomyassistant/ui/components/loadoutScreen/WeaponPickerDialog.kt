package com.example.cseconomyassistant.ui.components.loadoutScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cseconomyassistant.data.model.Weapon

@Composable
fun WeaponPickerDialog(
    weapons: List<Weapon>,
    isWeaponDisabled: (Weapon) -> Boolean,
    onDismiss: () -> Unit,
    onWeaponSelected: (Weapon) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        title = { Text("Select weapon") },
        text = {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(weapons.size) { index ->
                    val weapon = weapons[index]
                    val disabled = isWeaponDisabled(weapon)

                    Text(
                        text = weapon.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !disabled) {
                                onWeaponSelected(weapon)
                                onDismiss()
                            }
                            .padding(12.dp),
                        color = if (disabled) androidx.compose.ui.graphics.Color.Gray
                        else androidx.compose.ui.graphics.Color.White
                    )
                }
            }
        }
    )
}
