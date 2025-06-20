package com.example.mnrader.ui.animaldetail.viewmodel

import com.example.mnrader.ui.home.model.AnimalDataType

data class MNAnimalDetailUiState(
    val animalDetail: MNAnimalDetail = MNAnimalDetail(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class MNAnimalDetail(
    val status: AnimalDataType = AnimalDataType.MN_LOST,
    val img: String = "",
    val gender: String = "",
    val breed: String = "",
    val address: String = "",
    val contact: String = "",
    val detail: String = "",
    val date: String = "",
    val isScrapped: Boolean = false
) 