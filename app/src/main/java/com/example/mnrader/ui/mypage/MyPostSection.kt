package com.example.mnrader.ui.mypage

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun MyPostSection(
    posts: List<Post>,
    postClick: (postId: String) -> Unit,
    allPostsClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("내가 올린 게시물", style = MaterialTheme.typography.titleMedium)

            // '전체보기 >' 버튼
            Row(
                modifier = Modifier
                    .clickable(onClick = allPostsClick)
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("전체보기", color = Color(0xFF8E8E93))
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "전체보기",
                    tint = Color(0xFF8E8E93)
                )
            }
        }

        posts.forEach {
            PostItem(it, onClick = { postClick(it.id) })
        }

    }
}

// 게시물 정보
@Composable
fun PostItem(post: Post, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = post.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .border(3.dp, Color(0xFFFFC0CB), CircleShape) // 분홍색 -> todo 동물의 상태마다 달라져야 함
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(post.name, style = MaterialTheme.typography.bodyLarge)
            Text("성별 ${post.gender}", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            Surface(
                color = Color(0xFFF0F0F0),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.padding(vertical = 2.dp)
            ) {
                Text("지역: ${post.region}", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(4.dp))
            }
            Text(post.date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }

        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
    }

    // 밑줄 구분자
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 0.5.dp,
        color = Color(0xFF8E8E93)
    )
}

val dummyPosts = listOf(
    Post(
        id = "1",
        name = "인절미",
        gender = "암컷",
        region = "서울 광진구",
        date = "2025-03-24",
        imageUrl = "https://example.com/dog.png"
    )
)

@Preview
@Composable
private fun MyPostSectionPreview() {
    MyPostSection(
        posts = dummyPosts,
        postClick = { postId -> println("Clicked post: $postId") },
        allPostsClick = { println("Clicked 전체보기 for posts") }
    )
}