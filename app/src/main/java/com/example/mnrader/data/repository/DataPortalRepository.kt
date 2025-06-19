package com.example.mnrader.data.repository

import android.content.Context
import com.example.mnrader.data.RetrofitClient
import com.example.mnrader.data.database.MNRaderDatabase
import com.example.mnrader.mapper.toEntityList

class DataPortalRepository(private val context: Context) {
    val dataPortalService = RetrofitClient.dataPortalService
    private val database = MNRaderDatabase.getDatabase(context)
    private val lostAnimalDao = database.lostAnimalDao()

    suspend fun fetchAbandonedAnimals(uprCd: String? = null, kind: String? = null) = runCatching {
        dataPortalService.getAbandonedAnimals(uprCd = uprCd, kind = kind)
    }

    suspend fun fetchLostAnimals(uprCd: String? = null, kind: String? = null) = runCatching {
        val apiResponse = dataPortalService.getLostAnimals(uprCd = uprCd, kind = kind)

        val entityList = apiResponse.toEntityList()

        lostAnimalDao.deleteAll()
        lostAnimalDao.insertAll(entityList)

        lostAnimalDao.getAllLostAnimals()
    }

    suspend fun getLostAnimalById(id: Long) = runCatching {
        lostAnimalDao.getLostAnimalById(id)
    }

    suspend fun getAbandonedAnimalByDesertionNo(desertionNo: String) = runCatching {
        dataPortalService.getAbandonedAnimalByDesertionNo(
            desertionNo = desertionNo
        )
    }
}