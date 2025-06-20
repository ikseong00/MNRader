package com.example.mnrader.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun NotificationIconWithBadge(
    painter: Painter,
    unreadCount: Int,
    modifier: Modifier = Modifier,
    iconSize: androidx.compose.ui.unit.Dp = 24.dp,
    contentDescription: String? = null
) {
    Box(modifier = modifier) {
        Icon(
            modifier = Modifier.size(iconSize),
            painter = painter,
            contentDescription = contentDescription,
            tint = Color.Unspecified
        )
        
        // 읽지 않은 알림이 있을 때만 배지 표시
        if (unreadCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (unreadCount > 99) "99+" else unreadCount.toString(),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }
        }
    }
} 