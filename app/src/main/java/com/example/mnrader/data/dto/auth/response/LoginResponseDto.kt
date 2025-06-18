package com.example.mnrader.data.dto.auth.response

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: LoginResultDto
)

data class LoginResultDto(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
) 