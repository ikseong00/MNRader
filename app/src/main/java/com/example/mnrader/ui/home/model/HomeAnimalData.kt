package com.example.mnrader.ui.home.model

data class HomeAnimalData(
    val imageUrl: String,
    val name: String,
    val gender: Gender,
    val location: String,
    val date: String,
    val type: AnimalDataType,
    var isBookmarked: Boolean = false
) {
    companion object {
        val dummyHomeAnimalData = listOf(
            HomeAnimalData(
                name = "코코",
                type = AnimalDataType.PROTECT,
                gender = Gender.MALE,
                location = "서울 강남구",
                date = "2023-10-01",
                isBookmarked = true,
                imageUrl = ""
            ),
            HomeAnimalData(
                name = "바둑이",
                type = AnimalDataType.LOST,
                gender = Gender.MALE,
                location = "서울 강서구",
                date = "2023-10-02",
                isBookmarked = false,
                imageUrl = ""
            ),
            HomeAnimalData(
                name = "바둑이",
                type = AnimalDataType.WITNESS,
                gender = Gender.MALE,
                location = "서울 강서구",
                date = "2023-10-02",
                isBookmarked = false,
                imageUrl = ""
            ),
            HomeAnimalData(
                name = "바둑이",
                type = AnimalDataType.LOST,
                gender = Gender.MALE,
                location = "서울 강서구",
                date = "2023-10-02",
                isBookmarked = false,
                imageUrl = ""
            ),
        )
    }
}
