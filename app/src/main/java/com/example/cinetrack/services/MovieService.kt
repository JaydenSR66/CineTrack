package com.example.cinetrack.services

import android.content.Context
import androidx.core.content.edit
import com.example.cinetrack.models.SavedMovie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MovieService(context: Context) {
    private val prefs = context.getSharedPreferences("cinetrack_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    private fun saveWatchList(movies: List<SavedMovie>) {
        prefs.edit { putString("watchList", gson.toJson(movies)) }
    }

    private fun savedWatched(movies: List<SavedMovie>) {
        prefs.edit { putString("watched", gson.toJson(movies)) }
    }

    // Get the watch list from shared prefs
    fun getWatchList(): List<SavedMovie> {
        val json = prefs.getString("watchList", null) ?: return emptyList()
        val type = object : TypeToken<List<SavedMovie>>() {}.type
        return gson.fromJson(json, type)
    }

    // Get the watched list from shared prefs
    fun getWatched(): List<SavedMovie> {
        val json = prefs.getString("watched", null) ?: return emptyList()
        val type = object : TypeToken<List<SavedMovie>>() {}.type
        return gson.fromJson(json, type)
    }

    // Add to watch list
    fun addToWatchList(Movie: SavedMovie) {
        val current = getWatchList().toMutableList()

        if (current.none { it.id == Movie.id }) {
            current.add(Movie.copy(isWatched = false))
            saveWatchList(current)
        }
    }

    // Add to watched list
    fun markAsWatched(movie: SavedMovie) {
        val current = getWatched().toMutableList()

        if (current.none { it.id == movie.id }) {
            current.add(movie.copy(isWatched = true))
            savedWatched(current)
        }
    }

    // Removes from watchlist
    fun removeFromWatchList(movieID: Int) {
        val current = getWatchList().toMutableList()

        current.removeAll { it.id == movieID }
        saveWatchList(current)
    }

    // Removes from watched
    fun removeFromWatched(movieID: Int) {
        val current = getWatched().toMutableList()

        current.removeAll { it.id == movieID }
        savedWatched(current)
    }

    fun isOnWatchList(movieId: Int) = getWatchList().any { it.id == movieId }
    fun isWatched(movieId: Int) = getWatched().any { it.id == movieId }
}