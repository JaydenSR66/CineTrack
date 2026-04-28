package com.example.cinetrack

import android.os.Build
import com.example.cinetrack.BuildConfig
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.cinetrack.services.TmdbApiService
import com.example.cinetrack.ui.theme.CineTrackTheme
import com.google.gson.internal.GsonBuildConfig
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch{
            try{
                val response = TmdbApiService.api.getPopularMovies(apiKey = BuildConfig.TMDB_API_KEY)
                Log.d("TMDB TEST", "Success! First movie: ${response.results}")
            }
            catch (e: Exception){
                Log.d("TMDB TEST", "Error! ${e.message}")
            }
        }

        enableEdgeToEdge()
        setContent {
            CineTrackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CineTrackTheme {
        Greeting("Android")
    }
}