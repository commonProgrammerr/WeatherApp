# 🌦️ Weather App

A weather application built with **Kotlin + Jetpack Compose** that fetches and displays weather data from the [OpenWeatherMap API](https://openweathermap.org/api).

---

## ✨ Features

- **Weather list** — Displays temperature (max/min/current), humidity, and pressure for multiple cities
- **City search** — Search cities by name via OpenWeatherMap Geocoding API and add to your list
- **My Location** — Automatically detects your current location and shows weather at the top
- **City management** — Add and remove cities from your personalized list
- **Detail screen** — Tap any city for detailed weather info with back navigation
- **Auto-refresh** — Weather data refreshes every 2 minutes
- **Day/Night theme** — Weather-based card colors adapt to conditions and system theme
- **Persistent storage** — Cities are saved and restored across app restarts

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| **Language** | Kotlin + Java |
| **UI** | Jetpack Compose (Material3) |
| **Architecture** | MVVM + Repository pattern |
| **Networking** | Retrofit 2.11 + OkHttp 4.12 |
| **API** | OpenWeatherMap (Current Weather + Geocoding) |
| **Persistence** | SharedPreferences |
| **Navigation** | Jetpack Navigation Compose |
| **Async** | Handler + Retrofit callbacks |
| **State** | LiveData + UiState sealed class |
| **Testing** | JUnit 4, MockK, Truth, Robolectric |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog+ (2023.1.1)
- JDK 17
- Android SDK 35
- OpenWeatherMap API key ([free sign-up](https://openweathermap.org/api))

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/commonProgrammerr/WeatherApp.git
   ```

2. Add your API key to `local.properties` in the project root:
   ```properties
   WEATHER_API_KEY=your_api_key_here
   ```

3. Build and run:
   ```bash
   ./gradlew assembleDebug
   ```

> ⚠️ The API key takes 10–30 minutes to activate after registration.

---

## 🧪 Running Tests

```bash
./gradlew test
```

16 unit tests covering utilities, ViewModel, repository, and navigation.

---

## 📁 Project Structure

```
app/src/main/java/com/example/findinglogs/
├── model/
│   ├── model/           # POJOs (Weather, WeatherInfo, WeatherDetail, GeoCity)
│   ├── repo/            # IRepository + Repository implementation
│   │   ├── local/       # SharedPrefManager
│   │   └── remote/      # Retrofit, API services, callbacks
│   └── util/            # Utils, Logger, Constants
├── ui/
│   ├── components/      # WeatherCard composable
│   ├── navigation/      # NavGraph + Screen routes
│   ├── screens/         # WeatherListScreen, WeatherDetailScreen, AddCityScreen
│   └── theme/           # Color.kt, Theme.kt
├── view/                # MainActivity
└── viewmodel/           # MainViewModel, UiState
```

---

## 📋 Changelog

See [CHANGELOG.md](CHANGELOG.md) for a detailed history of changes.
