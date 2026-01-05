package com.example.cseconomyassistant.ui.components.equipmentScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.data.model.EquipmentCategory
import com.example.cseconomyassistant.ui.theme.BorderSubtle
import com.example.cseconomyassistant.ui.theme.SurfaceVariant


@Composable
fun EquipmentSelection(
    equipmentCategory: EquipmentCategory,
    onEquipmentSelected: (EquipmentCategory) -> Unit
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            EquipmentCategoryCard(
                title = "Weapons",
                isSelected = equipmentCategory == EquipmentCategory.WEAPONS,
                onClick = { onEquipmentSelected(EquipmentCategory.WEAPONS) },
                modifier = Modifier.weight(1f)
            )

            EquipmentCategoryCard(
                title = "Utility",
                isSelected = equipmentCategory == EquipmentCategory.EQUIPMENT,
                onClick = { onEquipmentSelected(EquipmentCategory.EQUIPMENT) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun EquipmentCategoryCard(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .background(
                if (isSelected) SurfaceVariant else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = BorderSubtle,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}


