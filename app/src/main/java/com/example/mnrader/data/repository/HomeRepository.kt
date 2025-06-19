package com.example.mnrader.data.repository

import android.content.Context
import com.example.mnrader.data.RetrofitClient
import com.example.mnrader.data.datastore.LastAnimalDataStore

class HomeRepository(context: Context) {
    private val homeService = RetrofitClient.homeService
    private val lastAnimalDataStore = LastAnimalDataStore(context)

    suspend fun getHomeData(breed: Int? = null, city: Int? = null) = runCatching {
        val response = homeService.getHomeData(breed = breed, city = city)
        
        // 성공적으로 데이터를 받아온 경우 lastAnimal 값을 저장
        response.result?.lastAnimal?.let { lastAnimal ->
            lastAnimalDataStore.saveLastAnimal(lastAnimal.toInt())
        }
        
        response
    }
} 