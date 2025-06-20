package com.example.mnrader.ui.home.model

import com.naver.maps.geometry.LatLng

data class MapAnimalData(
    val id: Long = 0L,
    val imageUrl: String?,
    var name: String,
    val location: String,
    val gender: Gender,
    val date: String,
    var latLng: LatLng = LatLng(37.5407, 127.0791),
    val type: AnimalDataType,
    val count: Int = 0,
    var isBookmarked: Boolean = false
)
