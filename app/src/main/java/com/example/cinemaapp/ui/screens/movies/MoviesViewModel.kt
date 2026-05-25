package com.example.cinemaapp.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaapp.data.model.Movie
import com.example.cinemaapp.data.model.MovieCategory
import com.example.cinemaapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MoviesUiState(
    val categories:    List<MovieCategory> = emptyList(),
    val popularMovies: List<Movie>         = emptyList(),
    val isLoading:     Boolean             = true,
    val selectedMovie: Movie?              = null,
    val showBottomSheet: Boolean           = false,
    val showAlertDialog: Boolean           = false
)

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(
                movieRepository.getMovieCategories(),
                movieRepository.getPopularMovies()
            ) { categories, movies ->
                MoviesUiState(
                    categories = categories,
                    popularMovies = movies,
                    isLoading = false
                )
            }.collect { newState ->
                _uiState.update { it.copy(
                    categories = newState.categories,
                    popularMovies = newState.popularMovies,
                    isLoading = newState.isLoading
                )}
            }
        }
    }

    fun onCategorySelected(categoryId: Int) {
        viewModelScope.launch {
            movieRepository.saveSelectedCategory(categoryId)
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
