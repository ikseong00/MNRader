package com.example.mnrader.ui.mypost.viewmodel

import com.example.mnrader.ui.home.model.AnimalDataType
import com.example.mnrader.ui.home.model.Gender

data class MyPostUiState(
    val myPostList: List<MyPostModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    companion object {
        val dummyList = listOf(
            MyPostModel(
                id = 1L,
                name = "뽀미",
                address = "서울특별시",
                date = "2024-01-20",
                type = AnimalDataType.PORTAL_LOST,
                imageUrl = "",
            ),
            MyPostModel(
                id = 2L,
                name = "나비",
                address = "서울특별시",
                date = "2024-01-19",
                type = AnimalDataType.PORTAL_PROTECT,
                imageUrl = "",
            ),
            MyPostModel(
                id = 3L,
                name = "골댕이",
                address = "서울특별시",
                date = "2024-01-18",
                type = AnimalDataType.MY_WITNESS,
                imageUrl = "",
            ),
            MyPostModel(
                id = 4L,
                name = "치즈",
                address = "서울특별시",
                date = "2024-01-17",
                type = AnimalDataType.PORTAL_LOST,
                imageUrl = "",
            ),
        )
    }
}

data class MyPostModel(
    val id: Long = 0L,
    val name: String = "",
    val address: String = "",
    val date: String = "",
    val imageUrl: String = "",
    val gender: Gender = Gender.MALE,
    val type: AnimalDataType = AnimalDataType.PORTAL_LOST,
)
