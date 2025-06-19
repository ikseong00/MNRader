package com.example.mnrader.ui.notification.model

import com.example.mnrader.ui.home.model.AnimalDataType
import com.example.mnrader.ui.home.model.Gender

data class NotificationAnimalUiModel(
    val animalId: Long,
    val type: AnimalDataType,
    val imageUrl: String,
    val name: String,
    val address: String,
    val gender: Gender,
    val date: String
) 