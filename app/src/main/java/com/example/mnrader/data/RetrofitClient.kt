package com.example.mnrader.data

import android.content.Context
import com.example.mnrader.BuildConfig
import com.example.mnrader.data.datastore.TokenDataStore
import com.example.mnrader.data.interceptor.AuthInterceptor
import com.example.mnrader.data.service.AnimalService
import com.example.mnrader.data.service.AuthService
import com.example.mnrader.data.service.DataPortalService
import com.example.mnrader.data.service.HomeService
import com.example.mnrader.data.service.NaverService
import com.example.mnrader.data.service.UserAnimalService
import com.example.mnrader.data.service.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var tokenDataStore: TokenDataStore? = null

    fun initialize(context: Context) {
        tokenDataStore = TokenDataStore(context.applicationContext)
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .apply {
                tokenDataStore?.let { dataStore ->
                    addInterceptor(AuthInterceptor(dataStore))
                }
            }
            .build()
    }

    val dataPortalService: DataPortalService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.DATA_PORTAL_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DataPortalService::class.java)
    }

    val naverService: NaverService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.NAVER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NaverService::class.java)
    }

    val authService: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    val userService: UserService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }

    val homeService: HomeService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeService::class.java)
    }

    val animalService: AnimalService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AnimalService::class.java)
    }

    val userAnimalService: UserAnimalService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAnimalService::class.java)
    }
}