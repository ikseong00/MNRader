package com.example.mnrader.data.dto.user.response

import com.google.gson.annotations.SerializedName

data class UserAlarmResponseDto(
    @SerializedName("lastPost")
    val lastPost: Int,
    @SerializedName("animal")
    val animal: List<AlarmAnimalDto>?
)

data class AlarmAnimalDto(
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