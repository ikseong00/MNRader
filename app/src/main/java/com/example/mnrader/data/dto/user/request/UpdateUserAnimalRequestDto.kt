package com.example.mnrader.data.dto.user.request

import com.google.gson.annotations.SerializedName

data class UpdateUserAnimalRequestDto(
    @SerializedName("animal")
    val animal: Int,
    @SerializedName("breed")
    val breed: Int,
    @SerializedName("gender")
    val gender: Int, // MALE = 1, FEMALE = 2
    @SerializedName("age")
    val age: Int,
    @SerializedName("detail")
    val detail: String
) 