package com.example.mnrader.data.service

import com.example.mnrader.BuildConfig
import com.example.mnrader.data.dto.AbandonedResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface DataPortalService {
    @GET("abandonmentPublicService_v2/abandonmentPublic_v2")
    suspend fun getAbandonedAnimals(
        @Query("serviceKey") serviceKey: String = BuildConfig.DATA_PORTAL_SERVICE_KEY,
        @Query("_type") type: String = "json",
        @Query("bgnde") startDate: String = LocalDate.now().minusMonths(7).format(DateTimeFormatter.ofPattern("yyyyMMdd")),
        @Query("endde") endDate: String = LocalDate.now().minusMonths(6).format(DateTimeFormatter.ofPattern("yyyyMMdd")),
    ): AbandonedResponseDto
}