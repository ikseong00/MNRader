package com.example.mnrader.data.dto.user.request

data class UserAnimalRequestDto(
    val animal: Int,
    val breed: Int,
    val gender: Int, // MALE = 1, FEMALE = 2
    val name: String,
    val age: Int,
    val detail: String
) 