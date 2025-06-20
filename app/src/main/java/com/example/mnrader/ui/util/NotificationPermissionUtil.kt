package com.example.mnrader.ui.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

object NotificationPermissionUtil {
    
    private const val PREF_NAME = "notification_permission_prefs"
    private const val KEY_PERMISSION_REQUESTED = "permission_requested"
    
    /**
     * 알림 권한이 필요한지 확인
     * Android 13 (API 33) 이상에서만 필요
     */
    fun isNotificationPermissionRequired(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
    }
    
    /**
     * 알림 권한이 허용되어 있는지 확인
     */
    fun isNotificationPermissionGranted(context: Context): Boolean {
        return if (isNotificationPermissionRequired()) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            // Android 13 미만에서는 알림 권한이 자동으로 허용됨
            true
        }
    }
    
    /**
     * 알림 권한 문자열 반환
     */
    fun getNotificationPermission(): String {
        return Manifest.permission.POST_NOTIFICATIONS
    }
    
    /**
     * 권한 요청 여부를 SharedPreferences에 저장
     */
    fun setPermissionRequested(context: Context, requested: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_PERMISSION_REQUESTED, requested).apply()
    }
    
    /**
     * 권한 요청 여부를 SharedPreferences에서 조회
     */
    fun isPermissionRequested(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_PERMISSION_REQUESTED, false)
    }
    
    /**
     * 알림 권한 다이얼로그를 표시해야 하는지 확인
     * - Android 13 이상
     * - 권한이 허용되지 않음
     * - 이전에 권한을 요청한 적이 없음
     */
    fun shouldShowPermissionDialog(context: Context): Boolean {
        return isNotificationPermissionRequired() && 
               !isNotificationPermissionGranted(context) && 
               !isPermissionRequested(context)
    }
} 