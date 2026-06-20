package com.example.cinemaapp.ui.screens.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.cinemaapp.ui.components.*
import com.example.cinemaapp.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    onMovieClick: (movieTitle: String) -> Unit = {},
    viewModel: MoviesViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(DeepNavy),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Movies",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "What do you want to watch?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            if (uiState.isLoading) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = NeonGreen)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                SectionHeader(
                    title = "Categories",
                    actionLabel = "See all",
                    onActionClick = { viewModel.onSeeAllClicked() }
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.categories, key = { it.id }) { category ->
                        MovieCategoryChip(
                            category = category,
                            onClick = { viewModel.onCategorySelected(category.id) }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(28.dp))
                SectionHeader(
                    title = "Popular on TMDB",
                    actionLabel = "See all",
                    onActionClick = { viewModel.onSeeAllClicked() }
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(uiState.popularMovies, key = { it.id }) { movie ->
                        MoviePosterCard(
                            movie = movie,
                            onClick = { viewModel.onMovieClicked(movie) }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(28.dp))
                SectionHeader(
                    title = "Now Showing",
                    actionLabel = "See all",
                    onActionClick = { viewModel.onSeeAllClicked() }
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(uiState.nowShowingMovies, key = { "now_${it.id}" }) { movie ->
                        MoviePosterCard(
                            movie = movie,
                            onClick = { viewModel.onMovieClicked(movie) }
                        )
                    }
                }
            }
        }

        // --- Alert Dialog ---
        if (uiState.showAlertDialog) {
            AlertDialog(
                onDismissRequest = { viewModel.dismissAlertDialog() },
                confirmButton = {
                    TextButton(onClick = { viewModel.dismissAlertDialog() }) {
                        Text("OK", color = NeonGreen)
                    }
                },
                title = { Text("Information", color = TextPrimary) },
                text = { Text("This feature will be available in the next update!", color = TextSecondary) },
                containerColor = SurfaceDark,
                textContentColor = TextSecondary,
                titleContentColor = TextPrimary
            )
        }

        // --- Bottom Sheet ---
        if (uiState.showBottomSheet && uiState.selectedMovie != null) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.dismissBottomSheet() },
                sheetState = sheetState,
                containerColor = SurfaceDark,
                dragHandle = { BottomSheetDefaults.DragHandle(color = Color.Gray) }
            ) {
                val movie = uiState.selectedMovie!!
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .padding(bottom = 32.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (movie.posterUrl != null) {
                            AsyncImage(
                                model = movie.posterUrl,
                                contentDescription = movie.title,
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Image(
                                painter = painterResource(id = movie.posterRes),
                                contentDescription = movie.title,
                                modifier = Modifier
                                    .width(120.dp)
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.titleLarge,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = StarYellow, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "${movie.rating}", color = TextPrimary, style = MaterialTheme.typography.bodyMedium)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = movie.genre, color = TextSecondary, style = MaterialTheme.typography.bodySmall)
                            Text(text = "Release: ${movie.duration}", color = TextSecondary, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Synopsis",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movie.description.ifEmpty { "No description available for this movie yet." },
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                viewModel.dismissBottomSheet()
                                onMovieClick(movie.title)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonGreen),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Get Tickets", color = TextOnAccent, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}