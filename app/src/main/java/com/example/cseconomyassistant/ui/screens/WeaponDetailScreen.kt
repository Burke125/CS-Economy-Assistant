package com.example.cseconomyassistant.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.data.model.Weapon
import com.example.cseconomyassistant.ui.theme.TextPrimary
import com.example.cseconomyassistant.ui.theme.TextSecondary

@Composable
fun WeaponDetailScreen(
    weapon: Weapon,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = weapon.name,
            fontSize = 24.sp,
            color = TextPrimary
        )

        Image(
            painter = painterResource(id = weapon.image),
            contentDescription = weapon.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = "Price: $${weapon.price}", color = TextSecondary, fontSize = 14.sp)
            Text(text = "Side: ${weapon.side.name}", color = TextSecondary, fontSize = 14.sp)
            Text(text = "Type: ${weapon.type.name}", color = TextSecondary, fontSize = 14.sp)
            Text(text = "Magazine: ${weapon.magazineSize}/${weapon.ammoReserve}", color = TextSecondary, fontSize = 14.sp)
            Text(text = "Damage: ${weapon.damage}", color = TextSecondary, fontSize = 14.sp)
            Text(text = "Kill award: $${weapon.killAward}", color = TextSecondary, fontSize = 14.sp)
        }

        Text(
            text = weapon.description,
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
