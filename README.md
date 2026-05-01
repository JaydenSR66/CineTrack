# CineTrack 🎬

A native Android film tracking application built with Kotlin and Jetpack Compose for CMP309 - Software Development for Mobile Devices at Abertay University.

---

## Features

- 🎥 Browse popular movies powered by the TMDB API
- 🔍 Search the entire TMDB database with infinite scroll pagination
- 🔖 Add movies to a personal watchlist
- ✅ Mark movies as watched
- 🗑️ Remove movies from either list independently
- 🌙 Light and dark mode toggle — persists between sessions
- 🌍 Multi-language support (English & Spanish)
- 📡 Network connectivity monitoring via BroadcastReceiver
- ⚠️ Offline banner shown when internet connection is lost

---

## Video Demo

> ![Video Demo](Demo/CineTrack_Demo.mp4)

---

## Tech Stack

| Category | Technology |
|----------|-----------|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Design System | Material 3 |
| Architecture | MVVM |
| Networking | Retrofit 2 + Gson |
| Image Loading | Coil |
| Local Storage | SharedPreferences |
| Navigation | Jetpack Navigation Compose |
| Connectivity | BroadcastReceiver |
| API | The Movie Database (TMDB) |

---

## Architecture

CineTrack follows the **MVVM (Model-View-ViewModel)** architecture pattern:

```
Models → Services → ViewModels → Views
```

- **Models** — Pure Kotlin data classes (`Movie`, `SavedMovie`, `MovieResponse`)
- **Services** — Handles all external communication (`TmdbApiService`, `MovieService`, `SettingsService`, `NetworkReceiver`)
- **ViewModels** — Business logic layer with `StateFlow` state observed by the UI
- **Views** — Composable screens and reusable UI components

---

## Project Structure

```
com.example.cinetrack
├── models/
│   ├── Movie.kt
│   └── SavedMovie.kt
├── services/
│   ├── TmdbApiService.kt
│   ├── MovieService.kt
│   ├── SettingsService.kt
│   ├── NetworkReceiver.kt
│   └── Screen.kt
├── viewmodels/
│   ├── HomeViewModel.kt
│   ├── SearchViewModel.kt
│   ├── MyListsViewModel.kt
│   └── SettingsViewModel.kt
└── views/
    ├── HomeScreen.kt
    ├── SearchScreen.kt
    ├── WatchListScreen.kt
    ├── SettingsScreen.kt
    ├── MovieCard.kt
    ├── NetworkBanner.kt
    └── AppBar.kt
```

---

## Setup

### Prerequisites

- Android Studio Hedgehog or later
- Minimum SDK: API 33 (Android 13)
- Target SDK: API 36
- A free TMDB API key — register at [themoviedb.org](https://www.themoviedb.org/documentation/api)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/your-username/CineTrack.git
```

2. Open the project in Android Studio

3. Create a `local.properties` file in the root of the project and add your TMDB API key:
```
TMDB_API_KEY=your_api_key_here
```

4. Sync the project with Gradle

5. Run the app on an emulator or physical device (API 33+)

---

## Dependencies

```kotlin
// Retrofit + Gson (API calls)
implementation("com.squareup.retrofit2:retrofit:2.11.0")
implementation("com.squareup.retrofit2:converter-gson:2.11.0")
implementation("com.google.code.gson:gson:2.10.1")

// Coil (image loading)
implementation("io.coil-kt:coil-compose:2.7.0")

// Navigation
implementation("androidx.navigation:navigation-compose:2.8.9")

// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

// Material Icons
implementation("androidx.compose.material:material-icons-extended:1.7.8")
```

---

## API

This app uses the [TMDB API](https://www.themoviedb.org/documentation/api) to fetch movie data.

The following endpoints are used:

| Endpoint | Description |
|----------|-------------|
| `GET /movie/popular` | Fetches popular movies for the Home screen |
| `GET /discover/movie` | Fetches all movies sorted A-Z for the Search screen |
| `GET /search/movie` | Searches the full TMDB database by query |

> **Note:** The API key is stored in `local.properties` and injected via `BuildConfig` at build time. It is never committed to GitHub.

---

## Local Storage

User data is stored locally on the device using **SharedPreferences**:

| Data | Key | Type |
|------|-----|------|
| Watchlist | `watchlist` | JSON (List of SavedMovie) |
| Watched list | `watched` | JSON (List of SavedMovie) |
| Dark mode preference | `dark_mode` | Boolean |
| Language preference | `language` | String |

---

## App Screens

| Screen | Description |
|--------|-------------|
| Home | Displays popular movies fetched from TMDB |
| Search | Browse all movies A-Z with search functionality |
| My Lists | View and manage watchlist and watched movies in two tabs |
| Settings | Toggle dark mode, switch language, clear lists |

---

## Author

**Jayden Robertson** — Student Number: 2407659  
Abertay University | CMP309 Software Development for Mobile Devices | 2025/26
