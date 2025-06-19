package com.example.mnrader.ui.notification.viewmodel

import com.example.mnrader.ui.notification.model.NotificationAnimalUiModel

data class NotificationUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val notificationList: List<NotificationAnimalUiModel> = emptyList(),
    val lastPost: Int = 0
)