package com.example.cseconomyassistant.ui.navigation

sealed class Screen(val route: String, val title: String) {
    // Screens for BottomNavigationBar
    object Home : Screen("home", "Home")
    object Equipment : Screen("equipment", "Equipment")
    object Maps : Screen("maps", "Maps")
    object Guide : Screen("guide", "Guide")
    object History : Screen("history", "History")

    // Screens NOT in BottomNavigationBar
    object WeaponDetail : Screen("weaponDetail/{weaponId}", "Weapon Detail")
    object EquipmentDetail : Screen("equipmentDetail/{equipmentId}", "Equipment Detail")
}
