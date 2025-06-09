package com.example.mnrader.data.repository

import com.example.mnrader.data.RetrofitClient
import com.example.mnrader.data.dto.auth.LoginRequestDto
import com.example.mnrader.data.dto.auth.SignupRequestDto

class AuthRepository {
    val service = RetrofitClient.authService

    suspend fun login(
        email: String,
        password: String
    ) = runCatching {
        val request = LoginRequestDto(
            email = email,
            password = password
        )
        service.login(request)
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
}