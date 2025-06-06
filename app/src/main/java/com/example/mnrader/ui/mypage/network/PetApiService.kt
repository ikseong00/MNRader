package com.example.mnrader.ui.mypage.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Path

interface PetApiService {

    @Multipart
    // 동물 정보 수정
    @PATCH("user-animals/{animalId}")
    fun updatePet(
        @Path("animalId") animalId: Int,
        @Part img: MultipartBody.Part?,
        @Part("animal") animal: RequestBody,
        @Part("breed") breed: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("age") age: RequestBody,
        @Part("detail") detail: RequestBody
    ): Call<Void>
}
