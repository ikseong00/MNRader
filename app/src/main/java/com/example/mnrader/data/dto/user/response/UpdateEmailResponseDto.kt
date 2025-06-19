package com.example.mnrader.data.dto.user.response

import com.google.gson.annotations.SerializedName

data class UpdateEmailResponseDto(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
) 