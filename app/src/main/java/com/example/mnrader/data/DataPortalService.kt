package com.example.mnrader.data

import com.example.mnrader.data.dto.AbandonedResponseDto
import retrofit2.http.GET

interface DataPortalService {
    @GET("abandonmentPublicService_v2")
    suspend fun getAbandonedAnimals(): AbandonedResponseDto
}