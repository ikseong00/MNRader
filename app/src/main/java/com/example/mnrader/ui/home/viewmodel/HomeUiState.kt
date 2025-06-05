package com.example.mnrader.ui.home.viewmodel

import com.example.mnrader.ui.home.model.HomeAnimalData
import com.example.mnrader.ui.home.model.MapAnimalData
import com.naver.maps.geometry.LatLng

data class HomeUiState(
    var mLocation: String = "",
    val animalDataList: List<HomeAnimalData> = emptyList(),
    var locationFilter: String = "",
    var breedFilter: String= "",
    var isLostShown: Boolean = true,
    var isProtectShown: Boolean = true,
    var isWitnessShown: Boolean = true,
    var isExpanded: Boolean = false,
    val selectedAnimal: MapAnimalData? = null,
    val notificationCount: Int = 0,
    var currentPinAnimal: HomeAnimalData? = null,
    val shownAnimalDataList: List<HomeAnimalData> = emptyList(),
    val mapAnimalDataList: List<MapAnimalData> = emptyList(),
    val cameraLatLng: LatLng = LatLng(37.5407, 127.0791)
)
