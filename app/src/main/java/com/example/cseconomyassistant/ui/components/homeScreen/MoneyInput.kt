package com.example.cseconomyassistant.ui.components.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.ui.theme.BorderSubtle
import com.example.cseconomyassistant.ui.theme.CTBlue
import com.example.cseconomyassistant.ui.theme.TextPrimary
import com.example.cseconomyassistant.ui.theme.TextSecondary

@Composable
fun MoneyInput(
    currentMoney: Int,
    onMoneyChange: (Int) -> Unit
) {
    var text by remember { mutableStateOf(currentMoney.toString()) }

    Column {
        Text(
            text = "Current Money",
            color = TextPrimary,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = text,
            onValueChange = { newValue ->
                text = newValue.filter { it.isDigit() }
                onMoneyChange(text.toIntOrNull() ?: 0)
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = { Text("$") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CTBlue,
                unfocusedBorderColor = BorderSubtle
            )
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