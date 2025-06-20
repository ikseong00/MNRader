package com.example.mnrader.ui.animaldetail.viewmodel

import com.example.mnrader.ui.animaldetail.model.PortalAnimalDetailData

data class PortalProtectDetailUiState(
    val animalDetail: PortalAnimalDetailData = PortalAnimalDetailData(),
    val isBookmarked: Boolean = false
) 