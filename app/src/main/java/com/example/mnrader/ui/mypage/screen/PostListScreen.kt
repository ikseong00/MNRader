package com.example.mnrader.ui.mypage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mnrader.ui.mypage.component.CommonTopBar
import com.example.mnrader.ui.mypage.viewmodel.MyPageViewModel
import com.example.mnrader.ui.theme.Green2
import com.example.mnrader.ui.theme.Red
import com.example.mnrader.ui.theme.SkyBlue

@Composable
fun PostListScreen(
    viewModel: MyPageViewModel,
    onBackClick: () -> Unit,
    onPostClick: (postId: Int) -> Unit
) {
    val posts by viewModel.posts.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(12.dp)) // 상단 여백

        CommonTopBar(
            title = "내가 올린 게시물",
            onBack = { onBackClick() }
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(posts) { post ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onPostClick(post.id)
                        }
                        .padding(vertical = 12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .padding(end = 12.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(post.pet.imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .aspectRatio(1f)
                                .clip(CircleShape)
                                .border(
                                    width = 3.dp,
                                    color = when (post.pet.status) {
                                        "실종" -> Red
                                        "보호중" -> SkyBlue
                                        "목격" -> Green2
                                        else -> Color.LightGray
                                    },
                                    shape = CircleShape
                                )
                        )
                    }

                    Column {
                        Text(text = post.pet.name, fontSize = 16.sp)
                        Text(text = "성별 ${post.pet.gender}", color = Color.Gray, fontSize = 14.sp)
                        Text(text = "지역: ${post.region}", color = Color.Gray, fontSize = 14.sp)
                        Text(text = post.date, color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
