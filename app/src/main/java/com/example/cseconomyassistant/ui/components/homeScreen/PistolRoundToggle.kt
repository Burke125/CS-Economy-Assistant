package com.example.cseconomyassistant.ui.components.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.ui.theme.Background
import com.example.cseconomyassistant.ui.theme.TOrange
import com.example.cseconomyassistant.ui.theme.TextPrimary


@Composable
fun PistolRoundToggle(
    isPistolRound: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Column{
        Text(
            text = "Round Type",
            fontSize = 14.sp,
            color = TextPrimary,
            modifier = Modifier
                .padding(bottom = 4.dp)
            )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (isPistolRound) TOrange else Color.Transparent,
                    RoundedCornerShape(8.dp)
                )
                .border(
                    width = 2.dp,
                    color = TOrange,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onToggle(!isPistolRound) }
                .padding(top = 15.dp, bottom = 15.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isPistolRound) "Pistol Round" else "Normal Round",
                color = if (isPistolRound) Background else TOrange,
                fontSize = 14.sp
            )
        }
    }

}


@Preview
@Composable
fun PistolRoundTogglePreview() {
    PistolRoundToggle(
        isPistolRound = false,
        onToggle = {}
    )
}