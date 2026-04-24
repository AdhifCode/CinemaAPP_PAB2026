package com.example.cinemaapp.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val loginError: String? = null,
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false
)

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // Demo credentials
    private val validEmail    = "nadhifalazharuddiya@gmail.com"
    private val validPassword = "nadhif123"

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, emailError = null, loginError = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, passwordError = null, loginError = null)
    }

    fun onLogin() {
        val state = _uiState.value

        // Validation
        var emailError: String? = null
        var passwordError: String? = null

        if (state.email.isBlank()) {
            emailError = "Email is required"
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches()) {
            emailError = "Enter a valid email address"
        }

        if (state.password.isBlank()) {
            passwordError = "Password is required"
        } else if (state.password.length < 6) {
            passwordError = "Password must be at least 6 characters"
        }

        if (emailError != null || passwordError != null) {
            _uiState.value = state.copy(emailError = emailError, passwordError = passwordError)
            return
        }

        // Simulate network call
        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, loginError = null)
            delay(1500) // Simulate API delay

            if (state.email == validEmail && state.password == validPassword) {
                _uiState.value = _uiState.value.copy(isLoading = false, isLoggedIn = true)
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    loginError = "Invalid email or password. Try the demo credentials below."
                )
            }
        }
    }

    fun onSocialLogin(provider: String) {
        // Simulate social login success
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            delay(1000)
            _uiState.value = _uiState.value.copy(isLoading = false, isLoggedIn = true)
        }
    }
}