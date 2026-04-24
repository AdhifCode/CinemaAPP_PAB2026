package com.example.cinemaapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.cinemaapp.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ProfileUiState(
    val profile: UserProfile = UserProfile()
)

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState(profile = UserProfile(
        name            = "Budi Santoso",
        email           = "demo@cinemaapp.com",
        membershipTier  = "Gold",
        moviesWatched   = 47,
        ticketsBought   = 83,
        points          = 1240
    )))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
}