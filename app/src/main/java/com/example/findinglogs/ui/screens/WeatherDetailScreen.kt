package com.example.findinglogs.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.findinglogs.model.model.Weather
import com.example.findinglogs.model.util.Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailScreen(
    weather: Weather?,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = weather?.name ?: "Detalhes") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = androidx.compose.material3.TopAppBarDefaults
                    .centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (weather == null) {
                Text(
                    text = "Cidade não encontrada",
                    style = MaterialTheme.typography.bodyLarge
                )
                return@Column
            }

            val info = weather.main

            Text(
                text = weather.name ?: "",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Temperatura: ${Utils.getCelsiusTemperatureFromKevin(info?.temp ?: 0f)}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sensação: ${Utils.getCelsiusTemperatureFromKevin(info?.feels_like ?: 0f)}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Máxima: ${Utils.getCelsiusTemperatureFromKevin(info?.temp_max ?: 0f)}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Mínima: ${Utils.getCelsiusTemperatureFromKevin(info?.temp_min ?: 0f)}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Pressão: ${(info?.pressure ?: 0f).toInt()} hPa",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Umidade: ${(info?.humidity ?: 0f).toInt()}%",
                style = MaterialTheme.typography.bodyLarge
            )

            weather.weather.firstOrNull()?.let { detail ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = detail.description.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
