package com.example.mnrader.ui.animaldetail.viewmodel

data class MNAnimalDetailUiState(
    val animalDetail: MNAnimalDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class MNAnimalDetail(
    val status: String = "",
    val img: String = "",
    val gender: String = "",
    val breed: String = "",
    val address: String = "",
    val contact: String = "",
    val detail: String = "",
    val isScrapped: Boolean = false
) 