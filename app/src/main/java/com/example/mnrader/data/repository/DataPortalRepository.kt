package com.example.mnrader.data.repository

import android.content.Context
import com.example.mnrader.data.RetrofitClient
import com.example.mnrader.data.database.MNRaderDatabase
import com.example.mnrader.data.datastore.CurrentUserDataStore
import com.example.mnrader.data.entity.ScrapEntity
import com.example.mnrader.data.entity.UserEntity
import com.example.mnrader.mapper.toEntityList
import com.example.mnrader.ui.home.model.AnimalDataType

class DataPortalRepository(private val context: Context) {
    val dataPortalService = RetrofitClient.dataPortalService
    private val database = MNRaderDatabase.getDatabase(context)
    private val lostAnimalDao = database.lostAnimalDao()
    private val scrapDao = database.scrapDao()
    private val userDao = database.userDao()
    private val currentUserDataStore = CurrentUserDataStore(context)

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

    // 사용자 관련 메소드들
    suspend fun insertUser(user: UserEntity) = runCatching {
        userDao.insertUser(user)
    }
    
    suspend fun loginUser(email: String, password: String) = runCatching {
        val user = userDao.getUserByEmailAndPassword(email, password)
        if (user != null) {
            currentUserDataStore.setCurrentUser(email)
        }
        user
    }
    
    suspend fun getCurrentUserEmail(): String? {
        return currentUserDataStore.getCurrentUser()
    }
    
    suspend fun logoutUser() {
        currentUserDataStore.clearCurrentUser()
    }
    
    // 초기 테스트 사용자 생성
    suspend fun initializeTestUser() = runCatching {
        val testUser = UserEntity(
            email = "test@gmail.com",
            password = "test123!",
            city = 1
        )
        userDao.insertUser(testUser)
        currentUserDataStore.setCurrentUser("test@gmail.com")
    }

    // 스크랩 관련 메소드들 (사용자별로 관리)
    suspend fun isScrapExist(animalId: String) = runCatching {
        val userEmail = getCurrentUserEmail() ?: return@runCatching false
        scrapDao.isScrapExistForUser(animalId, userEmail)
    }

    suspend fun insertScrap(scrapEntity: ScrapEntity) = runCatching {
        val userEmail = getCurrentUserEmail() ?: throw IllegalStateException("User not logged in")
        val uniqueId = "${scrapEntity.animalId}_$userEmail"
        val userScrapEntity = scrapEntity.copy(
            id = uniqueId,
            userEmail = userEmail
        )
        scrapDao.insertScrap(userScrapEntity)
    }

    suspend fun deleteScrapById(animalId: String) = runCatching {
        val userEmail = getCurrentUserEmail() ?: throw IllegalStateException("User not logged in")
        scrapDao.deleteScrapByAnimalIdAndUser(animalId, userEmail)
    }

    suspend fun getAllScraps() = runCatching {
        val userEmail = getCurrentUserEmail() ?: throw IllegalStateException("User not logged in")
        scrapDao.getAllScrapsByUser(userEmail)
    }
}