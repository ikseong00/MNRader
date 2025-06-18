package com.example.mnrader.ui.mypage.viewmodel

data class MyUiState(
    val email: String = "",
    val address: String = "",
    val myAnimalList: List<MyAnimal> = emptyList(),
)


data class MyAnimal(
    val imgUrl: String = "",
    val name: String = "",
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