package com.example.cinetrack.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cinetrack.R
import com.example.cinetrack.viewmodels.MyWatchListViewModel

// Guide Used: https://kotlinlang.org/api/compose-multiplatform/material3/androidx.compose.material3/-primary-tab-row.html#:~:text=Primary%20tabs%20are%20placed%20at,transportation%20methods%20in%20a%20map.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchListScreen(viewModel: MyWatchListViewModel = viewModel()) {

    // Get watchlist and watched data
    val watchlist by viewModel.watchList.collectAsState()
    val watched by viewModel.watched.collectAsState()

    // Tracks which tab is selected - 0 = Watchlist, 1 = Watched
    val selectedTab = rememberSaveable { mutableIntStateOf(0) }

    // Vertical layout
    Column(modifier = Modifier.fillMaxSize()) {

        // Tab row to switch between Watchlist and Watched
        PrimaryTabRow(
            selectedTabIndex = selectedTab.intValue,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            // Watchlist tab
            Tab(
                selected = selectedTab.intValue == 0,
                onClick = { selectedTab.intValue = 0 },
                text = {
                    Text(
                        text = "Watchlist (${watchlist.size})",
                        fontWeight = if (selectedTab.intValue == 0) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
            // Watched tab
            Tab(
                selected = selectedTab.intValue == 1,
                onClick = { selectedTab.intValue = 1 },
                text = {
                    Text(
                        text = "Watched (${watched.size})",
                        fontWeight = if (selectedTab.intValue == 1) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }

        // Show the correct list based on selected tab
        when (selectedTab.intValue) {
            // Watchlist Tab
            0 -> {
                if (watchlist.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // If watchlist is empty, display empty_watch list
                        Text(
                            text = stringResource(R.string.empty_watchlist),
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    // Display list of watchlist movies
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(watchlist, key = { it.id }) { movie ->
                            SavedMovieCard(
                                movie = movie,
                                onRemove = { viewModel.removeFromWatchList(movie.id) }
                            )
                        }
                    }
                }
            }

            // Watched tab
            1 -> {
                if (watched.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        // If watched list is empty, display empty_watch list
                        Text(
                            text = stringResource(R.string.empty_watched),
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    // Display list of watched movies
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(watched, key = { it.id }) { movie ->
                            SavedMovieCard(
                                movie = movie,
                                onRemove = { viewModel.removeFromWatched(movie.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}