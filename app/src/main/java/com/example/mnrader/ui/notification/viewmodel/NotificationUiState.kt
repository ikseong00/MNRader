package com.example.mnrader.ui.notification.viewmodel

import com.example.mnrader.ui.home.model.AnimalDataType

data class NotificationUiState(
    val notificationList : List<NotificationModel> = emptyList(),
) {
    companion object {
        val dummyList = listOf(
            NotificationModel(
                id = 1L,
                name = "강아지",
                address = "서울시 강남구",
                date = "2023-10-01",
                type = AnimalDataType.LOST
            ),
            NotificationModel(
                id = 2L,
                name = "고양이",
                address = "서울시 서초구",
                date = "2023-10-02",
                type = AnimalDataType.PROTECT
            ),
            NotificationModel(
                id = 3L,
                name = "토끼",
                address = "서울시 송파구",
                date = "2023-10-03",
                type = AnimalDataType.WITNESS
            ),
        )
    }
}

data class NotificationModel(
    val id: Long = 0L,
    val name: String = "",
    val address: String = "",
    val date: String = "",
    val imageUrl: String = "",
    val type: AnimalDataType = AnimalDataType.LOST,
)