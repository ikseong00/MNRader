package com.example.mnrader.ui.notification.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.repository.UserRepository
import com.example.mnrader.mapper.toNotificationAnimals
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    init {
        loadNotifications()
    }

    private fun loadNotifications(last: Int? = null) {
        _uiState.update {
            it.copy(isLoading = true, errorMessage = null)
        }

        viewModelScope.launch {
            val lastValue = last ?: userRepository.getLastAnimal()
            userRepository.getAlarmList(lastValue)
                .onSuccess { response ->
                    response.result?.let { alarmResponse ->
                        val notificationList = alarmResponse.toNotificationAnimals()
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                notificationList = notificationList,
                                lastPost = alarmResponse.lastPost
                            )
                        }
                    } ?: run {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "알림 데이터를 불러올 수 없습니다."
                            )
                        }
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "알림을 불러오는데 실패했습니다."
                        )
                    }
                }
        }
    }

    fun loadMoreNotifications() {
        val currentLastPost = _uiState.value.lastPost
        loadNotifications(currentLastPost)
    }

    fun retry() {
        loadNotifications(0)
    }

    fun clearError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}

class NotificationViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationViewModel::class.java)) {
            return NotificationViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}