package com.example.mnrader.data.dto.animal.response

import com.google.gson.annotations.SerializedName

data class ScrapResponseDto(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String
) 