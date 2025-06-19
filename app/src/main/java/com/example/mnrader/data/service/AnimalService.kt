package com.example.mnrader.data.service

import com.example.mnrader.data.dto.animal.response.AnimalDetailResponseDto
import com.example.mnrader.data.dto.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimalService {
    @GET("animals/{animalId}")
    suspend fun getAnimalDetail(
        @Path("animalId") animalId: Long
    ): BaseResponse<AnimalDetailResponseDto>
} 