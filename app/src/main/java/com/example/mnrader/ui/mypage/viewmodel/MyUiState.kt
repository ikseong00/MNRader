package com.example.mnrader.ui.mypage.viewmodel

import com.example.mnrader.ui.mypage.viewmodel.MyAnimal.Companion.dummyMyAnimalList

data class MyUiState(
    val email: String = "email",
    val address: String = "seoul",
    val myAnimalList: List<MyAnimal> = dummyMyAnimalList,
)


data class MyAnimal(
    val imgUrl: String = "",
    val name: String = "",
    val id: Int = 0,
) {
    companion object {
        val dummyMyAnimalList = listOf(
            MyAnimal(
                imgUrl = "https://example.com/animal1.jpg",
                name = "코코"
            ),
            MyAnimal(
                imgUrl = "https://example.com/animal2.jpg",
                name = "바둑이"
            ),
            MyAnimal(
                imgUrl = "https://example.com/animal3.jpg",
                name = "몽이"
            )
        )
    }
}