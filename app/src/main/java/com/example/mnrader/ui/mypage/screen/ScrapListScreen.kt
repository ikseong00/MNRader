package com.example.mnrader.ui.mypage.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.mnrader.ui.mypage.component.CommonTopBar
import com.example.mnrader.ui.mypage.component.ScrapItem
import com.example.mnrader.ui.mypage.dataclass.Pet
import com.example.mnrader.ui.mypage.dataclass.Scrap
import com.example.mnrader.ui.mypage.viewmodel.MyPageViewModel
import com.example.mnrader.ui.theme.Green2
import com.example.mnrader.ui.theme.Red
import com.example.mnrader.ui.theme.SkyBlue

@Composable
fun ScrapListScreen(
    viewModel: MyPageViewModel,
    onBackClick: () -> Unit,
    onScrapClick: (postId: Int) -> Unit
) {
    val scraps by viewModel.scraps.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(12.dp))

        CommonTopBar(
            title = "스크랩",
            onBack = { onBackClick() }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 스크롤 가능 리스트
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(scraps) { scrap ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onScrapClick(scrap.id)
                        }
                        .padding(vertical = 12.dp)
                ) {
                    if (scrap.pet.imageUrl != null) {
                        Image(
                            painter = rememberAsyncImagePainter(scrap.pet.imageUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 3.dp,
                                    color = when (scrap.pet.status) {
                                        "실종" -> Red
                                        "보호중" -> SkyBlue
                                        "목격중" -> Green2
                                        else -> Color.LightGray
                                    },
                                    shape = CircleShape
                                )
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                    Column {
                        Text("${scrap.pet.name} / ${scrap.region}", fontSize = 16.sp)
                        Text(scrap.date, fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScrapListScreenPreview() {
    val pet1 = Pet(
        id = 1,
        name = "푸들",
        imageUrl = "https://example.com/poodle.png",
        species = "개",
        breed = "푸들",
        gender = "암컷",
        age = "3",
        description = "활발함",
        status = "목격중"
    )

    val pet2 = Pet(
        id = 2,
        name = "페르시안",
        imageUrl = "https://example.com/persian.png",
        species = "고양이",
        breed = "페르시안",
        gender = "수컷",
        age = "2",
        description = "느긋함",
        status = "보호중"
    )

    val dummyScraps = listOf(
        Scrap(id = 1, pet = pet1, region = "서울", date = "2025.06.01"),
        Scrap(id = 2, pet = pet2, region = "서울", date = "2025.05.30")
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(12.dp))
        CommonTopBar(title = "스크랩", onBack = {})
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(dummyScraps) { scrap ->
                ScrapItem(scrap = scrap, onClick = {})
            }
        }
    }
}