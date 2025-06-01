package com.example.mnrader.data.service

import com.example.mnrader.data.dto.NaverGeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverService {
    @GET("geocode")
    suspend fun reverseGeocode(
        @Header("X-NCP-APIGW-API-KEY-ID") clientId: String,
        @Header("X-NCP-APIGW-API-KEY") clientSecret: String,
        @Query("coords") coords: String, // "경도,위도" 형식
        @Query("output") output: String = "json",
        @Query("orders") orders: String = "roadaddr"
    ): NaverGeocodingResponse
} 