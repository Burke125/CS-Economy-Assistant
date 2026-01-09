package com.example.cseconomyassistant.ui.components.equipmentScreen

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.data.database.equipment
import com.example.cseconomyassistant.data.model.Equipment
import com.example.cseconomyassistant.ui.theme.BorderSubtle
import com.example.cseconomyassistant.ui.theme.SurfaceVariant
import com.example.cseconomyassistant.ui.theme.TextPrimary
import com.example.cseconomyassistant.ui.theme.TextSecondary

@Composable
fun EquipmentSection(
    modifier: Modifier = Modifier,
    onEquipmentClick: (Equipment) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(equipment) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEquipmentClick(item) }
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
                    Image(
                        painter = painterResource(id = item.image),
                        contentDescription = item.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = item.name,
                        color = TextPrimary,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Price: $${item.price}",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Side: ${item.side.name}",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}