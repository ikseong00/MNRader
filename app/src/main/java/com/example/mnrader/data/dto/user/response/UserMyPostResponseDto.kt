package com.example.mnrader.data.dto.user.response

import com.google.gson.annotations.SerializedName

data class UserMyPostResponseDto(
    @SerializedName("postAnimal")
    val postAnimal: List<MyPostAnimalDto>?
)

data class MyPostAnimalDto(
    @SerializedName("animalId")
    val animalId: Int,
    @SerializedName("status")
    val status: String?,
    @SerializedName("img")
    val img: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("city")
    val city: Int,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("occurredAt")
    val occurredAt: String?
) 