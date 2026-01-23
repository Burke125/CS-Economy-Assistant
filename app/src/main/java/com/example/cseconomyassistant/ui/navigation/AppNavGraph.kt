package com.example.cseconomyassistant.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cseconomyassistant.data.database.equipment
import com.example.cseconomyassistant.data.database.gameMaps
import com.example.cseconomyassistant.data.database.weapons
import com.example.cseconomyassistant.ui.screens.*
import com.example.cseconomyassistant.ui.viewmodel.RoundViewModel

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Equipment : Screen("equipment", "Equipment")
    object Maps : Screen("maps", "Maps")
    object Loadout : Screen("loadout", "Loadout")
    object History : Screen("history", "History")
    object CalculatorResults : Screen("calculator_results", "Results")

    object MapDetail : Screen("map_detail/{mapId}", "Map Detail") {
        fun createRoute(mapId: String) = "map_detail/$mapId"
    }
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    roundViewModel: RoundViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) { HomeScreen(navController, roundViewModel) }

        composable(Screen.Equipment.route) {
            EquipmentScreen(
                onWeaponClick = { weapon ->
                    navController.navigate("weapon_detail/${weapon.id}")
                },
                onEquipmentClick = { equipment ->
                    navController.navigate("equipment_detail/${equipment.id}")
                }
            )
        }

        composable(Screen.Maps.route) { MapScreen(navController = navController) }

        composable(Screen.Loadout.route) {
            LoadoutScreen()
        }

        composable(Screen.History.route) { HistoryScreen() }

        composable("weapon_detail/{weaponId}") { backStackEntry ->
            val weaponId = backStackEntry.arguments?.getString("weaponId") ?: return@composable
            val weapon = weapons.find { it.id == weaponId } ?: return@composable
            WeaponDetailScreen(
                weapon = weapon,
                onBack = { navController.popBackStack() }
            )
        }

        composable("equipment_detail/{equipmentId}") { backStackEntry ->
            val equipmentId = backStackEntry.arguments?.getString("equipmentId") ?: return@composable
            val equipmentItem = equipment.find { it.id == equipmentId } ?: return@composable
            EquipmentDetailScreen(
                equipment = equipmentItem,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.MapDetail.route) { backStackEntry ->
            val mapId = backStackEntry.arguments?.getString("mapId") ?: return@composable
            val map = gameMaps.find { it.id == mapId } ?: return@composable
            MapDetailScreen(
                gameMap = map,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.CalculatorResults.route) {
            ResultsScreen(
                navController = navController,
                roundViewModel = roundViewModel,
                onBack = { navController.popBackStack() }
            )
        }

    }
}
