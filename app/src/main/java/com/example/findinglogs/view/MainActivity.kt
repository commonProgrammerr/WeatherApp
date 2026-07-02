package com.example.findinglogs.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import com.example.findinglogs.model.model.Weather
import com.example.findinglogs.ui.screens.WeatherListScreen
import com.example.findinglogs.ui.theme.WeatherAppTheme
import com.example.findinglogs.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setContent {
            WeatherAppTheme {
                val weatherList: List<Weather> by viewModel.getWeatherList()
                    .observeAsState(emptyList())

                WeatherListScreen(
                    weatherList = weatherList,
                    onCityClick = { weather ->
                        Toast.makeText(
                            this@MainActivity,
                            "Detalhes para ${weather.name} (em breve)",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    onRefresh = { viewModel.refresh() }
                )
            }
        }
    }
}
