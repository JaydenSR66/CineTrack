package com.example.cinetrack.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cinetrack.models.Movie
import com.example.cinetrack.models.SavedMovie
import com.example.cinetrack.services.MovieService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/*
ViewModel responsible for managing user's watchlist and watched movies
 */
class MyWatchListViewModel(application: Application) : AndroidViewModel(application) {
    private val movieService = MovieService(application)

    // List of movies the user wants to watch
    private val _watchlist = MutableStateFlow<List<SavedMovie>>(emptyList())
    val watchList: StateFlow<List<SavedMovie>> = _watchlist

    // List of movies the user has watched
    private val _watched = MutableStateFlow<List<SavedMovie>>(emptyList())
    val watched: StateFlow<List<SavedMovie>> = _watched

    init {
        loadLists()
    }

    // Loads both watchlist and watched lists from the movie service
    private fun loadLists() {
        _watchlist.value = movieService.getWatchList()
        _watched.value = movieService.getWatched()
    }

    // Add a movie to the watch list
    fun addToWatchList(movie: Movie) {
        movieService.addToWatchList(
            SavedMovie(
                id = movie.id,
                title = movie.title,
                posterPath = movie.poster_path,
                isWatched = false // Mark as not watched
            )
        )
        loadLists()
    }

    // Add a movie to the watched list
    fun markAsWatched(movie: Movie) {
        movieService.markAsWatched(
            SavedMovie(
                id = movie.id,
                title = movie.title,
                posterPath = movie.poster_path,
                isWatched = true // Mark as watched
            )
        )
        loadLists()
    }

    // Removes a movie from watchlist
    fun removeFromWatchList(movieId: Int) {
        movieService.removeFromWatchList(movieId)
        loadLists()
    }

    // Removes a movie from watched
    fun removeFromWatched(movieId: Int) {
        movieService.removeFromWatched(movieId)
        loadLists()
    }


    // Check to see what list a movie is in
    fun isOnWatchList(movieId: Int) = movieService.isOnWatchList(movieId)
    fun isWatched(movieId: Int) = movieService.isWatched(movieId)
}