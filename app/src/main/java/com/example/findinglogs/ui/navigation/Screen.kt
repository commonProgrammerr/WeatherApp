package com.example.findinglogs.ui.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object Home : Screen("home")

    data object Detail : Screen("detail/{cityName}") {
        fun createRoute(cityName: String): String = "detail/${Uri.encode(cityName)}"
    }

    data object AddCity : Screen("add_city")
}
