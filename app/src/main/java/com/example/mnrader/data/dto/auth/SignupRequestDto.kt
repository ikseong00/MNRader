package com.example.mnrader.data.dto.auth


import com.google.gson.annotations.SerializedName

data class SignupRequestDto(
    @SerializedName("city")
    val cityNumber: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)