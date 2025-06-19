package com.example.mnrader.data.dto.user.request

import com.google.gson.annotations.SerializedName

data class UpdateAlarmRequestDto(
    @SerializedName("enabled")
    val enabled: Boolean,
    @SerializedName("fcmToken")
    val fcmToken: String
) 