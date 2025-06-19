package com.example.mnrader.data.repository

import android.content.Context
import com.example.mnrader.data.RetrofitClient

class AnimalRepository(private val context: Context) {
    private val animalService = RetrofitClient.animalService

    suspend fun getAnimalDetail(animalId: Long) = runCatching {
        animalService.getAnimalDetail(animalId)
    }
} 