package com.example.mnrader.data.repository

import com.example.mnrader.data.RetrofitClient

class DataPortalRepository() {
    val dataPortalService = RetrofitClient.dataPortalService

    suspend fun fetchAbandonedAnimals() = runCatching {
        dataPortalService.getAbandonedAnimals()
    }
}