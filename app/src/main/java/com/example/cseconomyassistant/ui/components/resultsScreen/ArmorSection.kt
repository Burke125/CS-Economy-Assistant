package com.example.cseconomyassistant.ui.components.resultsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cseconomyassistant.data.model.Equipment
import com.example.cseconomyassistant.ui.theme.BorderSubtle
import com.example.cseconomyassistant.ui.theme.SurfaceVariant


@Composable
fun ArmorSection(equipment: List<Equipment>) {
    val kevlarHelmet = equipment.find { it.name == "Kevlar+Helmet" }
    val kevlar =
        if (kevlarHelmet == null)
            equipment.find { it.name == "Kevlar" }
        else null

    if(kevlar == null && kevlarHelmet == null){
        return
    }

    Text("Armor:")

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
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
        kevlarHelmet?.let {
            Image(
                painter = painterResource(id = it.image),
                contentDescription = it.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )
            Text(it.name)
        }


        kevlar?.let {
            Image(
                painter = painterResource(id = it.image),
                contentDescription = it.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )
            Text(it.name)
        }
    }
}

