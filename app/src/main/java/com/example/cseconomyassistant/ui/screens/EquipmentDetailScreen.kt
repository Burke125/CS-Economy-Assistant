package com.example.cseconomyassistant.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.data.model.Equipment
import com.example.cseconomyassistant.ui.theme.TextPrimary
import com.example.cseconomyassistant.ui.theme.TextSecondary

@Composable
fun EquipmentDetailScreen(
    equipment: Equipment,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = equipment.name,
            fontSize = 24.sp,
            color = TextPrimary
        )

        Image(
            painter = painterResource(id = equipment.image),
            contentDescription = equipment.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "Price: $${equipment.price}", color = TextSecondary, fontSize = 14.sp)
            Text(text = "Side: ${equipment.side.name}", color = TextSecondary, fontSize = 14.sp)
            Text(text = "Slot: ${equipment.equipmentSlot.name}", color = TextSecondary, fontSize = 14.sp)
        }

        Text(
            text = equipment.description,
            color = TextPrimary,
            fontSize = 16.sp,
            lineHeight = 20.sp
        )

        Button(
            onClick = { onBack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back")
        }
    }
}
