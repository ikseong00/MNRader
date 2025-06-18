package com.example.mnrader.ui.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    fun updateEmail(email: String) {
        _uiState.update { currentState ->
            currentState.copy(email = email)
        }
    }
    
    fun updatePassword(password: String) {
        _uiState.update { currentState ->
            currentState.copy(password = password)
        }
    }
    
    fun clearError() {
        _uiState.update { currentState ->
            currentState.copy(errorMessage = null)
        }
    }
    
    private fun setLoading(isLoading: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(isLoading = isLoading)
        }
    }
    
    private fun setError(errorMessage: String) {
        _uiState.update { currentState ->
            currentState.copy(errorMessage = errorMessage, isLoading = false)
        }
    }
} 