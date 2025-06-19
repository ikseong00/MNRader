package com.example.mnrader.data.dto.home.response

import com.google.gson.annotations.SerializedName

data class HomeResponseDto(
    @SerializedName("lastAnimal")
    val lastAnimal: Long,
    @SerializedName("animal")
    val animal: List<HomeAnimalDto>?
)

data class HomeAnimalDto(
    @SerializedName("animalId")
    val animalId: Long,
    @SerializedName("status")
    val status: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("city")
    val city: Int,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("occurredAt")
    val occurredAt: String
) 