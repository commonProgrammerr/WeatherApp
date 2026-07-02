package com.example.findinglogs.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.findinglogs.model.model.Weather
import com.example.findinglogs.ui.components.WeatherCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherListScreen(
    weatherList: List<Weather>,
    onCityClick: (Weather) -> Unit,
    onRefresh: () -> Unit,
    onAddCity: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Weather App", style = MaterialTheme.typography.titleLarge)
                },
                actions = {
                    IconButton(onClick = onAddCity) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Adicionar cidade",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults
                    .centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onRefresh,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Atualizar"
                )
            }
        }
    ) { paddingValues ->
        if (weatherList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(text = "Nenhum dado disponível", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(top = 8.dp)
            ) {
                items(items = weatherList, key = { it.name ?: it.hashCode().toString() }) { weather ->
                    WeatherCard(
                        weather = weather,
                        onClick = { onCityClick(weather) }
                    )
                }
            }
        }
    }
}
