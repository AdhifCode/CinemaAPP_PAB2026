package com.example.cinemaapp.ui.screens.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cinemaapp.ui.components.*
import com.example.cinemaapp.ui.theme.*

@Composable
fun MoviesScreen(
    onMovieClick: (movieId: Int) -> Unit = {},
    viewModel: MoviesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        // ── Screen title ──────────────────────────────────────────────────────
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text     = "Movies",
                style    = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color    = TextPrimary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text     = "What do you want to watch?",
                style    = MaterialTheme.typography.bodyMedium,
                color    = TextSecondary,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }

        // ── Categories header ─────────────────────────────────────────────────
        item {
            Spacer(modifier = Modifier.height(24.dp))
            SectionHeader(title = "Categories", actionLabel = "See all")
            Spacer(modifier = Modifier.height(14.dp))
        }

        // ── Category chips horizontal row ─────────────────────────────────────
        item {
            LazyRow(
                contentPadding        = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.categories, key = { it.id }) { category ->
                    MovieCategoryChip(
                        category = category,
                        onClick  = { viewModel.onCategorySelected(category.id) }
                    )
                }
            }
        }

        // ── Popular header ────────────────────────────────────────────────────
        item {
            Spacer(modifier = Modifier.height(28.dp))
            SectionHeader(title = "Popular", actionLabel = "See all")
            Spacer(modifier = Modifier.height(14.dp))
        }

        // ── Movie poster carousel ─────────────────────────────────────────────
        item {
            LazyRow(
                contentPadding        = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(uiState.popularMovies, key = { it.id }) { movie ->
                    MoviePosterCard(
                        movie   = movie,
                        onClick = { onMovieClick(movie.id) }
                    )
                }
            }
        }

        // ── Now Showing header ────────────────────────────────────────────────
        item {
            Spacer(modifier = Modifier.height(28.dp))
            SectionHeader(title = "Now Showing", actionLabel = "See all")
            Spacer(modifier = Modifier.height(14.dp))
        }

        // ── Second movie row (reversed for variety) ───────────────────────────
        item {
            LazyRow(
                contentPadding        = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(uiState.popularMovies.reversed(), key = { "ns_${it.id}" }) { movie ->
                    MoviePosterCard(
                        movie   = movie,
                        onClick = { onMovieClick(movie.id) }
                    )
                }
            }
        }
    }
}