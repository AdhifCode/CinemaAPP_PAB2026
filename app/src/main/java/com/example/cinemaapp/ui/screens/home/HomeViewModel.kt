package com.example.cinemaapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaapp.data.model.Coupon
import com.example.cinemaapp.data.repository.CouponRepository
import com.example.cinemaapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class HomeUiState(
    val userName: String          = "",
    val greeting: String          = "",
    val coupons: List<Coupon>     = emptyList(),
    val isLoading: Boolean        = true
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val couponRepository: CouponRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            combine(
                userRepository.userProfileFlow,
                couponRepository.getCoupons()
            ) { userProfile, coupons ->
                HomeUiState(
                    userName = userProfile?.name?.split(" ")?.firstOrNull() ?: "Guest",
                    greeting = getGreeting(),
                    coupons = coupons,
                    isLoading = false
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    private fun getGreeting(): String {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hour) {
            in 0..11 -> "Good Morning 👋"
            in 12..17 -> "Good Afternoon 👋"
            else -> "Good Night 👋"
        }
    }

    fun markCouponAsUsed(couponId: Int) {
        viewModelScope.launch {
            couponRepository.markCouponAsUsed(couponId)
        }
    }
}