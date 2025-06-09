package com.example.mnrader.ui.notification.viewmodel

import com.example.mnrader.ui.home.model.AnimalDataType

data class NotificationUiState(
    val notificationList : List<NotificationModel> = emptyList(),
)

data class NotificationModel(
    val id: Long = 0L,
    val name: String = "",
    val address: String = "",
    val date: String = "",
    val type: AnimalDataType = AnimalDataType.LOST,
)