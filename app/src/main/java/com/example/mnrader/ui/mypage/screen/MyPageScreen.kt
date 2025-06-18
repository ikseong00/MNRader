package com.example.mnrader.ui.mypage.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mnrader.ui.common.CommonTopBar
import com.example.mnrader.ui.mypage.component.MyAnimalCardList
import com.example.mnrader.ui.mypage.component.MyPageColumn
import com.example.mnrader.ui.mypage.component.UserInfo
import com.example.mnrader.ui.mypage.viewmodel.MyViewModel
import com.example.mnrader.ui.mypage.viewmodel.MyViewModelFactory
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun MyPageScreen(
    padding: PaddingValues,
    navigateToSettings: () -> Unit = {},
    navigateToMyArticles: () -> Unit = {},
    navigateToScrap: () -> Unit = {},
    viewModel: MyViewModel = viewModel(
        factory = MyViewModelFactory(
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CommonTopBar(
                hasBackButton = false,
                title = "마이페이지",
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            UserInfo(
                modifier = Modifier.padding(horizontal = 16.dp),
                email = uiState.email,
                address = uiState.address,
                onSettingsClick = navigateToSettings
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "보유 중인 동물",
                style = MNRaderTheme.typography.medium.copy(
                    fontSize = 24.sp
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            MyAnimalCardList(
                animalList = uiState.myAnimalList
            )
            Spacer(modifier = Modifier.height(20.dp))

            MyPageColumn(
                text = "내가 올린 게시물",
                onClick = navigateToMyArticles
            )
            HorizontalDivider(
                thickness = 1.dp
            )
            MyPageColumn(
                text = "스크랩",
                onClick = navigateToScrap
            )
        }
    }
}
