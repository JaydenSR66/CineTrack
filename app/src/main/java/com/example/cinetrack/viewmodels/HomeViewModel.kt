package com.example.cinetrack.viewmodels

import com.example.cinetrack.BuildConfig
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinetrack.models.Movie
import com.example.cinetrack.services.TmdbApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/*
    ViewModel For the Home Screen
 */
class HomeViewModel : ViewModel()
{
    // List of Movies
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies

    // Checks if the API request is still fetching
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Error messages
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Calls the GetPopularMovies when the ViewModel is created
    init {
        GetPopularMovies()
    }

    /*
    Gets the latest popular movies from  the API, returns them as a list if successful otherwise return error message
     */
    private fun GetPopularMovies()
    {
        viewModelScope.launch {
            try
            {
                val response = TmdbApiService.api.getPopularMovies(apiKey = BuildConfig.TMDB_API_KEY) // Get the most popular movies

                _movies.value = response.results
                _isLoading.value = false
            }
            catch (e: Exception)
            {
                _error.value = e.message // Display error message
                _isLoading.value = true
            }
        }
    }
}

