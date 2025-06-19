package com.example.mnrader.data.interceptor

import com.example.mnrader.data.datastore.TokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenDataStore: TokenDataStore
) : Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // 토큰을 가져와서 헤더에 추가
        val accessToken = runBlocking {
            tokenDataStore.getAccessToken()
        }
        
        val newRequest = if (accessToken.isNotEmpty()) {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        } else {
            originalRequest
        }
        
        return chain.proceed(newRequest)
    }
} 