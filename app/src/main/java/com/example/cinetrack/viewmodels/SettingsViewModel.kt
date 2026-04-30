package com.example.cinetrack.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cinetrack.services.MovieService
import com.example.cinetrack.services.SettingsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsService = SettingsService(application)
    private val movieService = MovieService(application)

    private val _isDarkMode = MutableStateFlow(settingsService.getDarkMode())
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    // Saves dark mode in shared preference
    fun toggleDarkMode(enabled: Boolean) {
        settingsService.setDarkMode(enabled)
        _isDarkMode.value = enabled
    }

    // Clears the watchlist
    fun clearWatchlist() {
        movieService.clearWatchList()
    }

    // Clears watched list
    fun clearWatched() {
        movieService.clearWatched()
    }
}