package com.example.cinetrack.services

sealed class Screen(val route: String){
    object Splash : Screen("splash")
    object Home : Screen("home")
    object MyLists : Screen("mylists")
    object Settings : Screen("Settings")
}