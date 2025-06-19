package com.example.mnrader.data.service

import com.example.mnrader.data.dto.base.BaseResponse
import com.example.mnrader.data.dto.user.request.UpdateAlarmRequestDto
import com.example.mnrader.data.dto.user.request.UpdateCityRequestDto
import com.example.mnrader.data.dto.user.request.UpdateEmailRequestDto
import com.example.mnrader.data.dto.user.response.UpdateEmailResponseDto
import com.example.mnrader.data.dto.user.response.UserMyPageResponseDto
import com.example.mnrader.data.dto.user.response.UserMyPostResponseDto
import com.example.mnrader.data.dto.user.response.UserAlarmResponseDto
import com.example.mnrader.data.dto.user.response.UserScrapResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface UserService {
    @GET("users/my/page")
    suspend fun getMyPage(): BaseResponse<UserMyPageResponseDto>

    @PATCH("users/my/email")
    suspend fun updateEmail(
        @Body request: UpdateEmailRequestDto
    ): BaseResponse<UpdateEmailResponseDto>

    @PATCH("users/my/city")
    suspend fun updateCity(
        @Body request: UpdateCityRequestDto
    ): BaseResponse<Unit>

    @GET("users/my/scrap")
    suspend fun getScrapList(): BaseResponse<UserScrapResponseDto>

    @GET("users/my/posts")
    suspend fun getUserMyPosts(): BaseResponse<UserMyPostResponseDto>

    @PATCH("users/alarm")
    suspend fun updateAlarm(
        @Body request: UpdateAlarmRequestDto
    ): BaseResponse<Unit>

    @GET("users/alarm")
    suspend fun getAlarmList(
        @Query("last") last: Int
    ): BaseResponse<UserAlarmResponseDto>
} 