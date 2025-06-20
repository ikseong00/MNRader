package com.example.mnrader.ui.onboarding.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.manager.FCMTokenManager
import com.example.mnrader.data.repository.AuthRepository
import com.example.mnrader.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val authRepository = AuthRepository(application)
    private val userRepository = UserRepository(application)
    
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
    
    fun login() {
        val currentState = _uiState.value
        
        // 입력값 검증
        if (currentState.email.isBlank() || currentState.password.isBlank()) {
            _uiState.update { 
                it.copy(errorMessage = "이메일과 비밀번호를 입력해주세요.")
            }
            return
        }
        
        viewModelScope.launch {
            authRepository.login(
                email = currentState.email.trim(),
                password = currentState.password.trim()
            ).onSuccess { response ->
                if (response.status == 200) {
                    // 로그인 성공 후 FCM 토큰 전송
                    sendFcmTokenToServer()
                    _uiState.update { 
                        it.copy(isLoggedIn = true, errorMessage = null)
                    }
                } else {
                    _uiState.update { 
                        it.copy(errorMessage = response.message)
                    }
                }
            }.onFailure { exception ->
                _uiState.update { 
                    it.copy(errorMessage = "로그인에 실패했습니다: ${exception.message}")
                }
            }
        }
    }
    
    private suspend fun sendFcmTokenToServer() {
        try {
            val fcmToken = FCMTokenManager.getFCMTokenAsync()
            Log.d("LoginViewModel", "FCM Token: $fcmToken")
            
            userRepository.sendFcmToken(fcmToken).onSuccess { response ->
                Log.d("LoginViewModel", "FCM 토큰 전송 성공: ${response.message}")
            }.onFailure { exception ->
                Log.e("LoginViewModel", "FCM 토큰 전송 실패", exception)
            }
        } catch (e: Exception) {
            Log.e("LoginViewModel", "FCM 토큰 획득 실패", e)
        }
    }
    
    fun clearError() {
        _uiState.update { currentState ->
            currentState.copy(errorMessage = null)
        }
    }
}


class LoginViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}