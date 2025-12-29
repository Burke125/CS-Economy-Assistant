package com.example.cseconomyassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.cseconomyassistant.ui.navigation.AppNavGraph
import com.example.cseconomyassistant.ui.theme.CSEconomyAssistantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CSEconomyAssistantTheme {
                Surface {
                    val navController = rememberNavController()
                    AppNavGraph(navController)
                }
            }
        }
    }
}