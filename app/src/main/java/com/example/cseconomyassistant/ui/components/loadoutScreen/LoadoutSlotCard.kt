package com.example.cseconomyassistant.ui.components.loadoutScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.data.model.Weapon
import com.example.cseconomyassistant.ui.theme.PrimaryVariant
import com.example.cseconomyassistant.ui.theme.SurfaceVariant

@Composable
fun LoadoutSlotCard(
    title: String,
    weapon: Weapon?,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(90.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (enabled) SurfaceVariant else PrimaryVariant
            )
            .clickable(enabled = enabled) { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        weapon?.image?.let { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = weapon.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 12.dp)
            )
        }

        Text(
            text = weapon?.name ?: title,
            color = if (enabled) Color.White else Color.Gray,
            fontSize = 14.sp
        )
    }
}

