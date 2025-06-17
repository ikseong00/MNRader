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
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mnrader.ui.mypage.dataclass.Pet
import com.example.mnrader.ui.mypage.dataclass.Scrap
import com.example.mnrader.ui.theme.Green2
import com.example.mnrader.ui.theme.Red
import com.example.mnrader.ui.theme.SkyBlue

@Composable
fun MyScrapSection(
    scraps: List<Scrap>,
    scrapClick: (Int) -> Unit,
    allScrapClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("스크랩", fontSize = 18.sp)
            TextButton(onClick = allScrapClick) {
                Text("전체보기", color = Color(0xFF8E8E93), fontSize = 14.sp)
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = Color(0xFF8E8E93)
                )
            }
        }

    }
}

// todo onClick: AnimalPage 컴포저블 구현 이후 navigate
@Composable
fun ScrapItem(scrap: Scrap, onClick: () -> Unit) {
    val color = when (scrap.pet.status) {
        "실종" -> Red
        "보호중" -> SkyBlue
        "목격중" -> Green2
        else -> Color.LightGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (scrap.pet.imageUrl != null) {
            AsyncImage(
                model = scrap.pet.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(2.dp, color, CircleShape)
            )
        }


        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text("${scrap.pet.name} / ${scrap.region}")
            Text(scrap.date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }

        Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
    }

    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 0.5.dp,
        color = Color(0xFF8E8E93)
    )
}

@Preview
@Composable
private fun MyScrapSectionPreview() {
    val pet = Pet(
        id = 1,
        name = "나비",
        imageUrl = "https://example.com/cat.png",
        species = "고양이",
        breed = "러시안블루",
        gender = "수컷",
        age = "2",
        description = "조용함",
        status = "보호중"
    )

    val scrap = Scrap(
        id = 1,
        pet = pet,
        region = "서울",
        date = "2025.04.01"
    )

    MyScrapSection(
        scraps = listOf(scrap),
        scrapClick = {},
        allScrapClick = {}
    )
}
