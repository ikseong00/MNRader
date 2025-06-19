package com.example.mnrader.data.dto.user.response

import com.google.gson.annotations.SerializedName

data class UserAnimalDetailResponseDto(
    @SerializedName("animal")
    val animal: String,
    @SerializedName("breed")
    val breed: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("img")
    val img: String
) 