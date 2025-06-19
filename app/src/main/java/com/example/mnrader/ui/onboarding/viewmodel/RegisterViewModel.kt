package com.example.mnrader.ui.onboarding.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.repository.AuthRepository
import com.example.mnrader.model.City
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val authRepository = AuthRepository(application)

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
        val currentState = _uiState.value

        // 입력값 검증
        if (currentState.email.isBlank() || currentState.password.isBlank() || currentState.city == null) {
            _uiState.update {
                it.copy(errorMessage = "모든 필드를 입력해주세요.")
            }
            return
        }

        viewModelScope.launch {
            authRepository.signup(
                email = currentState.email.trim(),
                password = currentState.password.trim(),
                city = currentState.city.code
            ).onSuccess { response ->
                if (response.status == 200) {
                    _uiState.update {
                        it.copy(isRegistered = true, errorMessage = null)
                    }
                } else {
                    _uiState.update {
                        it.copy(errorMessage = response.message)
                    }
                }
            }.onFailure { exception ->
                _uiState.update {
                    it.copy(errorMessage = "회원가입에 실패했습니다: ${exception.message}")
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { currentState ->
            currentState.copy(errorMessage = null)
        }
    }
}

class RegisterViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}