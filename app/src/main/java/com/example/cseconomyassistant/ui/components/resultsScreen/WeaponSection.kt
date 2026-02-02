package com.example.cseconomyassistant.ui.components.resultsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.data.model.Weapon
import com.example.cseconomyassistant.ui.theme.BorderSubtle
import com.example.cseconomyassistant.ui.theme.SurfaceVariant
import com.example.cseconomyassistant.ui.theme.TextPrimary

@Composable
fun WeaponSection(weapon: Weapon?) {
    Text(text = "Weapon:")

    Column(
        modifier = Modifier
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
        if (weapon != null) {
            Image(
                painter = painterResource(id = weapon.image),
                contentDescription = weapon.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Fit
            )
            Text(
                text = weapon.name,
                color = TextPrimary,
                fontSize = 16.sp
            )
        }
    }
}
