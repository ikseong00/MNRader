package com.example.mnrader.ui.scrap

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.mnrader.ui.home.model.AnimalDataType
import com.example.mnrader.ui.scrap.component.ScrapAnimalCard
import com.example.mnrader.ui.scrap.viewmodel.ScrapViewModel
import com.example.mnrader.ui.scrap.viewmodel.ScrapViewModelFactory
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun ScrapScreen(
    onBackClick: () -> Unit = { },
    onMNAnimalClick: (Long) -> Unit = { },
    onPortalLostClick: (Long) -> Unit = { },
    onPortalProtectClick: (Long) -> Unit = { },
    viewModel: ScrapViewModel = viewModel(
        factory = ScrapViewModelFactory(
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
            title = "스크랩",
            onBack = onBackClick,
        )
        
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier
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
                onClick = { /* TODO: Handle sort button click */ }
            ) {
                Text(
                    text = "정렬",
                    style = MNRaderTheme.typography.medium.copy(
                        fontSize = 14.sp
                    )
                )
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.scrapList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "스크랩한 동물이 없습니다",
                        style = MNRaderTheme.typography.medium.copy(
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "관심있는 동물을 스크랩해보세요",
                        style = MNRaderTheme.typography.regular.copy(
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    )
                }
            }
        } else {
            LazyColumn(
                state = lazyState,
                contentPadding = PaddingValues(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.scrapList.size) { index ->
                    val scrap = uiState.scrapList[index]
                    ScrapAnimalCard(
                        scrapModel = scrap,
                        onClick = { id -> 
                            when (scrap.type) {
                                AnimalDataType.PORTAL_LOST -> onPortalLostClick(id)
                                AnimalDataType.PORTAL_PROTECT -> onPortalProtectClick(id)
                                AnimalDataType.MN_LOST,
                                AnimalDataType.MY_WITNESS -> onMNAnimalClick(id)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScrapScreenPreview() {
    MNRaderTheme {
        ScrapScreen()
    }
} 