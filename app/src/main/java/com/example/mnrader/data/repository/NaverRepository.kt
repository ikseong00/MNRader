package com.example.mnrader.data.repository

import com.example.mnrader.data.RetrofitClient

class NaverRepository {
    val naverService = RetrofitClient.naverService

    suspend fun getLatLngByLocation(
        location: String
    ) = runCatching {
        naverService.reverseGeocode(query = location)
    }
}

