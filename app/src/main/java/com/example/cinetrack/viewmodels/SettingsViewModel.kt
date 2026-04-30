package com.example.cinetrack.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cinetrack.services.MovieService
import com.example.cinetrack.services.SettingsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// This guide was used to help with the multiple languages support: https://www.youtube.com/watch?v=MXTsj43Csp4

/*
ViewModel that handles the user settings such as dark mode, data management and languages
 */
class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val settingsService = SettingsService(application)
    private val movieService =
        MovieService(application) // Services responsible for movie related data

    private val _isDarkMode = MutableStateFlow(settingsService.getDarkMode())
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    // List of supported languages
    val supportedLanguages = listOf(
        Pair("en", "English"),
        Pair("es", "Spanish"),
        Pair("fr", "French"),
        Pair("ru", "Russian"),
        Pair("hi", "Hindi")
    )

    // Current selected language
    private val _language = MutableStateFlow(settingsService.getLanguage())
    val language: StateFlow<String> = _language

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

    // Sets the users language
    fun setLanguage(languageCode: String) {
        settingsService.setLanguage(languageCode)
        _language.value = languageCode
    }
}