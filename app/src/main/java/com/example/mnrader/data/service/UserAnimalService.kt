package com.example.mnrader.data.service

import com.example.mnrader.data.dto.base.BaseResponse
import com.example.mnrader.data.dto.user.response.UserAnimalDetailResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface UserAnimalService {
    @Multipart
    @POST("user-animals")
    suspend fun createUserAnimal(
        @Part("animal") animal: RequestBody,
        @Part("breed") breed: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("name") name: RequestBody,
        @Part("age") age: RequestBody,
        @Part("detail") detail: RequestBody,
        @Part img: MultipartBody.Part
    ): BaseResponse<Unit>
    
    @Multipart
    @PATCH("user-animals/{animalId}")
    suspend fun updateUserAnimal(
        @Path("animalId") animalId: Int,
        @Part("animal") animal: RequestBody,
        @Part("breed") breed: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("age") age: RequestBody,
        @Part("detail") detail: RequestBody,
        @Part img: MultipartBody.Part
    ): BaseResponse<Unit>
    
    @GET("user-animals/{animalId}")
    suspend fun getUserAnimalDetail(
        @Path("animalId") animalId: Int
    ): BaseResponse<UserAnimalDetailResponseDto>
} 