package com.example.cinemaapp.ui.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemaapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignupUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val signupError: String? = null,
    val isLoading: Boolean = false,
    val isSignedUp: Boolean = false
)

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.value = _uiState.value.copy(name = name, nameError = null, signupError = null)
    }

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email, emailError = null, signupError = null)
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password, passwordError = null, signupError = null)
    }

    fun onConfirmPasswordChange(confirm: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirm, confirmPasswordError = null, signupError = null)
    }

    fun onSignup() {
        val state = _uiState.value

        var nameError: String? = null
        var emailError: String? = null
        var passwordError: String? = null
        var confirmPasswordError: String? = null

        if (state.name.isBlank()) nameError = "Nama tidak boleh kosong"
        else if (state.name.trim().length < 2) nameError = "Nama minimal 2 karakter"

        if (state.email.isBlank()) emailError = "Email tidak boleh kosong"
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(state.email).matches())
            emailError = "Masukkan alamat email yang valid"

        if (state.password.isBlank()) passwordError = "Password tidak boleh kosong"
        else if (state.password.length < 8) passwordError = "Password minimal 8 karakter"
        else if (!state.password.any { it.isDigit() }) passwordError = "Password harus mengandung angka"

        if (state.confirmPassword.isBlank()) confirmPasswordError = "Konfirmasi password diperlukan"
        else if (state.password != state.confirmPassword) confirmPasswordError = "Password tidak cocok"

        if (nameError != null || emailError != null || passwordError != null || confirmPasswordError != null) {
            _uiState.value = state.copy(
                nameError = nameError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, signupError = null)
            delay(1200)

            val result = userRepository.signup(state.name, state.email, state.password)

            result.onSuccess {
                _uiState.value = _uiState.value.copy(isLoading = false, isSignedUp = true)
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    signupError = error.message ?: "Signup failed. Please try again."
                )
            }
        }
    }
}