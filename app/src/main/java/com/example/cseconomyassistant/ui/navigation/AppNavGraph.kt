package com.example.cseconomyassistant.ui.navigation

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cseconomyassistant.ui.screens.EconomyGuideScreen
import com.example.cseconomyassistant.ui.screens.HistoryScreen
import com.example.cseconomyassistant.ui.screens.HomeScreen
import com.example.cseconomyassistant.ui.screens.MapScreen
import com.example.cseconomyassistant.ui.screens.WeaponScreen


sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Weapons : Screen("weapons", "Weapons")
    object Maps : Screen("maps", "Maps")
    object Guide : Screen("guide", "Guide")
    object History : Screen("history", "History")
}

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Weapons.route) { WeaponScreen() }
        composable(Screen.Maps.route) { MapScreen() }
        composable(Screen.Guide.route) { EconomyGuideScreen() }
        composable(Screen.History.route) { HistoryScreen() }
    }
}