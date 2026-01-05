package com.example.cseconomyassistant.ui.navigation

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cseconomyassistant.data.database.equipment
import com.example.cseconomyassistant.data.database.weapons
import com.example.cseconomyassistant.data.model.Equipment
import com.example.cseconomyassistant.data.model.Weapon
import com.example.cseconomyassistant.ui.screens.*


@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
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
        composable(Screen.Maps.route) { MapScreen() }
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
    }
}
