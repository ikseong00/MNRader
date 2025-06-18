package com.example.mnrader.ui.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun UserInfo(
    modifier: Modifier = Modifier,
    imgUrl: String,
    email: String,
    address: String,
    onSettingsClick: () -> Unit = {},
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imgUrl,
                contentDescription = "User Profile Image",
                modifier = Modifier.size(40.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = email,
                    style = MNRaderTheme.typography.medium.copy(
                        fontSize = 24.sp
                    )
                )
                Text(
                    text = address,
                    style = MNRaderTheme.typography.regular.copy(
                        fontSize = 16.sp,
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                )
            }
        }
        Icon(
            imageVector = Icons.Filled.Settings,
            contentDescription = "Settings",
            tint = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UserInfoPreview() {
    UserInfo(
        imgUrl = "https://example.com/profile.jpg",
        email = "ikseong12@sdfsdf.com",
        address = "Seoul, South Korea",
    )
}