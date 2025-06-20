package com.example.mnrader.data.repository

import android.content.Context
import android.util.Log
import com.example.mnrader.data.RetrofitClient
import com.example.mnrader.data.database.MNRaderDatabase
import com.example.mnrader.data.datastore.CurrentUserDataStore
import com.example.mnrader.data.datastore.LastAnimalDataStore
import com.example.mnrader.data.datastore.TokenDataStore
import com.example.mnrader.data.dto.user.request.FcmTokenRequestDto
import com.example.mnrader.data.dto.user.request.UpdateAlarmRequestDto
import com.example.mnrader.data.dto.user.request.UpdateCityRequestDto
import com.example.mnrader.data.dto.user.request.UpdateEmailRequestDto
import com.example.mnrader.data.dto.user.response.UserScrapAnimalDto

class UserRepository(private val context: Context) {
    private val service = RetrofitClient.userService
    private val tokenDataStore = TokenDataStore(context)
    private val lastAnimalDataStore = LastAnimalDataStore(context)
    private val currentUserDataStore = CurrentUserDataStore(context)
    private val database = MNRaderDatabase.getDatabase(context)
    private val scrapDao = database.scrapDao()

    suspend fun getMyPage() = runCatching {
        service.getMyPage()
    }

    suspend fun updateEmail(email: String) = runCatching {
        val refreshToken = tokenDataStore.getRefreshToken()
        val request = UpdateEmailRequestDto(
            email = email,
            refreshToken = refreshToken
        )
        val response = service.updateEmail(request)

        // 새로운 토큰들을 저장
        response.result?.let { result ->
            tokenDataStore.saveTokens(
                result.accessToken,
                result.refreshToken
            )
        }

        response
    }

    suspend fun getScrapList() = runCatching {
        // 서버에서 스크랩 리스트 가져오기
        val remoteScrapDto = service.getScrapList()
        Log.d("UserRepository", "Remote scrap list size: ${remoteScrapDto.result?.postAnimal}")

        // 현재 사용자 이메일 가져오기
        val currentUserEmail = currentUserDataStore.getCurrentUser() ?: "test@gmail.com"
        
        // 로컬 Room에서 스크랩 리스트 가져오기
        val localScrapDto = scrapDao.getAllScrapsByUser(currentUserEmail).map { scrapEntity ->
            UserScrapAnimalDto(
                animalId = scrapEntity.animalId.toLongOrNull() ?: 0L,
                status = when (scrapEntity.animalType) {
                    com.example.mnrader.ui.home.model.AnimalDataType.PORTAL_LOST -> "LOST"
                    com.example.mnrader.ui.home.model.AnimalDataType.PORTAL_PROTECT -> "PROTECTED"
                    else -> "LOST"
                },
                img = scrapEntity.imageUrl ?: "",
                breed = scrapEntity.name,
                city = scrapEntity.city,
                gender = scrapEntity.gender,
                occurredAt = scrapEntity.date
            )
        }
        Log.d("UserRepository", "Local scrap list size: ${localScrapDto}")

        // 서버 스크랩과 로컬 스크랩 합치기
        val totalScraps = (remoteScrapDto.result?.postAnimal ?: emptyList()) + localScrapDto
        Log.d("UserRepository", "Total scrap list size: ${totalScraps}")
        totalScraps
    }

    suspend fun getMyPostList() = runCatching {
        service.getUserMyPosts()
    }

    suspend fun updateCity(cityId: Int) = runCatching {
        val request = UpdateCityRequestDto(city = cityId)
        service.updateCity(request)
    }

    suspend fun updateAlarm(enabled: Boolean, fcmToken: String) = runCatching {
        val request = UpdateAlarmRequestDto(
            enabled = enabled,
            fcmToken = fcmToken
        )
        service.updateAlarm(request)
    }

    suspend fun sendFcmToken(fcmToken: String) = runCatching {
        val request = FcmTokenRequestDto(fcmToken = fcmToken)
        service.sendFcmToken(request)
    }

    suspend fun getAlarmList(last: Int) = runCatching {
        service.getAlarmList(last)
    }

    fun getLastAnimal(): Int {
        return lastAnimalDataStore.getLastAnimal()
    }
} 