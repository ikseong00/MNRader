package com.example.mnrader.ui.mypost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.example.mnrader.ui.mypost.component.MyPostAnimalCard
import com.example.mnrader.ui.mypost.viewmodel.MyPostViewModel
import com.example.mnrader.ui.mypost.viewmodel.MyPostViewModelFactory
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun MyPostScreen(
    onBackClick: () -> Unit = { },
    onMNAnimalClick: (Long) -> Unit = { },
    onPortalLostClick: (Long) -> Unit = { },
    onPortalProtectClick: (Long) -> Unit = { },
    viewModel: MyPostViewModel = viewModel(
        factory = MyPostViewModelFactory(
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
            title = "내 게시글",
            onBack = onBackClick,
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.myPostList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "작성한 게시글이 없습니다",
                        style = MNRaderTheme.typography.medium.copy(
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "첫 번째 게시글을 작성해보세요",
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
                items(uiState.myPostList.size) { index ->
                    val myPost = uiState.myPostList[index]
                    MyPostAnimalCard(
                        myPostModel = myPost,
                        onClick = { id -> 
                            when (myPost.type) {
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
private fun MyPostScreenPreview() {
    MNRaderTheme {
        MyPostScreen()
    }
} 