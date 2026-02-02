package com.example.cseconomyassistant.ui.components.historyScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.data.model.BuyHistoryEntry
import com.example.cseconomyassistant.ui.theme.BorderSubtle
import com.example.cseconomyassistant.ui.theme.SurfaceVariant

@Composable
fun HistoryCard(
    id: String,
    entry: BuyHistoryEntry,
    onDelete: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
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
            .clickable { expanded = !expanded }
    ) {
        Text(
            text = "${entry.side} • ${entry.buyType}",
            fontSize = 16.sp
        )

        Text(
            text = "My $: ${entry.myMoney} | Team avg: ${entry.teamMoney}"
        )

        Text(
            text = "Loss streak: ${entry.lossStreak}"
        )

        if (expanded) {
            Spacer(Modifier.height(8.dp))

            Text("Weapon: ${entry.weaponName ?: "None"}")
            Text("Armor: ${entry.armor}")

            if (entry.hasDefuse) {
                Text("Defuse kit: Yes")
            }

            if (entry.utility.isNotEmpty()) {
                Text("Utility:")
                entry.utility.forEach {
                    Text("• $it")
                }
            } else {
                Text("Utility: None")
            }

            Spacer(Modifier.height(4.dp))
            Text("Total spend: $${entry.totalCost}")
            Text("Remaining: $${entry.remainingMoney}")

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = { onDelete(id) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Delete")
            }
        }
    }
}
