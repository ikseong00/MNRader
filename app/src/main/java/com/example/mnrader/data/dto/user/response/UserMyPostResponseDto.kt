package com.example.mnrader.data.dto.user.response

data class UserMyPostResponseDto(
    val postAnimal: List<MyPostAnimalDto>?
)

data class MyPostAnimalDto(
    val animalId: Int,
    val status: String,
    val img: String,
    val name: String,
    val city: Int,
    val gender: String,
    val occurredAt: String
) 