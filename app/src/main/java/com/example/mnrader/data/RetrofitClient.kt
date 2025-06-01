package com.example.mnrader.data

import com.example.mnrader.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val DATA_PORTAL_BASE_URL = BuildConfig.DATA_PORTAL_BASE_URL

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val dataPortalRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(DATA_PORTAL_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dataPortalService: DataPortalService by lazy {
        dataPortalRetrofit.create(DataPortalService::class.java)
    }

}