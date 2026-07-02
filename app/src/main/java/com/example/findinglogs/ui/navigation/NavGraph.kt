package com.example.findinglogs.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.findinglogs.model.model.Weather
import com.example.findinglogs.ui.screens.WeatherDetailScreen
import com.example.findinglogs.ui.screens.WeatherListScreen
import com.example.findinglogs.viewmodel.MainViewModel

@Composable
fun WeatherNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel()
) {
    val weatherList: List<Weather> by viewModel.getWeatherList()
        .observeAsState(emptyList())

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            WeatherListScreen(
                weatherList = weatherList,
                onCityClick = { weather ->
                    navController.navigate(Screen.Detail.createRoute(weather.name ?: ""))
                },
                onRefresh = { viewModel.refresh() }
            )
        }

        composable(Screen.Detail.route) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            val weather = weatherList.find { it.name == cityName }
            WeatherDetailScreen(
                weather = weather,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
