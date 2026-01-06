package com.example.cseconomyassistant.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cseconomyassistant.data.database.equipment
import com.example.cseconomyassistant.data.database.gameMaps
import com.example.cseconomyassistant.data.database.weapons
import com.example.cseconomyassistant.data.model.GameMap
import com.example.cseconomyassistant.ui.screens.*

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Equipment : Screen("equipment", "Equipment")
    object Maps : Screen("maps", "Maps")
    object Guide : Screen("guide", "Guide")
    object History : Screen("history", "History")

    object MapDetail : Screen("map_detail/{mapId}", "Map Detail") {
        fun createRoute(mapId: String) = "map_detail/$mapId"
    }


}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,

    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
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
        composable(Screen.Guide.route) { EconomyGuideScreen() }
        composable(Screen.History.route) { HistoryScreen() }

        composable(
            route = "weapon_detail/{weaponId}"
        ) { backStackEntry ->
            val weaponId = backStackEntry.arguments?.getString("weaponId") ?: return@composable
            val weapon = weapons.find { it.id == weaponId } ?: return@composable
            WeaponDetailScreen(
                weapon = weapon,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = "equipment_detail/{equipmentId}"
        ) { backStackEntry ->
            val equipmentId = backStackEntry.arguments?.getString("equipmentId") ?: return@composable
            val equipmentItem = equipment.find { it.id == equipmentId } ?: return@composable
            EquipmentDetailScreen(
                equipment = equipmentItem,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.MapDetail.route
        ) { backStackEntry ->
            val mapId = backStackEntry.arguments
                ?.getString("mapId")
                ?: return@composable

            val map = gameMaps.find { it.id == mapId }
                ?: return@composable

            MapDetailScreen(
                gameMap = map,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
