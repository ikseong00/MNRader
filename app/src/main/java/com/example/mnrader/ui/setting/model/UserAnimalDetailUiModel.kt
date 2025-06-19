package com.example.mnrader.ui.setting.model

import com.example.mnrader.ui.home.model.Gender
import com.example.mnrader.ui.setting.screen.AnimalType

data class UserAnimalDetailUiModel(
    val animalType: AnimalType,
    val breed: String,
    val gender: Gender,
    val age: Int,
    val detail: String,
    val imageUrl: String
) 