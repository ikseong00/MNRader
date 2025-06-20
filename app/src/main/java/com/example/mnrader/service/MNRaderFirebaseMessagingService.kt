package com.example.mnrader.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.mnrader.MainActivity
import com.example.mnrader.R
import com.example.mnrader.data.manager.NotificationManager as AppNotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MNRaderFirebaseMessagingService : FirebaseMessagingService() {
    
    companion object {
        private const val TAG = "FCMService"
        private const val CHANNEL_ID = "mnrader_notifications"
        private const val NOTIFICATION_ID = 1001
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "새로운 FCM 토큰: $token")
        
        // 새로운 토큰을 서버에 전송하는 로직을 여기에 추가할 수 있습니다
        // 현재는 로그인 시에만 토큰을 전송하므로 별도 구현하지 않습니다
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        
        Log.d(TAG, "FCM 메시지 수신: ${remoteMessage.from}")
        
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
        val data = remoteMessage.data
        
        // 메시지 데이터가 있는 경우
        if (data.isNotEmpty()) {
            Log.d(TAG, "메시지 데이터: $data")
        }
        
        // 알림이 있는 경우
        if (title != null || body != null || data.isNotEmpty()) {
            Log.d(TAG, "메시지 알림 제목: $title")
            Log.d(TAG, "메시지 알림 내용: $body")
            
            // 앱 내 알림 매니저에 알림 전달
            CoroutineScope(Dispatchers.IO).launch {
                AppNotificationManager.handleFCMMessage(title, body, data)
            }
            
            // 시스템 알림 표시
            showNotification(
                title = title ?: "멍냥레이더",
                body = body ?: "새로운 알림이 있습니다",
                data = data
            )
        }
    }

    private fun showNotification(title: String, body: String, data: Map<String, String>) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        // Android O 이상에서는 알림 채널 생성 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "멍냥레이더 알림",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "멍냥레이더 앱의 알림 채널입니다"
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }
        
        // 메인 액티비티로 이동하는 인텐트
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            
            // 알림 데이터에 따라 특정 화면으로 이동
            data["animalId"]?.let { animalId ->
                putExtra("animalId", animalId)
                putExtra("navigateTo", "animalDetail")
            }
            data["type"]?.let { type ->
                putExtra("notificationType", type)
            }
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this, 
            System.currentTimeMillis().toInt(), // 고유한 request code
            intent, 
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // 알림 생성
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_home_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setShowWhen(true)
            .build()
        
        notificationManager.notify(
            System.currentTimeMillis().toInt(), // 고유한 알림 ID
            notification
        )
    }
} 