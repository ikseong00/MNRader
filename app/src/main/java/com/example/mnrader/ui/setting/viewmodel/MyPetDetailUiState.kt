package com.example.mnrader.ui.setting.viewmodel

import com.example.mnrader.ui.setting.model.UserAnimalDetailUiModel

data class MyPetDetailUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val animalDetail: UserAnimalDetailUiModel? = null
) 