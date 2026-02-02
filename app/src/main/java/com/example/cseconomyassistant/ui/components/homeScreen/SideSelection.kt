package com.example.cseconomyassistant.ui.components.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cseconomyassistant.R
import com.example.cseconomyassistant.data.model.Side
import com.example.cseconomyassistant.ui.theme.*

@Composable
fun SideSelection(
    selectedSide: Side,
    onSideSelected: (Side) -> Unit
) {
    Column {
        Text(
            text = "Select Side",
            color = TextPrimary,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 4.dp, bottom = 4.dp)
                .fillMaxWidth()
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SideCard(
                selectedSide = Side.CT,
                isSelected = selectedSide == Side.CT,
                color = CTBlue,
                icon = R.drawable.icon_shield,
                title = "Counter-Terrorist",
            ) { onSideSelected(Side.CT) }

            SideCard(
                selectedSide = Side.T,
                isSelected = selectedSide == Side.T,
                color = TOrange,
                icon = R.drawable.icon_bomb,
                title = "Terrorist",
            ) { onSideSelected(Side.T) }
        }
    }
}

@Composable
private fun SideCard(
    selectedSide: Side,
    isSelected: Boolean,
    color: Color,
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .background(
                if (isSelected) color else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .border(
                width = 3.dp,
                color = if(selectedSide == Side.CT) CTBlueDark else TOrangeDark,
                shape = RoundedCornerShape(8.dp)
            )
            .size(
                width = 185.dp,
                height = 80.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = title,
                tint = if (isSelected) Background else color,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                color = if (isSelected) Background else color
            )
        }
    }
}