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
        name            = "Nadhifal Azharuddiya",
        email           = "nadhifalazharuddiya@gmail.com",
        membershipTier  = "Bronze",
        moviesWatched   = 0,
        ticketsBought   = 0,
        points          = 0
    )))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
}