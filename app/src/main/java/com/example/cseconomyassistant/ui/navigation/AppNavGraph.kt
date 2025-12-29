package com.example.cseconomyassistant.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cseconomyassistant.ui.screens.EconomyGuideScreen
import com.example.cseconomyassistant.ui.screens.HistoryScreen
import com.example.cseconomyassistant.ui.screens.HomeScreen


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object EconomyGuide : Screen("economy")
    object History : Screen("history")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.EconomyGuide.route) { EconomyGuideScreen() }
        composable(Screen.History.route) { HistoryScreen() }
    }
}