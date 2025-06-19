package com.example.mnrader.ui.notification

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mnrader.data.repository.UserRepository
import com.example.mnrader.ui.common.CommonTopBar
import com.example.mnrader.ui.common.MNRaderButton
import com.example.mnrader.ui.home.model.AnimalDataType
import com.example.mnrader.ui.notification.component.NotificationAnimalCard
import com.example.mnrader.ui.notification.viewmodel.NotificationViewModel
import com.example.mnrader.ui.notification.viewmodel.NotificationViewModelFactory
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun NotificationScreen(
    padding: PaddingValues = PaddingValues(0.dp),
    onBackClick: () -> Unit = { },
    onMNAnimalClick: (Long) -> Unit = { },
    onPortalLostClick: (Long) -> Unit = { },
    onPortalProtectClick: (Long) -> Unit = { },
    viewModel: NotificationViewModel = viewModel(
        factory = NotificationViewModelFactory(
            userRepository = UserRepository(LocalContext.current)
        )
    )
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
            onClick = { viewModel.loadMoreNotifications() }
        ) {
            Text(
                text = "더 불러오기",
                style = MNRaderTheme.typography.medium.copy(
                    fontSize = 14.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        when {
            uiState.isLoading && uiState.notificationList.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.errorMessage != null && uiState.notificationList.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = uiState.errorMessage!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        MNRaderButton(
                            text = "다시 시도",
                            onClick = { viewModel.retry() }
                        )
                    }
                }
            }
            else -> {
                LazyColumn(
                    state = lazyState,
                    contentPadding = PaddingValues(horizontal = 20.dp)
                ) {
                    items(uiState.notificationList.size) { index ->
                        val notification = uiState.notificationList[index]
                        NotificationAnimalCard(
                            notificationModel = notification,
                            onClick = { id ->
                                when (notification.type) {
                                    AnimalDataType.PORTAL_LOST -> onPortalLostClick(id)
                                    AnimalDataType.PORTAL_PROTECT -> onPortalProtectClick(id)
                                    AnimalDataType.MN_LOST,
                                    AnimalDataType.MY_WITNESS -> onMNAnimalClick(id)
                                }
                            }
                        )
                    }
                    
                    if (uiState.isLoading && uiState.notificationList.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun NotificationScreenPreview() {
    NotificationScreen()
}