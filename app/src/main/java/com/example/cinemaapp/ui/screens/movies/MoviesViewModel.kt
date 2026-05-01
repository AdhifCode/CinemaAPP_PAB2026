package com.example.cinemaapp.ui.screens.movies

import androidx.lifecycle.ViewModel
import com.example.cinemaapp.data.MockData
import com.example.cinemaapp.data.model.Movie
import com.example.cinemaapp.data.model.MovieCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MoviesUiState(
    val categories:    List<MovieCategory> = emptyList(),
    val popularMovies: List<Movie>         = emptyList(),
    val isLoading:     Boolean             = false,
    val selectedMovie: Movie?              = null,
    val showBottomSheet: Boolean           = false,
    val showAlertDialog: Boolean           = false
)

class MoviesViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.value = _uiState.value.copy(
            categories    = MockData.movieCategories,
            popularMovies = MockData.popularMovies,
            isLoading     = false
        )
    }

    fun onCategorySelected(categoryId: Int) {
        _uiState.update { state ->
            state.copy(
                categories = state.categories.map { cat ->
                    cat.copy(isSelected = cat.id == categoryId)
                }
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
