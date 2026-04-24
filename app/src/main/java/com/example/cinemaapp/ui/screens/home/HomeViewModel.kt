package com.example.cinemaapp.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.cinemaapp.data.MockData
import com.example.cinemaapp.data.model.Coupon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeUiState(
    val userName: String          = "Nadhifal",
    val greeting: String          = "Good Night 👋",
    val coupons: List<Coupon>     = emptyList(),
    val isLoading: Boolean        = false
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.value = _uiState.value.copy(
            coupons   = MockData.coupons,
            isLoading = false
        )
    }
}