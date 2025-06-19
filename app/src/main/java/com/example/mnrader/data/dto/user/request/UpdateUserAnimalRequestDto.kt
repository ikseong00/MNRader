package com.example.mnrader.data.dto.user.request

data class UpdateUserAnimalRequestDto(
    val animal: Int,
    val breed: Int,
    val gender: Int, // MALE = 1, FEMALE = 2
    val age: Int,
    val detail: String
) 