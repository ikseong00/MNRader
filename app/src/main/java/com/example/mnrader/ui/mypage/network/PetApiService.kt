package com.example.mnrader.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface PetApiService {

    @Multipart
    @POST("api/pets/update")
    fun updatePet(
        @Part image: MultipartBody.Part?,
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("species") species: RequestBody,
        @Part("breed") breed: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("age") age: RequestBody,
        @Part("description") description: RequestBody
    ): Call<Void>
}
