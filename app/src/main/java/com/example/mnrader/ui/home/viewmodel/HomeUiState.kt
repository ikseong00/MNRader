package com.example.mnrader.ui.home.viewmodel

import com.example.mnrader.ui.home.model.HomeAnimalData

data class HomeUiState(
    var mLocation: String = "",
    val animalDataList: List<HomeAnimalData> = emptyList(),
    var locationFilter: String = "",
    var breedFilter: String= "",
    var isLostShown: Boolean = true,
    var isProtectShown: Boolean = true,
    var isWitnessShown: Boolean = true,
    var isExpanded: Boolean = false,
    val notificationCount: Int = 0,
    var currentPinAnimal: HomeAnimalData? = null,
    val shownAnimalDataList: List<HomeAnimalData> = emptyList()
)
