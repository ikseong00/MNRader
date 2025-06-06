package com.example.mnrader.ui.mypage.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val petService: PetApiService by lazy {
        Retrofit.Builder()
            // PC에서 실행 중인 Spring 서버 (localhost:8080으로 전달)
            .baseUrl("http://10.0.2.2:8080/") // todo 실제 서버 도메인으로 수정
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PetApiService::class.java)
    }
}
