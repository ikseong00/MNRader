package com.example.mnrader.data.manager

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * FCM 알림을 관리하고 앱 내 알림 상태를 처리하는 매니저
 */
object NotificationManager {
    private const val TAG = "NotificationManager"
    private const val PREFS_NAME = "notification_prefs"
    private const val KEY_UNREAD_COUNT = "unread_count"
    
    // 새로운 알림이 도착했을 때 앱에 알리기 위한 Flow
    private val _newNotificationFlow = MutableSharedFlow<NotificationData>()
    val newNotificationFlow: SharedFlow<NotificationData> = _newNotificationFlow.asSharedFlow()
    
    // 읽지 않은 알림 개수 Flow
    private val _unreadCountFlow = MutableSharedFlow<Int>()
    val unreadCountFlow: SharedFlow<Int> = _unreadCountFlow.asSharedFlow()
    
    private var context: Context? = null
    private val sharedPrefs: SharedPreferences?
        get() = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    fun initialize(context: Context) {
        this.context = context.applicationContext
    }
    
    /**
     * FCM 메시지를 받았을 때 호출되는 함수
     */
    suspend fun handleFCMMessage(
        title: String?,
        body: String?,
        data: Map<String, String>
    ) {
        Log.d(TAG, "FCM 메시지 처리: title=$title, body=$body, data=$data")
        
        val notificationData = NotificationData(
            title = title ?: "멍냥레이더",
            body = body ?: "새로운 알림이 있습니다",
            animalId = data["animalId"]?.toLongOrNull(),
            type = data["type"] ?: "general",
            timestamp = System.currentTimeMillis()
        )
        
        // 읽지 않은 알림 개수 증가
        incrementUnreadCount()
        
        // 앱에 새로운 알림 전달
        _newNotificationFlow.emit(notificationData)
    }
    
    /**
     * 읽지 않은 알림 개수 가져오기
     */
    fun getUnreadCount(): Int {
        return sharedPrefs?.getInt(KEY_UNREAD_COUNT, 0) ?: 0
    }
    
    /**
     * 읽지 않은 알림 개수 증가
     */
    private fun incrementUnreadCount() {
        val currentCount = getUnreadCount()
        val newCount = currentCount + 1
        sharedPrefs?.edit()?.putInt(KEY_UNREAD_COUNT, newCount)?.apply()
        
        // 알림 개수 업데이트 전파
        _unreadCountFlow.tryEmit(newCount)
        
        Log.d(TAG, "읽지 않은 알림 개수 업데이트: $newCount")
    }
    
    /**
     * 알림을 읽음으로 표시 (알림 화면을 열었을 때)
     */
    fun markAllAsRead() {
        sharedPrefs?.edit()?.putInt(KEY_UNREAD_COUNT, 0)?.apply()
        _unreadCountFlow.tryEmit(0)
        Log.d(TAG, "모든 알림을 읽음으로 표시")
    }
    
    /**
     * 특정 알림을 읽음으로 표시
     */
    fun markAsRead(count: Int = 1) {
        val currentCount = getUnreadCount()
        val newCount = maxOf(0, currentCount - count)
        sharedPrefs?.edit()?.putInt(KEY_UNREAD_COUNT, newCount)?.apply()
        _unreadCountFlow.tryEmit(newCount)
        Log.d(TAG, "알림 $count 개를 읽음으로 표시, 남은 개수: $newCount")
    }
}

/**
 * 알림 데이터 클래스
 */
data class NotificationData(
    val title: String,
    val body: String,
    val animalId: Long? = null,
    val type: String = "general",
    val timestamp: Long = System.currentTimeMillis()
) 