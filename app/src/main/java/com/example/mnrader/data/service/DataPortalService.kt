package com.example.mnrader.data.service

import com.example.mnrader.BuildConfig
import com.example.mnrader.data.dto.AbandonedResponseDto
import com.example.mnrader.data.dto.LostPortalResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface DataPortalService {
    @GET("abandonmentPublicService_v2/abandonmentPublic_v2")
    suspend fun getAbandonedAnimals(
        @Query("serviceKey") serviceKey: String = BuildConfig.DATA_PORTAL_SERVICE_KEY,
        @Query("_type") type: String = "json",
        @Query("bgnde") startDate: String = LocalDate.now().minusMonths(3).format(DateTimeFormatter.ofPattern("yyyyMMdd")),
        @Query("endde") endDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
        @Query("numOfRows") numOfRows: Int = 25,
        @Query("upr_cd") uprCd: String? = null,
        @Query("kind") kind: String? = null
    ): AbandonedResponseDto

    @GET("lossInfoService/lossInfo")
    suspend fun getLostAnimals(
        @Query("serviceKey") serviceKey: String = BuildConfig.DATA_PORTAL_SERVICE_KEY,
        @Query("_type") type: String = "json",
        @Query("bgnde") startDate: String = LocalDate.now().minusMonths(3).format(DateTimeFormatter.ofPattern("yyyyMMdd")),
        @Query("endde") endDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")),
        @Query("upr_cd") uprCd: String? = null,
        @Query("kind") kind: String? = null
    ): LostPortalResponseDto

    @GET("abandonmentPublicService_v2/abandonmentPublic_v2")
    suspend fun getAbandonedAnimalByDesertionNo(
        @Query("serviceKey") serviceKey: String = BuildConfig.DATA_PORTAL_SERVICE_KEY,
        @Query("_type") type: String = "json",
        @Query("desertionNo") desertionNo: String
    ): AbandonedResponseDto
}