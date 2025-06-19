package com.example.mnrader.ui.animaldetail.viewmodel

import com.example.mnrader.ui.home.model.HomeAnimalData

data class PortalAnimalDetailUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val animalDetail: HomeAnimalData? = null,
    val isBookmarked: Boolean = false
) 