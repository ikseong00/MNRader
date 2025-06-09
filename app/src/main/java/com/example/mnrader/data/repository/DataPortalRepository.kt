package com.example.mnrader.data.repository

import android.content.Context
import com.example.mnrader.data.RetrofitClient
import com.example.mnrader.data.database.MNRaderDatabase
import com.example.mnrader.mapper.toEntityList

class DataPortalRepository(private val context: Context) {
    val dataPortalService = RetrofitClient.dataPortalService
    private val database = MNRaderDatabase.getDatabase(context)
    private val lostAnimalDao = database.lostAnimalDao()

    suspend fun fetchAbandonedAnimals() = runCatching {
        dataPortalService.getAbandonedAnimals()
    }

    suspend fun fetchLostAnimals() = runCatching {
        val apiResponse = dataPortalService.getLostAnimals()
        
        val entityList = apiResponse.toEntityList()
        
        lostAnimalDao.deleteAll()
        lostAnimalDao.insertAll(entityList)
        
        lostAnimalDao.getAllLostAnimals()
    }
}