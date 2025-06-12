package com.example.mnrader.data.repository

import android.content.Context
import com.example.mnrader.data.RetrofitClient
import com.example.mnrader.data.datastore.TokenDataStore
import com.example.mnrader.data.dto.auth.LoginRequestDto
import com.example.mnrader.data.dto.auth.SignupRequestDto

class AuthRepository(private val context: Context) {
    val service = RetrofitClient.authService
    private val tokenDataStore = TokenDataStore(context)

    suspend fun login(
        email: String,
        password: String
    ) = runCatching {
        val request = LoginRequestDto(
            email = email,
            password = password
        )
        val response = service.login(request)
        response.result ?: throw Exception("Login failed: No result returned")
        tokenDataStore.saveTokens(response.result.accessToken, response.result.refreshToken)

        response
    }

    suspend fun signup(
        cityNumber: Int,
        email: String,
        password: String
    ) = runCatching {
        val request = SignupRequestDto(
            cityNumber = cityNumber,
            email = email,
            password = password
        )

        service.signup(request)
    }

    suspend fun isLoggedIn(): Boolean {
        return tokenDataStore.isLoggedIn()
    }

    suspend fun getAccessToken(): String {
        return tokenDataStore.getAccessToken()
    }

    suspend fun saveTokens(accessToken: String, refreshToken: String) {
        tokenDataStore.saveTokens(accessToken, refreshToken)
    }
}