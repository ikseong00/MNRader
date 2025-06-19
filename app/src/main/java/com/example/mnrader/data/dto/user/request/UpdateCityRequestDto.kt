package com.example.mnrader.data.dto.user.request

import com.google.gson.annotations.SerializedName

data class UpdateCityRequestDto(
    @SerializedName("city")
    val city: Int
) 