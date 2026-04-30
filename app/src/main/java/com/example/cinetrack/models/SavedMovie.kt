package com.example.cinetrack.models

/*
Represents a movie that the user has saved locally on their device
 */
data class SavedMovie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val isWatched: Boolean
)
