package com.example.cinetrack.services

import android.content.Context

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
}