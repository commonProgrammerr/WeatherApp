package com.example.findinglogs.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.findinglogs.ui.navigation.WeatherNavGraph
import com.example.findinglogs.ui.theme.WeatherAppTheme
import com.example.findinglogs.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {
            WeatherAppTheme {
                val navController = rememberNavController()
                WeatherNavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}
