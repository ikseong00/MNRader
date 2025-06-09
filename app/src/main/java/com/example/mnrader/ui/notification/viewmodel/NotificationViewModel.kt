package com.example.mnrader.ui.notification.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NotificationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getNotificationList()
    }

    private fun getNotificationList() {
        _uiState.update {
            it.copy(
                notificationList = NotificationUiState.dummyList
            )
        }
    }

}