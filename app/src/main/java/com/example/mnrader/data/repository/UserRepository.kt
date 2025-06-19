package com.example.mnrader.data.repository

import android.content.Context
import com.example.mnrader.data.RetrofitClient
import com.example.mnrader.data.datastore.LastAnimalDataStore
import com.example.mnrader.data.datastore.TokenDataStore
import com.example.mnrader.data.dto.user.request.UpdateAlarmRequestDto
import com.example.mnrader.data.dto.user.request.UpdateCityRequestDto
import com.example.mnrader.data.dto.user.request.UpdateEmailRequestDto

class UserRepository(private val context: Context) {
    private val service = RetrofitClient.userService
    private val tokenDataStore = TokenDataStore(context)
    private val lastAnimalDataStore = LastAnimalDataStore(context)

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
        service.getScrapList()
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

    suspend fun getAlarmList(last: Int) = runCatching {
        service.getAlarmList(last)
    }

    fun getLastAnimal(): Int {
        return lastAnimalDataStore.getLastAnimal()
    }
} 