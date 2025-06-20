package com.example.mnrader.data.dto.user.response

import com.google.gson.annotations.SerializedName

data class UserScrapResponseDto(
    @SerializedName("postAnimal")
    val postAnimal: List<UserScrapAnimalDto>?
)

data class UserScrapAnimalDto(
    @SerializedName("animalId")
    val animalId: Long,
    @SerializedName("status")
    val status: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("breed")
    val breed: String,
    @SerializedName("city")
    val city: Int,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("occurredAt")
    val occurredAt: String
) 