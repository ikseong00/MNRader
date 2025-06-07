package com.example.mnrader.ui.mypage.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mnrader.ui.mypage.dataclass.Pet
import com.example.mnrader.ui.mypage.dataclass.Post
import com.example.mnrader.ui.theme.Green2
import com.example.mnrader.ui.theme.Red
import com.example.mnrader.ui.theme.SkyBlue

@Composable
fun MyPostSection(
    posts: List<Post>,
    postClick: (postId: Int) -> Unit,
    allPostsClick: () -> Unit
) {

    Column(modifier = Modifier.padding(top = 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("내가 올린 게시물", style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = allPostsClick) {
                Text("전체보기", color = Color(0xFF8E8E93))
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color(0xFF8E8E93)
                )
            }

        }

    }
}

@Composable
fun PostItem(post: Post, onClick: () -> Unit) {
    val color = when (post.pet.status) {
        "실종" -> Red
        "보호중" -> SkyBlue
        "목격중" -> Green2
        else -> Color.LightGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (post.pet.imageUrl != null) {
            AsyncImage(
                model = post.pet.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .border(3.dp, color, CircleShape)
            )
            Spacer(Modifier.width(16.dp))
        }

        Column(Modifier.weight(1f)) {
            Text(post.pet.name, style = MaterialTheme.typography.bodyLarge)
            Text("성별 ${post.pet.gender}", color = Color.Gray)
            Text("지역: ${post.region}", color = Color.Gray)
            Text(post.date, color = Color.Gray)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null)
    }
}

@Preview
@Composable
private fun MyPostSectionPreview() {
    val pet = Pet(
        id = 1,
        name = "인절미",
        imageUrl = "https://example.com/dog.png",
        species = "개",
        breed = "진돗개",
        gender = "암컷",
        age = "3",
        description = "털이 복슬복슬",
        status = "실종"
    )

    val post = Post(
        id = 1,
        pet = pet,
        region = "서울",
        date = "2025-03-24"
    )

    MyPostSection(
        posts = listOf(post),
        postClick = {},
        allPostsClick = {}
    )
}
