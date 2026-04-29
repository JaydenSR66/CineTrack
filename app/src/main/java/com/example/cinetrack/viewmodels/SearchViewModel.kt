package com.example.cinetrack.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinetrack.models.Movie
import com.example.cinetrack.services.TmdbApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/*
ViewModel for the search screen
Loads all movies from A-Z
 */
class SearchViewModel : ViewModel() {
    // Movies currently displayed
    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    // Stores all fetched movies
    private val _allMovies = MutableStateFlow<List<Movie>>(emptyList())

    // Checks if the API request is still fetching
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Tracks if more pages are being loaded
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore

    // Error messages
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    // The current pages and whether more pages exist
    private var currentPage = 1
    private var totalPages = 1

    init {
        GetMovies()
    }

    /*
    Gets movies from the API
     */
    fun GetMovies() {
        // If current pages is greater than total pages and is loading then return
        if (currentPage > totalPages || _isLoadingMore.value) return

        viewModelScope.launch {
            try {

                // Display the correct loading state
                if (currentPage == 1) {
                    _isLoading.value = true
                } else {
                    _isLoadingMore.value = true
                }

                // Request the API query
                val response = TmdbApiService.api.getAllMovies(page = currentPage)
                totalPages = response.total_pages // Update total available pages

                // Add new movies to the existing list
                val updateMovies = _allMovies.value + response.results
                _allMovies.value = updateMovies
                _searchResults.value = updateMovies

                // Move to next page
                currentPage++

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                // Reset loading state
                _isLoading.value = false
                _isLoadingMore.value = false
            }
        }
    }

    /*
    Searches movies based on query string
     */
    fun searchMovies(query: String) {
        // IF query is empty reset to full list
        if (query.isEmpty()) {
            _searchResults.value = _allMovies.value
            return
        }

        viewModelScope.launch {
            try {
                _isLoading.value = true // Display loading indicator

                // Request the API query for searching
                val response = TmdbApiService.api.searchMovies(query = query)

                // Update the results
                _searchResults.value = response.results
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}

