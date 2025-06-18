package com.example.mnrader.ui.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mnrader.model.City
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun updateCity(city: City) {
        _uiState.update { currentState ->
            currentState.copy(city = city)
        }
    }

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

    fun register() {
        viewModelScope.launch {
            // TODO : Register Api , set isRegistered to true on success
        }
    }
} 