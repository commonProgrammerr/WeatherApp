package com.example.findinglogs.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.findinglogs.model.repo.Repository
import com.example.findinglogs.ui.screens.AddCityScreen
import com.example.findinglogs.ui.screens.WeatherDetailScreen
import com.example.findinglogs.ui.screens.WeatherListScreen
import com.example.findinglogs.viewmodel.MainViewModel
import com.example.findinglogs.viewmodel.UiState

@Composable
fun WeatherNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel()
) {
    val weatherList by viewModel.getWeatherList().observeAsState(emptyList())
    val uiState by viewModel.getUiState().observeAsState(UiState.Loading)

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            when (val state = uiState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                is UiState.Success -> {
                    if (state.data.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Nenhuma cidade cadastrada",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    } else {
                        val repo = Repository(
                            navController.context.applicationContext as android.app.Application
                        )
                        WeatherListScreen(
                            weatherList = state.data,
                            onCityClick = { weather ->
                                navController.navigate(
                                    Screen.Detail.createRoute(weather.name ?: "")
                                )
                            },
                            onRefresh = { viewModel.refresh() },
                            onAddCity = {
                                navController.navigate(Screen.AddCity.route)
                            },
                            onDeleteCity = { index ->
                                repo.removeCity((index + 1).toString())
                                viewModel.refresh()
                            }
                        )
                    }
                }
            }
        }

        composable(Screen.Detail.route) { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            val weather = weatherList.find { it.name == cityName }
            WeatherDetailScreen(
                weather = weather,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AddCity.route) {
            val repository = Repository(
                navController.context.applicationContext as android.app.Application
            )
            AddCityScreen(
                repository = repository,
                onBack = { navController.popBackStack() },
                onCityAdded = {
                    viewModel.refresh()
                    navController.popBackStack()
                }
            )
        }
    }
}
