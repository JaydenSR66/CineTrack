package com.example.cinetrack.services

import android.content.Context
import androidx.core.content.edit

class SettingsService(context: Context) {
    private val prefs = context.getSharedPreferences("cinetrack_settings", Context.MODE_PRIVATE)

    // Set dark mode
    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean("dark_mode", enabled).apply()
    }

    // Get dark mode
    fun getDarkMode(): Boolean {
        return prefs.getBoolean("dark_mode", false)
    }

    // Sets the language for the app
    fun setLanguage(languageCode: String) {
        prefs.edit { putString("language", languageCode) }
    }

    // Gets the language the user saved
    fun getLanguage(): String {
        return prefs.getString("language", "en") ?: "en"
    }
}