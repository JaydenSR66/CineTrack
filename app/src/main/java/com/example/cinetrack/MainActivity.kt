package com.example.cinetrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { CineTrackTheme { MainScreen() } }
    }
}

/*
Handles the navigation and start destination
 */
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppBar() },
        bottomBar = { BottomNavBar(navController) }) { innerPadding ->

        val graph = navController.createGraph(startDestination = Screen.Home.route) {
            composable(route = Screen.Home.route) {
                HomeScreen()
            }
            composable(route = Screen.Search.route) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    SearchScreen()
                }
            }
            composable(route = Screen.WatchList.route) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                }
            }
            composable(route = Screen.Settings.route) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                }
            }
        }

        NavHost(
            navController = navController, graph = graph, modifier = Modifier.padding(innerPadding)
        )
    }
}