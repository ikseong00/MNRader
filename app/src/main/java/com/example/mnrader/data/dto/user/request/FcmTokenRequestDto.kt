package com.example.mnrader.data.dto.user.request

import com.google.gson.annotations.SerializedName

data class FcmTokenRequestDto(
    @SerializedName("fcmToken")
    val fcmToken: String
) 