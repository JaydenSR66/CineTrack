package com.example.cinetrack.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.cinetrack.R
import com.example.cinetrack.services.Screen

// Guide used to help: https://medium.com/@santosh_yadav321/bottom-navigation-bar-in-jetpack-compose-5b3c5f2cea9b

// Data class representing a navigation item
data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
)

@Composable
fun BottomNavBar(navController: NavController) {

    // List of the navigation items
    val navigationItems = listOf(
        NavigationItem(stringResource(R.string.home), Icons.Default.Home, Screen.Home.route),
        NavigationItem(stringResource(R.string.search), Icons.Default.Search, Screen.Search.route),
        NavigationItem(
            stringResource(R.string.watchlist), Icons.Default.Bookmark, Screen.WatchList.route
        ),
        NavigationItem(
            stringResource(R.string.settings), Icons.Default.Settings, Screen.Settings.route
        ),
    )

    // Tracks which item is currently selected
    val selectedNavigationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    // Bottom Navigation container
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        // Create a Navigation Item for Each Screen
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex.intValue == index,
                onClick = {
                    selectedNavigationIndex.intValue = index
                    navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    ) // Navigation Icon Image
                },
                label = {
                    Text(text = item.title) // Navigation Text
                },
                // Colours for Selected and Unselected Status
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surface,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}


