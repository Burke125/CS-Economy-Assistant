package com.example.cseconomyassistant.ui.components.loadoutScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.data.model.Weapon
import com.example.cseconomyassistant.ui.theme.*

@Composable
fun LoadoutWeaponCard(
    weapon: Weapon,
    isSelected: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isSelected) Primary else BorderSubtle
    val alpha = if (enabled) 1f else 0.4f

    Column(
        modifier = modifier
            .alpha(alpha)
            .clickable(enabled = enabled) { onClick() }
            .background(SurfaceVariant, RoundedCornerShape(8.dp))
            .border(2.dp, borderColor, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = weapon.image),
            contentDescription = weapon.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = weapon.name,
            color = TextPrimary,
            fontSize = 13.sp
        )
    }
}
