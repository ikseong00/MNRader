package com.example.mnrader.ui.mypage

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class MyPost(
    val id: String,
    val name: String,
    val gender: String,
    val region: String,
    val date: String,
    val imageUrl: String
)

@Composable
fun MyPostSection(
    posts: List<MyPost>,
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("내가 올린 게시물", style = MaterialTheme.typography.titleMedium)
            TextButton (onClick = allPostsClick) {
                Text("전체보기 >")
            }
        }

        posts.forEach {
            MyPostItem(it, onClick = { postClick(it.id) })
        }

    }
}

// 게시물 정보
@Composable
fun MyPostItem(post: MyPost, onClick: () -> Unit) {
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
                .border(3.dp, Color(0xFFFFC0CB), CircleShape) // 분홍색
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
}


