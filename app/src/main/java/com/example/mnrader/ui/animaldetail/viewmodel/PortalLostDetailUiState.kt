package com.example.mnrader.ui.animaldetail.viewmodel

import com.example.mnrader.ui.animaldetail.model.PortalAnimalDetailData

data class PortalLostDetailUiState(
    val animalDetail: PortalAnimalDetailData= PortalAnimalDetailData(),
    val isBookmarked: Boolean = false
) 