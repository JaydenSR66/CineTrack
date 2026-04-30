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
import com.example.cinetrack.services.SettingsService
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

        // Used to load user saved settings for dark mode or languages
        val settingsService = SettingsService(this)

        // Apply the saved language before the ui is displayed
        applyLanguage(settingsService.getLanguage())

        setContent {
            var isDarkMode by remember { mutableStateOf(settingsService.getDarkMode()) }

            CineTrackTheme(darkTheme = isDarkMode) {
                MainScreen(
                    onThemeChange = { isDarkMode = it },
                    onLanguageChange = { languageCode ->
                        applyLanguage(languageCode)
                        recreate()
                    }
                )
            }
        }
    }

    @Composable
    fun MainScreen(onThemeChange: (Boolean) -> Unit, onLanguageChange: (String) -> Unit) {
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
                    SettingsScreen(
                        onThemeChange = onThemeChange,
                        onLanguageChange = onLanguageChange
                    )
                }
            }

            NavHost(
                navController = navController,
                graph = graph,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }

    /*
    Applies the selected language to the app
     */
    private fun applyLanguage(languageCode: String) {
        val locale = java.util.Locale(languageCode)
        java.util.Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}

