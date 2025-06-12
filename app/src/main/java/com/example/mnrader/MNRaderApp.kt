package com.example.mnrader

import android.app.Application
import com.example.mnrader.data.RetrofitClient
import com.naver.maps.map.NaverMapSdk

class MNRaderApp: Application() {
    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NcpKeyClient(BuildConfig.NAVER_CLIENT_ID)
        RetrofitClient.initialize(this)
    }
}