package com.example.cinetrack.models

/*
Represents a movie object returned from TMDB API
 */
data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String,
    val vote_average: Double,
    val vote_count: Int,
    val popularity: Double,
    val genre_ids: List<Int>
)

// Represents the API response from TMDB
data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
