package com.example.mnrader.ui.mypost

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mnrader.ui.common.CommonTopBar
import com.example.mnrader.ui.mypost.component.MyPostAnimalCard
import com.example.mnrader.ui.mypost.viewmodel.MyPostViewModel
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun MyPostScreen(
    padding: PaddingValues = PaddingValues(0.dp),
    onBackClick: () -> Unit = { },
    onItemClick: (Long) -> Unit = { },
    onAddPostClick: () -> Unit = { },
    viewModel: MyPostViewModel = viewModel()
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
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = onAddPostClick,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("게시글 작성")
                    }
                }
            }
        } else {
            LazyColumn(
                state = lazyState,
                contentPadding = PaddingValues(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.myPostList.size) { index ->
                    MyPostAnimalCard(
                        myPostModel = uiState.myPostList[index],
                        onClick = { id -> onItemClick(id) }
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