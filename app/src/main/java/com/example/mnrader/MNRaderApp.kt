package com.example.mnrader

import android.app.Application
import com.example.mnrader.data.RetrofitClient
import com.example.mnrader.data.manager.NotificationManager
import com.example.mnrader.data.repository.DataPortalRepository
import com.google.firebase.FirebaseApp
import com.naver.maps.map.NaverMapSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MNRaderApp: Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Firebase 초기화
        FirebaseApp.initializeApp(this)
        
        // 알림 매니저 초기화
        NotificationManager.initialize(this)
        
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NcpKeyClient(BuildConfig.NAVER_CLIENT_ID)
        RetrofitClient.initialize(this)
        
        // 테스트 사용자 초기화
        initializeTestUser()
    }
    
    private fun initializeTestUser() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val dataPortalRepository = DataPortalRepository(this@MNRaderApp)
                dataPortalRepository.initializeTestUser()
            } catch (e: Exception) {
                // 이미 존재하는 사용자거나 다른 이유로 실패한 경우 무시
            }
        }
    }
}