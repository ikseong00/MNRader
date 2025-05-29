package com.example.mnrader.ui.mypage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

data class Scrap(
    val id: String,
    val title: String,
    val region: String,
    val date: String,
    val imageUrl: String
)

val dummyScraps = listOf(
    Scrap("scrap1", "치와와", "서울 광진구", "2024.06.07", "https://example.com/dog.png"),
    Scrap("scrap2", "고양이", "서울 동작구", "2025.04.01", "https://example.com/cat.png")
)

@Composable
fun MyScrapSection(
    scraps: List<Scrap> = dummyScraps,
    scrapClick: (String) -> Unit,
    allScrapClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Text("스크랩", style = MaterialTheme.typography.titleMedium)
            // '전체보기 >' 버튼
            Row(
                modifier = Modifier
                    .clickable(onClick = allScrapClick)
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

        scraps.forEach {
            ScrapItem(it, onClick = { scrapClick(it.id) })
        }
    }
}

@Composable
fun ScrapItem(scrap: Scrap, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = scrap.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text("${scrap.title} / ${scrap.region}")
            Text(scrap.date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
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
