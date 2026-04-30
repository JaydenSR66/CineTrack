package com.example.cinetrack.services

import com.example.cinetrack.BuildConfig
import com.example.cinetrack.models.MovieResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
Handles all communication with the TMDB API
 */
object TmdbApiService {
    // Base URL for TMDB API Requests
    private const val base_url = "https://api.themoviedb.org/3/"

    // Builds the HTTP client with the base URL and Gson converter
    private val Http_Client = Retrofit.Builder()
        .baseUrl(base_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Creates an instance of ApiInterface
    val api: ApiInterface by lazy {
        Http_Client.create(ApiInterface::class.java)
    }

    /*
    Get request to TMDB for returning list of popular movies
     */
    interface ApiInterface {

        // Gets the current popular movies
        @GET("movie/popular")
        suspend fun getPopularMovies(
            @Query("api_key") apiKey: String,
            @Query("page") page: Int = 1
        ): MovieResponse

        // Gets all movies sorted from A-Z
        @GET("discover/movie")
        suspend fun getAllMovies(
            @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
            @Query("sort_by") sortBy: String = "primary_release_date.desc",
            @Query("page") page: Int = 1
        ): MovieResponse

        // Searches movies by query
        @GET("search/movie")
        suspend fun searchMovies(
            @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
            @Query("query") query: String,
            @Query("page") page: Int = 1
        ): MovieResponse
    }
}