package com.example.mnrader.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mnrader.ui.mypage.viewmodel.MyPageViewModel

@Composable
fun PostListScreen(
    navController: NavHostController,
    viewModel: MyPageViewModel
) {
    val posts by viewModel.posts.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("내가 올린 게시물", fontSize = 20.sp)
        }

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(posts) { post ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(vertical = 12.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(post.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .padding(end = 12.dp)
                    )
                    Column {
                        Text(text = post.name, fontSize = 16.sp)
                        Text(text = "성별 ${post.gender}", fontSize = 14.sp, color = Color.Gray)
                        Text(text = "지역: ${post.region}", fontSize = 14.sp, color = Color.Gray)
                        Text(text = post.date, fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostListScreenPreview() {
    PostListScreenPreviewContent()
}

@Composable
private fun PostListScreenPreviewContent() {
    val dummyPosts = listOf(
        Post(
            id = "1",
            name = "진돗개",
            gender = "수컷",
            region = "부산 해운대구",
            date = "2025.06.01",
            imageUrl = "https://example.com/jindo.png"
        ),
        Post(
            id = "2",
            name = "러시안블루",
            gender = "암컷",
            region = "서울 서초구",
            date = "2025.05.29",
            imageUrl = "https://example.com/russianblue.png"
        )
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text("내 게시물", modifier = Modifier.padding(16.dp), fontSize = 20.sp)
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(dummyPosts) { post ->
                PostItem(post = post, onClick = {})
            }
        }
    }
}
