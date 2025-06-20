package com.example.mnrader.data.service

import com.example.mnrader.data.dto.animal.response.AnimalDetailResponseDto
import com.example.mnrader.data.dto.base.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface AnimalService {
    @GET("animals/detail/{animalId}")
    suspend fun getAnimalDetail(
        @Path("animalId") animalId: Long
    ): BaseResponse<AnimalDetailResponseDto>
    
    @POST("animals/scrap/{animalId}")
    suspend fun scrapAnimal(
        @Path("animalId") animalId: Int
    ): BaseResponse<Unit>
    
    @DELETE("animals/scrap/{animalId}")
    suspend fun unscrapAnimal(
        @Path("animalId") animalId: Int
    ): BaseResponse<Unit>
    
    @Multipart
    @POST("animals/register")
    suspend fun registerAnimal(
        @Part("status") status: RequestBody,
        @Part("name") name: RequestBody,
        @Part("contact") contact: RequestBody,
        @Part("breed") breed: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("address") address: RequestBody,
        @Part("occuredAt") occuredAt: RequestBody,
        @Part("detail") detail: RequestBody,
        @Part img: MultipartBody.Part
    ): BaseResponse<Unit>
} 