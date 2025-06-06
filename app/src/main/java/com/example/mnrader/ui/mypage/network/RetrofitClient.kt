package com.example.mnrader.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val petService: PetApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://your-backend-url.com/") // 실제 서버 URL로 교체
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PetApiService::class.java)
    }
}
