package com.example.mnrader.data.service

import com.example.mnrader.data.dto.auth.request.LoginRequestDto
import com.example.mnrader.data.dto.auth.request.SignupRequestDto
import com.example.mnrader.data.dto.auth.response.LoginResponseDto
import com.example.mnrader.data.dto.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/signup")
    suspend fun signup(
        @Body request: SignupRequestDto
    ): BaseResponse<Unit>

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): BaseResponse<LoginResponseDto>
}