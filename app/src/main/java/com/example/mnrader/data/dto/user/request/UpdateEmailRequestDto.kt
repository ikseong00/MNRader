package com.example.mnrader.data.dto.user.request

import com.google.gson.annotations.SerializedName

data class UpdateEmailRequestDto(
    @SerializedName("email")
    val email: String,
    @SerializedName("refreshToken")
    val refreshToken: String
) 