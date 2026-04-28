package com.example.cinetrack.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cinetrack.R
import com.example.cinetrack.models.Movie
import com.example.cinetrack.viewmodels.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()){
    val movies by viewModel.movies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    when {
        // Display loading indicator while loading
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }

        // Display error message
        error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
            {
                Text(text = "Error: $error", color = MaterialTheme.colorScheme.error)
            }
        }

        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)){
                item{
                    Text(text = stringResource(R.string.popular_movies), style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 8.dp))
                }
                items(movies){ movie ->
                    MovieCard(movie = movie)
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie){
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ){
        Row(modifier = Modifier.padding(12.dp)){

            // Movie Poster
            AsyncImage(model = "https://image.tmdb.org/t/p/w200${movie.poster_path}", contentDescription = "${movie.title} poster", modifier = Modifier.width(80.dp).height(120.dp))

            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = "⭐ ${String.format("%.1f", movie.vote_average)}/10", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = movie.release_date, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface)

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = movie.overview, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface, maxLines=3, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}