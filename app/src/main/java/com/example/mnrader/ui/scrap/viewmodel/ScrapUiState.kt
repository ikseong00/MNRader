package com.example.mnrader.ui.scrap.viewmodel

import com.example.mnrader.ui.home.model.AnimalDataType

data class ScrapUiState(
    val scrapList: List<ScrapModel> = emptyList(),
) {
    companion object {
        val dummyList = listOf(
            ScrapModel(
                id = 1L,
                name = "말티즈",
                address = "서울특별시",
                date = "2024-01-15",
                type = AnimalDataType.PORTAL_LOST,
                imageUrl = ""
            ),
            ScrapModel(
                id = 2L,
                name = "페르시안 고양이",
                address = "서울특별시",
                date = "2024-01-16",
                type = AnimalDataType.PORTAL_PROTECT,
                imageUrl = ""
            ),
            ScrapModel(
                id = 3L,
                name = "골든 리트리버",
                address = "서울특별시",
                date = "2024-01-17",
                type = AnimalDataType.MY_WITNESS,
                imageUrl = ""
            ),
            ScrapModel(
                id = 4L,
                name = "스코티시 폴드",
                address = "서울특별시",
                date = "2024-01-18",
                type = AnimalDataType.PORTAL_LOST,
                imageUrl = ""
            ),
        )
    }
}

data class ScrapModel(
    val id: Long = 0L,
    val name: String = "",
    val address: String = "",
    val date: String = "",
    val imageUrl: String = "",
    val type: AnimalDataType = AnimalDataType.PORTAL_LOST,
) 