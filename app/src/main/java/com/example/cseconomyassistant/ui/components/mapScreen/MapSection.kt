package com.example.cseconomyassistant.ui.components.mapScreen

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
import com.example.cseconomyassistant.data.database.gameMaps
import com.example.cseconomyassistant.data.model.GameMap
import com.example.cseconomyassistant.ui.theme.BorderSubtle
import com.example.cseconomyassistant.ui.theme.SurfaceVariant
import com.example.cseconomyassistant.ui.theme.TextPrimary
import com.example.cseconomyassistant.ui.theme.TextSecondary

@Composable
fun MapSection(
    onMapClick: (GameMap) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = gameMaps,
            key = { it.id }
        ) { map ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onMapClick(map) }
                    .background(
                        color = SurfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = BorderSubtle,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = map.name,
                        color = TextPrimary,
                        fontSize = 16.sp
                    )

                    Text(
                        text = "${map.location} • CT ${map.ctWinRate}% / T ${map.tWinRate}%",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
