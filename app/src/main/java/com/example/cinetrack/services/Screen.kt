package com.example.cinetrack.services

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")
    object Search : Screen("search_screen")
    object WatchList : Screen("watchlist_screen")
    object Settings : Screen("settings_screen")
}