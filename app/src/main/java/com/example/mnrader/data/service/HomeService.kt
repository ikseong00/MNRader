package com.example.mnrader.data.service

import com.example.mnrader.data.dto.base.BaseResponse
import com.example.mnrader.data.dto.home.response.HomeResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("users/home")
    suspend fun getHomeData(
        @Query("breed") breed: Int? = null,
        @Query("city") city: Int? = null
    ): BaseResponse<HomeResponseDto>
} 