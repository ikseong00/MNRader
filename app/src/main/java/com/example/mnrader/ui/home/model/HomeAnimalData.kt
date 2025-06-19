package com.example.mnrader.ui.home.model

import com.naver.maps.geometry.LatLng

data class HomeAnimalData(
    val id: Long = 0L,
    val imageUrl: String?,
    val name: String,
    val location: String,
    val gender: Gender,
    val date: String,
    var latLng: LatLng = LatLng(37.5407, 127.0791),
    val type: AnimalDataType,
    var isBookmarked: Boolean = false
) {

    companion object {
        val dummyHomeAnimalData = listOf(
            HomeAnimalData(
                id = 1L,
                name = "코코",
                type = AnimalDataType.PORTAL_PROTECT,
                gender = Gender.MALE,
                location = "서울 강남구",
                date = "2023-10-01",
                latLng = LatLng(37.5408, 127.0792),
                isBookmarked = true,
                imageUrl = ""
            ),
            HomeAnimalData(
                id = 2L,
                name = "바둑이2",
                type = AnimalDataType.PORTAL_LOST,
                gender = Gender.MALE,
                location = "서울 강서구",
                date = "2023-10-02",
                latLng = LatLng(37.5409, 127.0793),
                isBookmarked = false,
                imageUrl = ""
            ),
            HomeAnimalData(
                id = 3L,
                name = "바둑이3",
                type = AnimalDataType.MY_WITNESS,
                gender = Gender.MALE,
                location = "서울 강서구",
                date = "2023-10-02",
                latLng = LatLng(37.5410, 127.0794),
                isBookmarked = false,
                imageUrl = ""
            ),
            HomeAnimalData(
                id = 4L,
                name = "바둑이4",
                type = AnimalDataType.PORTAL_LOST,
                gender = Gender.MALE,
                location = "서울 강서구",
                date = "2023-10-02",
                latLng = LatLng(37.5411, 127.0795),
                isBookmarked = false,
                imageUrl = ""
            ),
        )
    }
}

data class ItemData(val name: String, val category: String)
