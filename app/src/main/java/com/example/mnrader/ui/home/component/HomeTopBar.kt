package com.example.mnrader.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mnrader.R
import com.example.mnrader.data.manager.NotificationManager
import com.example.mnrader.ui.common.NotificationIconWithBadge
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    hasNewNotification: Boolean = false,
    onNotificationClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(51.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(start = 6.dp)
                .size(50.dp),
            painter = painterResource(R.drawable.img_logo_onboarding),
            contentDescription = null,
        )
        Row {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(R.drawable.ic_home_pin),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Text(
                text = "서울 광진구",
                style = MNRaderTheme.typography.regular.copy(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                )
            )
        }

        NotificationIconWithBadge(
            modifier = Modifier
                .padding(end = 18.dp)
                .clickable { onNotificationClick() },
            painter = painterResource(R.drawable.ic_home_notification),
            unreadCount = if (hasNewNotification) 1 else 0,
            contentDescription = "알림",
            iconSize = 24.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTopBarPreview() {
    HomeTopBar()
}