package com.example.findinglogs.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.findinglogs.R
import com.example.findinglogs.model.model.Weather
import com.example.findinglogs.model.util.Utils
import com.example.findinglogs.ui.theme.cardBackgroundForIcon

@Composable
fun WeatherCard(
    weather: Weather,
    onClick: () -> Unit,
    onDelete: () -> Unit = {}
) {
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()
    val weatherInfo = weather.main
    val weatherDetail = weather.weather.firstOrNull()
    val iconCode = weatherDetail?.icon.orEmpty()
    val iconId = context.resources.getIdentifier(
        "weather_icon_$iconCode",
        "drawable",
        context.packageName
    ).takeIf { it != 0 } ?: R.drawable.ic_launcher_foreground

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundForIcon(iconCode, isDark)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (weather.isCurrentLocation) {
                        Icon(
                            imageVector = Icons.Default.MyLocation,
                            contentDescription = "Minha localização",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                    Text(
                        text = weather.name ?: "",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Temp. atual: ${Utils.getCelsiusTemperatureFromKevin(weatherInfo?.temp ?: 0f)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Temp. máx: ${Utils.getCelsiusTemperatureFromKevin(weatherInfo?.temp_max ?: 0f)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Temp. mín: ${Utils.getCelsiusTemperatureFromKevin(weatherInfo?.temp_min ?: 0f)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Pressão: ${(weatherInfo?.pressure ?: 0f).toInt()}hPa",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    text = "Umidade: ${(weatherInfo?.humidity ?: 0f).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remover cidade",
                    tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                )
            }

            Image(
                painter = painterResource(id = iconId),
                contentDescription = "Ícone do tempo: $iconCode",
                modifier = Modifier.size(100.dp)
            )
        }
    }
}
