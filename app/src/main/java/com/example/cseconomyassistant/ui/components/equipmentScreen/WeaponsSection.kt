package com.example.cseconomyassistant.ui.components.equipmentScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.data.database.weapons
import com.example.cseconomyassistant.data.model.Weapon
import com.example.cseconomyassistant.ui.theme.BorderSubtle
import com.example.cseconomyassistant.ui.theme.SurfaceVariant
import com.example.cseconomyassistant.ui.theme.TextPrimary
import com.example.cseconomyassistant.ui.theme.TextSecondary

@Composable
fun WeaponSection(
    modifier: Modifier = Modifier,
    onWeaponClick: (Weapon) -> Unit
) {
    val weaponsByType = weapons.groupBy { it.type }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        weaponsByType.forEach { (type, weaponsList) ->
            item {
                Text(
                    text = type.name.uppercase(),
                    color = TextPrimary,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(weaponsList) { weapon ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onWeaponClick(weapon) }
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
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
                        Text(
                            text = weapon.name,
                            color = TextPrimary,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Price: $${weapon.price}",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "Side: ${weapon.side.name}",
                            color = TextSecondary,
                            fontSize = 12.sp)
                        Text(
                            text = "Type: ${weapon.type.name}",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}