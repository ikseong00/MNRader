package com.example.mnrader.data.dto.animal.response

import com.google.gson.annotations.SerializedName

data class AnimalDetailResponseDto(
    @SerializedName("status")
    val status: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("breed")
    val breed: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("occurredAt")
    val occurredAt: String,
    @SerializedName("isScrapped")
    val isScrapped: Boolean
) 