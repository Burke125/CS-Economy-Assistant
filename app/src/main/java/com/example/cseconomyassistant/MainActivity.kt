package com.example.cseconomyassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.cseconomyassistant.ui.navigation.AppNavGraph
import com.example.cseconomyassistant.ui.navigation.BottomNavigationBar
import com.example.cseconomyassistant.ui.theme.CSEconomyAssistantTheme
import com.example.cseconomyassistant.ui.viewmodel.RoundViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            CSEconomyAssistantTheme(darkTheme = true) {
                AppEntryPoint()
            }
        }
    }
}

@Composable
fun AppEntryPoint() {

    val navController = rememberNavController()
    val roundViewModel: RoundViewModel = viewModel()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .consumeWindowInsets(paddingValues)
        ) {
            AppNavGraph(
                navController = navController,
                roundViewModel = roundViewModel
            )
        }
    }
}
