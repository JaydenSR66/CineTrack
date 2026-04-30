package com.example.cinetrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.cinetrack.services.Screen
import com.example.cinetrack.ui.theme.CineTrackTheme
import com.example.cinetrack.views.AppBar
import com.example.cinetrack.views.BottomNavBar
import com.example.cinetrack.views.HomeScreen
import com.example.cinetrack.views.SearchScreen
import com.example.cinetrack.views.SettingsScreen
import com.example.cinetrack.views.WatchListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }

            CineTrackTheme(darkTheme = isDarkMode) {
                MainScreen(onThemeChange = { isDarkMode = it })
            }
        }
    }
}

@Composable
fun MainScreen(onThemeChange: (Boolean) -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppBar() },
        bottomBar = { BottomNavBar(navController) }
    ) { innerPadding ->

        val graph = navController.createGraph(startDestination = Screen.Home.route) {
            composable(route = Screen.Home.route) {
                HomeScreen()
            }
            composable(route = Screen.Search.route) {
                SearchScreen()
            }
            composable(route = Screen.WatchList.route) {
                WatchListScreen()
            }
            composable(route = Screen.Settings.route) {
                SettingsScreen(onThemeChange = onThemeChange)
            }
        }

        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(innerPadding)
        )
    }
}