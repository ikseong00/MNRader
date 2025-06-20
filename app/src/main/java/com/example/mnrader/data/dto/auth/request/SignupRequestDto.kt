package com.example.mnrader.data.dto.auth.request

import com.google.gson.annotations.SerializedName

data class SignupRequestDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("city")
    val city: Int
) 