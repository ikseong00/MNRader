package com.example.mnrader.data.dto.animal.request

import com.google.gson.annotations.SerializedName

data class AnimalRegisterRequestDto(
    @SerializedName("status")
    val status: Int, // 1: 실종, 2: 목격
    @SerializedName("name")
    val name: String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("breed")
    val breed: Int,
    @SerializedName("gender")
    val gender: Int, // 1: 수컷, 2: 암컷
    @SerializedName("address")
    val address: String,
    @SerializedName("occuredAt")
    val occuredAt: String, // DateTime을 String으로 변환
    @SerializedName("detail")
    val detail: String
) 