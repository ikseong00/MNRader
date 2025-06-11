package com.example.mnrader.ui.mypage.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface PetApiService {

    @Multipart
    @PATCH("user-animals/{animalId}")
    fun updatePet(
        @Path("animalId") animalId: Int,
        @Part("animal") animal: RequestBody,
        @Part("breed") breed: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("age") age: RequestBody,
        @Part("detail") detail: RequestBody,
        @Part img: MultipartBody.Part? = null
    ): Call<Void>
}
