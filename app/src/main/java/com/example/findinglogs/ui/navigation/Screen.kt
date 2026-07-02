package com.example.findinglogs.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")

    data object Detail : Screen("detail/{cityName}") {
        fun createRoute(cityName: String): String = "detail/$cityName"
    }

    data object AddCity : Screen("add_city")
}
