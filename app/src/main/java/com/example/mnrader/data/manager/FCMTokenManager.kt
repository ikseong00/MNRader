package com.example.mnrader.data.manager

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

object FCMTokenManager {
    private const val TAG = "FCMTokenManager"
    
    /**
     * FCM 토큰을 비동기적으로 받아오는 함수
     */
    suspend fun getFCMTokenAsync(): String {
        return try {
            val token = FirebaseMessaging.getInstance().token.await()
            Log.d(TAG, "FCM Token retrieved: $token")
            token
        } catch (e: Exception) {
            Log.e(TAG, "FCM token retrieval failed", e)
            // 기본 더미 토큰 반환
            val dummyToken = "eMM9G3ttTd2YTJ9B5jNB8u:APA91bGxxhJefMYf_NBdNFkOOvbhtJOteLODfg083rBkJ1VP_eFKQRqQzJA9UbGEEJ47W1nDJ4kTRxSohxHrghFQv7a6oTf6SYYPZMWcq4immCUyppbqI21khEfARwXh1WmpVsLabJD_dummy"
            Log.w(TAG, "Using dummy token: $dummyToken")
            dummyToken
        }
    }
} 