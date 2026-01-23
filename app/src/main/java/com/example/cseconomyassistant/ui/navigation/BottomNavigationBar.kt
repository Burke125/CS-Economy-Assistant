package com.example.cseconomyassistant.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cseconomyassistant.ui.theme.*

@Composable
fun BottomNavigationBar(navController: NavController) {
    val screens = listOf(
        Screen.Home,
        Screen.Equipment,
        Screen.Maps,
        Screen.Loadout,
        Screen.History
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        containerColor = Background,
        contentColor = TextPrimary
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (screen) {
                            Screen.Home -> Icons.Default.Home
                            Screen.Equipment -> Icons.AutoMirrored.Filled.List
                            Screen.Maps -> Icons.Default.Map
                            Screen.Loadout -> Icons.Filled.Backpack
                            Screen.History -> Icons.Default.History
                            else -> Icons.Default.Home
                        },
                        contentDescription = screen.title
                    )
                },
                label = { Text(screen.title, fontSize = 12.sp) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CTBlue,
                    unselectedIconColor = TextSecondary,
                    selectedTextColor = CTBlue,
                    unselectedTextColor = TextSecondary,
                    indicatorColor = SurfaceVariant
                )
            )
        }
    }
}
