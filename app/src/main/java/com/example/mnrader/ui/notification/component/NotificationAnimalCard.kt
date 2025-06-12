package com.example.mnrader.ui.notification.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mnrader.ui.notification.viewmodel.NotificationModel
import com.example.mnrader.ui.notification.viewmodel.NotificationUiState
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun NotificationAnimalCard(
    modifier: Modifier = Modifier,
    notificationModel: NotificationModel,
    onClick: (Long) -> Unit = { }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(notificationModel.id) }
            .padding(vertical = 12.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .border(
                    width = 1.dp,
                    color = notificationModel.type.color,
                    shape = CircleShape
                ),
            model = notificationModel.imageUrl,
            contentDescription = "Notification Animal Image",
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(
                text = "${notificationModel.name} / ${notificationModel.address}",
                style = MNRaderTheme.typography.regular.copy(
                    fontSize = 14.sp
                )
            )
            Text(
                text = notificationModel.date,
                style = MNRaderTheme.typography.regular.copy(
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.5f)
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotificationAnimalCardPreview() {
    NotificationAnimalCard(
        notificationModel = NotificationUiState.dummyList.first()
    )
}