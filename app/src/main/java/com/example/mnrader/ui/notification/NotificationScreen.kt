package com.example.mnrader.ui.notification

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mnrader.ui.common.CommonTopBar
import com.example.mnrader.ui.notification.component.NotificationAnimalCard
import com.example.mnrader.ui.notification.viewmodel.NotificationViewModel
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun NotificationScreen(
    padding: PaddingValues = PaddingValues(0.dp),
    onBackClick: () -> Unit = { },
    onItemClick: (Long) -> Unit = { },
    viewModel: NotificationViewModel = viewModel()
) {
    val lazyState = rememberLazyListState()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CommonTopBar(
            hasBackButton = true,
            title = "과거 알림",
            onBack = onBackClick,
        )
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .height(36.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                width = 1.dp,
                color = Color(0xFFCAC4D0),
            ),
            onClick = { /* TODO: Handle button click */ }
        ) {
            Text(
                text = "정렬",
                style = MNRaderTheme.typography.medium.copy(
                    fontSize = 14.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            state = lazyState,
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(uiState.notificationList.size) { index ->
                NotificationAnimalCard(
                    notificationModel = uiState.notificationList[index],
                    onClick = { id -> onItemClick(id) }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun NotificationScreenPreview() {
    NotificationScreen()
}