package com.example.cseconomyassistant.ui.components.homeScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.ui.theme.*

@Composable
fun TeamMoneyInputSlider(
    teamMoney: Int,
    onTeamMoneyChange: (Int) -> Unit,
    locked: Boolean
) {
    var sliderValue by remember { mutableStateOf(teamMoney.toFloat()) }
    var text by remember { mutableStateOf(teamMoney.toString()) }

    LaunchedEffect(teamMoney) {
        sliderValue = teamMoney.toFloat()
        text = teamMoney.toString()
    }

    Column {
        Text(
            text = "$ Team Average Money",
            color = TextPrimary,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = {},
            enabled = false,
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = { Text("$") },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = TextSecondary,
                disabledBorderColor = BorderSubtle,
                disabledLeadingIconColor = TextSecondary
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Slider(
            value = sliderValue,
            onValueChange = { value ->
                val rounded = ((value / 50).toInt() * 50).coerceIn(0, 16000)
                sliderValue = rounded.toFloat()
                text = rounded.toString()
                onTeamMoneyChange(rounded)
            },
            valueRange = 0f..16000f,
            steps = 79,
            enabled = !locked
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Min: $0", color = TextSecondary, fontSize = 12.sp)
            Text("Max: $16,000", color = TextSecondary, fontSize = 12.sp)
        }
    }
}
