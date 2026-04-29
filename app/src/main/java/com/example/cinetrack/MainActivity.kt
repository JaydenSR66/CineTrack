package com.example.cinetrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cinetrack.services.Screen
import com.example.cinetrack.ui.theme.CineTrackTheme
import com.example.cinetrack.views.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { CineTrackTheme { AppNavigation() } }
    }
}

/*
Handles the navigation and start destination
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route // The initial screen when the app launches
    ) {
        composable(Screen.Home.route) {
            HomeScreen()
        }
    }

}