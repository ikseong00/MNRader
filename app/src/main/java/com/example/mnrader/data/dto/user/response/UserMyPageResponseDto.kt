package com.example.mnrader.data.dto.user.response

import com.google.gson.annotations.SerializedName

data class UserMyPageResponseDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("city")
    val city: Int,
    @SerializedName("myAnimal")
    val myAnimal: List<MyAnimalDto>?
)

data class MyAnimalDto(
    @SerializedName("animalUserId")
    val animalUserId: Int,
    @SerializedName("img")
    val img: String,
    @SerializedName("name")
    val name: String
) 