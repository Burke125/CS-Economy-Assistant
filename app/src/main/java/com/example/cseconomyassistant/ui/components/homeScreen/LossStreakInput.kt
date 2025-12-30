package com.example.cseconomyassistant.ui.components.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.ui.theme.TextPrimary
import com.example.cseconomyassistant.ui.theme.BorderSubtle
import com.example.cseconomyassistant.ui.theme.TOrange

@Composable
fun LossStreakInput(
    selectedLoss: Int,
    onLossSelected: (Int) -> Unit
) {
    Column {
        Text(
            text = "Loss Streak",
            color = TextPrimary,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            (0..4).forEach { value ->
                LossStreakChip(
                    value = value,
                    isSelected = value == selectedLoss,
                    onClick = { onLossSelected(value) }
                )
            }
        }
    }
}

@Composable
private fun LossStreakChip(
    value: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .background(
                if (isSelected) TOrange else Color.Transparent,
                RoundedCornerShape(2.dp)
            )
            .border(
                width = 1.dp,
                color = if(isSelected) TOrange else BorderSubtle,
                shape = RoundedCornerShape(2.dp)
            )
            .size(
                width = 70.dp,
                height = 45.dp
            ),
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value.toString(),
                color = TextPrimary,
                fontSize = 16.sp,
            )
        }
    }
}

/*
@Preview()
@Composable
fun LossStreakInputPreview() {
    LossStreakInput(
        selectedLoss = 0,
        onLossSelected = {}
    )
}
*/