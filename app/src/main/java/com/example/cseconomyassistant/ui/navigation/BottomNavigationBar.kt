package com.example.cseconomyassistant.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cseconomyassistant.ui.theme.Background
import com.example.cseconomyassistant.ui.theme.CTBlue
import com.example.cseconomyassistant.ui.theme.SurfaceVariant
import com.example.cseconomyassistant.ui.theme.TextPrimary
import com.example.cseconomyassistant.ui.theme.TextSecondary

@Composable
fun BottomNavigationBar(navController: NavController) {
    val screens = listOf(
        Screen.Home,
        Screen.Weapons,
        Screen.Maps,
        Screen.Guide,
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
                            Screen.Weapons -> Icons.AutoMirrored.Filled.List
                            Screen.Maps -> Icons.Default.Map
                            Screen.Guide -> Icons.Default.Info
                            Screen.History -> Icons.Default.History
                        },
                        contentDescription = screen.title
                    )
                },
                label = {
                    Text(
                        screen.title,
                        fontSize = 12.sp
                    )
                },
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

@Preview
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(
        navController = navController
    )
}