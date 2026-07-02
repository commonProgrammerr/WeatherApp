# Changelog

## [PR #17] — City Search, Current Location & Model Fixes

**Added:**
- City search via OpenWeatherMap Geocoding API (`geo/1.0/direct`)
- "My Location" with GPS permission and last known location
- `GeoCity` model and `GeoCallback` for geocoding responses
- Location pin icon on current location card

**Fixed:**
- Delete index mapping: correct key when "My Location" occupies display position 0
- Empty city list: ViewModel no longer hangs when no cities are configured
- Locale bug: `GeoCity.getCoordinates()` now uses `Locale.US` to prevent comma decimal separator issues on Portuguese/Brazilian devices
- `removeCity()`: validates key range before decrementing counter
- `equals()`/`hashCode()` added to `Weather`, `WeatherInfo`, `WeatherDetail` for structural equality

**Behavior diff:**
- Manual coordinate entry replaced by city name search with autocomplete results
- Current location auto-detected via GPS and pinned at top of the list
- Current location card can't be deleted and shows the real place name

---

## [PR #16] — Add City Screen, UiState & City Management

**Added:**
- `AddCityScreen` with city search and coordinates input
- `UiState` sealed class (`Loading`, `Success`, `Error`) with proper state handling
- City persistence in `SharedPreferences` via `addCity()`/`removeCity()`
- Delete city option with confirmation icon on each card
- Empty state: "Nenhuma cidade cadastrada" when list is empty
- "+" button in top bar to navigate to AddCity screen

**Fixed:**
- Added city now appears on main screen (`refresh()` forces new fetch)
- Auto-navigate back after adding a city
- Consistent ordering via `LinkedHashMap` instead of `HashMap`
- `WeatherListScreen` now handles loading, error, and empty states via `UiState`

**Behavior diff:**
- Cities can be added/removed and persist across sessions
- Loading, error, and empty states are now visually distinct
- City list maintains stable ordering

---

## [PR #15] — Navigation, Detail Screen & Race Condition

**Added:**
- Navigation with Jetpack Compose Navigation (`NavGraph`, `Screen` sealed class)
- `WeatherDetailScreen` with full weather info and back navigation
- `NavGraphTest` for route definitions

**Fixed:**
- Race condition in `fetchAllForecasts()`: added `isFetching` guard and generation counter to discard stale callbacks
- `postDelayed` moved from individual callbacks to single completion path
- Thread safety: `synchronized` blocks around shared `completedCount`

**Behavior diff:**
- Tapping a city opens a detail screen with expanded weather info and back navigation
- Rapid refreshes no longer cause duplicate requests or data corruption

---

## [PR #14] — Refresh, City Persistence & Deduplication

**Added:**
- `IRepository` interface for testability
- City persistence: coordinates stored in `SharedPreferences` instead of hardcoded
- `SharedPrefManager`: singleton wrapper for `SharedPreferences`
- `RepositoryTest` for persistence and deduplication behavior

**Fixed:**
- Refresh button: FAB now calls `viewModel.refresh()` instead of showing a Toast
- `refresh()` forces a new fetch even if one is in progress
- Duplicate coordinates removed: added Manaus and São Paulo replacing repeated entries
- `getLocalizations()` deduplicates coordinate values automatically
- Test-only constructor in `MainViewModel` accepting `IRepository` for mocking

**Behavior diff:**
- Refresh button now actually fetches fresh data instead of doing nothing
- Cities persist across app restarts
- Duplicate entries removed from default city list

---

## [PR #13] — Kotlin Migration & Jetpack Compose UI

**Added:**
- Kotlin 2.0.21 plugin and JVM target 17
- Jetpack Compose with BOM 2024.12.01 (Material3, Navigation, Icons)
- Testing stack: MockK 1.13.12, Truth 1.4.2, Robolectric 4.14.1, coroutines-test 1.9.0
- Compose theme with weather-based day/night color palette
- `WeatherCard` composable with icon, temperatures, pressure, humidity
- `WeatherListScreen` with `LazyColumn`, top app bar, and FAB
- `MainActivity.kt` migrated to Compose with `setContent {}`

**Fixed:**
- Kelvin conversion: `275.15` → `273.15` (fixed ~2°C offset)
- `KELVIN_OFFSET` extracted as named constant
- Cleaned up unused imports in `Utils.java`

**Removed:**
- `MainActivity.java` (replaced by Kotlin version)
- `model/Repository.java` (orphan stub)
- Old XML layouts and RecyclerView adapter

**Behavior diff:**
- UI migrated from XML layouts to Jetpack Compose with Material3 theming
- Pressure and icons now display correctly
- Temperature readings corrected (~2°C offset fixed)

---

## Original Project

- Basic weather app with Retrofit/OkHttp integration
- OpenWeatherMap API for current weather data
- SharedPreferences for basic persistence
- Material Design theme with day/night support
