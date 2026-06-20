package com.example.cinemaapp.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaapp.data.ApiService
import com.example.cinemaapp.data.MockData
import com.example.cinemaapp.data.model.Movie
import com.example.cinemaapp.data.model.MovieCategory
import com.example.cinemaapp.data.model.TmdbResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MoviesUiState(
    val categories:    List<MovieCategory> = emptyList(),
    val popularMovies: List<Movie>         = emptyList(),
    val nowShowingMovies: List<Movie>      = emptyList(),
    val isLoading:     Boolean             = false,
    val selectedMovie: Movie?              = null,
    val showBottomSheet: Boolean           = false,
    val showAlertDialog: Boolean           = false,
    val errorMessage: String?              = null
)

class MoviesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    private val apiService = ApiService.create()
    private val apiKey = "194b09e225cab0ce17e921eb9568c1c5"
    private val imageBaseUrl = "https://image.tmdb.org/t/p/w500/"

    init {
        loadCategories()
        fetchMoviesByCategory(1) // Default to first category
        fetchNowShowing()
    }

    private fun loadCategories() {
        _uiState.update { it.copy(categories = MockData.movieCategories) }
    }

    private fun fetchNowShowing() {
        viewModelScope.launch {
            try {
                val response = apiService.getNowPlayingMovies(apiKey)
                _uiState.update { it.copy(nowShowingMovies = mapTmdbResponse(response)) }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun onCategorySelected(categoryId: Int) {
        _uiState.update { state ->
            state.copy(
                categories = state.categories.map { cat ->
                    cat.copy(isSelected = cat.id == categoryId)
                }
            )
        }
        fetchMoviesByCategory(categoryId)
    }

    private fun fetchMoviesByCategory(categoryId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val response = when (categoryId) {
                    1 -> apiService.getPopularMovies(apiKey)
                    2 -> apiService.getNowPlayingMovies(apiKey)
                    3 -> apiService.getTopRatedMovies(apiKey)
                    4 -> apiService.getUpcomingMovies(apiKey)
                    else -> apiService.getPopularMovies(apiKey)
                }
                _uiState.update { it.copy(popularMovies = mapTmdbResponse(response), isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(
                        isLoading = false, 
                        errorMessage = "Failed to load movies: ${e.message}" 
                    ) 
                }
            }
        }
    }

    private fun mapTmdbResponse(response: TmdbResponse): List<Movie> {
        return response.results.map { tmdbMovie ->
            Movie(
                id = tmdbMovie.id,
                title = tmdbMovie.title,
                rating = tmdbMovie.voteAverage,
                genre = "Movie",
                duration = tmdbMovie.releaseDate ?: "N/A",
                posterUrl = imageBaseUrl + tmdbMovie.posterPath,
                description = tmdbMovie.overview
            )
        }
    }

    fun onMovieClicked(movie: Movie) {
        _uiState.update { it.copy(selectedMovie = movie, showBottomSheet = true) }
    }

    fun dismissBottomSheet() {
        _uiState.update { it.copy(showBottomSheet = false, selectedMovie = null) }
    }

    fun onSeeAllClicked() {
        _uiState.update { it.copy(showAlertDialog = true) }
    }

    fun dismissAlertDialog() {
        _uiState.update { it.copy(showAlertDialog = false) }
    }
}