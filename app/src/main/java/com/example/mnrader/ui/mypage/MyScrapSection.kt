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

@Composable
fun MyScrapSection(
    scraps: List<Scrap>,
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("스크랩", style = MaterialTheme.typography.titleMedium)
            TextButton(onClick = allScrapClick) {
                Text("전체보기 >")
            }
        }

        val scraps = listOf(
            Triple("치와와", "서울 광진구", "scrap1"),
            Triple("고양이", "서울 동작구", "scrap2")
        )

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
}
