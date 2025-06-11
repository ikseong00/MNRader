package com.example.mnrader.data.service

import com.example.mnrader.BuildConfig
import com.example.mnrader.data.dto.NaverGeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverService {
    @GET("geocode")
    suspend fun reverseGeocode(
        @Header("X-NCP-APIGW-API-KEY-ID") clientId: String = BuildConfig.NAVER_CLIENT_ID,
        @Header("X-NCP-APIGW-API-KEY") clientSecret: String = BuildConfig.NAVER_MAP_CLIENT_SECRET,
        @Query("query") query: String,
    ): NaverGeocodingResponse
} 