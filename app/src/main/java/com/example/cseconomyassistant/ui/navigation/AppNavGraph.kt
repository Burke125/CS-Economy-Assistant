package com.example.cseconomyassistant.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cseconomyassistant.data.database.equipment
import com.example.cseconomyassistant.data.database.gameMaps
import com.example.cseconomyassistant.data.database.weapons
import com.example.cseconomyassistant.ui.screens.*
import com.example.cseconomyassistant.ui.viewmodel.RoundViewModel

sealed class Screen(
    val route: String,
    val title: String
) {

    object Home : Screen("home", "Home")
    object Equipment : Screen("equipment", "Equipment")
    object Maps : Screen("maps", "Maps")
    object Loadout : Screen("loadout", "Loadout")
    object History : Screen("history", "History")
    object CalculatorResults : Screen("calculator_results", "Results")

    object WeaponDetail : Screen(
        route = "weapon_detail/{weaponId}",
        title = "Weapon Detail"
    ) {
        fun createRoute(weaponId: String) = "weapon_detail/$weaponId"
    }

    object EquipmentDetail : Screen(
        route = "equipment_detail/{equipmentId}",
        title = "Equipment Detail"
    ) {
        fun createRoute(equipmentId: String) = "equipment_detail/$equipmentId"
    }

    object MapDetail : Screen(
        route = "map_detail/{mapId}",
        title = "Map Detail"
    ) {
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
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                roundViewModel = roundViewModel
            )
        }

        composable(Screen.Equipment.route) {
            EquipmentScreen(
                onWeaponClick = { weapon ->
                    navController.navigate(
                        Screen.WeaponDetail.createRoute(weapon.id)
                    )
                },
                onEquipmentClick = { equipment ->
                    navController.navigate(
                        Screen.EquipmentDetail.createRoute(equipment.id)
                    )
                }
            )
        }

        composable(Screen.Maps.route) {
            MapScreen(navController = navController)
        }

        composable(Screen.Loadout.route) {
            LoadoutScreen(
                loadoutViewModel = viewModel(),
                roundViewModel = roundViewModel
            )
        }

        composable(Screen.History.route) {
            HistoryScreen()
        }

        composable(Screen.WeaponDetail.route) { backStackEntry ->
            val weaponId =
                backStackEntry.arguments?.getString("weaponId") ?: return@composable

            val weapon =
                weapons.find { it.id == weaponId } ?: return@composable

            WeaponDetailScreen(
                weapon = weapon,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.EquipmentDetail.route) { backStackEntry ->
            val equipmentId =
                backStackEntry.arguments?.getString("equipmentId") ?: return@composable

            val equipmentItem =
                equipment.find { it.id == equipmentId } ?: return@composable

            EquipmentDetailScreen(
                equipment = equipmentItem,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.MapDetail.route) { backStackEntry ->
            val mapId =
                backStackEntry.arguments?.getString("mapId") ?: return@composable

            val map =
                gameMaps.find { it.id == mapId } ?: return@composable

            MapDetailScreen(
                gameMap = map,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.CalculatorResults.route) {
            ResultsScreen(
                roundViewModel = roundViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}