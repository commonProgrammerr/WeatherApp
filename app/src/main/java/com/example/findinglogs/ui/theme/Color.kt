package com.example.findinglogs.ui.theme

import androidx.compose.ui.graphics.Color

// Day colors (from colors.xml)
val WeatherClearSky = Color(0xFF4FC3F7)
val WeatherFewClouds = Color(0xFF81D4FA)
val WeatherScatteredClouds = Color(0xFFB0BEC5)
val WeatherCloudy = Color(0xFF90A4AE)
val WeatherLightRain = Color(0xFF64B5F6)
val WeatherHeavyRain = Color(0xFF455A64)
val WeatherThunderstorm = Color(0xFF616161)
val WeatherSnow = Color(0xFFECEFF1)
val WeatherFog = Color(0xFFBDBDBD)

// Night/dark colors
val WeatherClearSkyDark = Color(0xFF0288D1)
val WeatherFewCloudsDark = Color(0xFF039BE5)
val WeatherScatteredCloudsDark = Color(0xFF78909C)
val WeatherCloudyDark = Color(0xFF607D8B)
val WeatherLightRainDark = Color(0xFF1E88E5)
val WeatherHeavyRainDark = Color(0xFF263238)
val WeatherThunderstormDark = Color(0xFF212121)
val WeatherSnowDark = Color(0xFFCFD8DC)
val WeatherFogDark = Color(0xFF9E9E9E)

fun cardBackgroundForIcon(icon: String, isDark: Boolean): Color {
    return when (icon) {
        "02d" -> WeatherFewClouds
        "02n" -> WeatherFewCloudsDark
        "03d" -> WeatherCloudy
        "03n" -> WeatherCloudyDark
        "04d" -> WeatherScatteredClouds
        "04n" -> WeatherScatteredCloudsDark
        "09d" -> WeatherFog
        "09n" -> WeatherFogDark
        else -> if (isDark) WeatherFewCloudsDark else WeatherFewClouds
    }
}
