package com.example.cseconomyassistant.ui.components.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.cseconomyassistant.R
import com.example.cseconomyassistant.data.model.Side
import com.example.cseconomyassistant.ui.theme.Background
import com.example.cseconomyassistant.ui.theme.CTBlue
import com.example.cseconomyassistant.ui.theme.TOrange

@Composable
fun SideSelection(
    selectedSide: Side,
    onSideSelected: (Side) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable{onSideSelected(Side.CT)}
                .background(
                    if(selectedSide == Side.CT) CTBlue
                    else Color.Transparent,
                    RoundedCornerShape(8.dp)
                )
                .border(
                    3.dp,
                    CTBlue,
                    RoundedCornerShape(8.dp)
                )
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.shield),
                    contentDescription = "Counter-Terrorist",
                    tint = (if(selectedSide == Side.CT) Background
                            else CTBlue
                            ),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Counter-Terrorist",
                    color = (if (selectedSide ==  Side.CT) Background
                            else CTBlue
                            )
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable{onSideSelected(Side.T)}
                .background(
                    if(selectedSide == Side.T) TOrange
                    else Color.Transparent,
                    RoundedCornerShape(8.dp)
                )
                .border(
                    3.dp,
                    TOrange,
                    RoundedCornerShape(8.dp)
                )
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bomb),
                    contentDescription = "Terrorist",
                    tint = (if(selectedSide == Side.T) Background
                            else TOrange
                            ),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Terrorist",
                    color = (if (selectedSide ==  Side.T) Background
                    else TOrange
                            )
                )
            }
        }
    }
}