package com.example.mnrader.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mnrader.ui.mypage.viewmodel.MyPageViewModel

@Composable
fun ScrapListScreen(navController: NavHostController, viewModel: MyPageViewModel) {
    val scraps by viewModel.scraps.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("스크랩")
        }

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(scraps) { scrap ->
                ScrapItem(scrap = scrap, onClick = { /* TODO */ })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScrapListScreenPreview() {
    val pet1 = Pet(
        id = "p1",
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
        id = "p2",
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
        Scrap(id = "1", pet = pet1, region = "서울 강남구", date = "2025.06.01"),
        Scrap(id = "2", pet = pet2, region = "서울 종로구", date = "2025.05.30")
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text("스크랩", modifier = Modifier.padding(16.dp), fontSize = 20.sp)
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(dummyScraps) { scrap ->
                ScrapItem(scrap = scrap, onClick = {})
            }
        }
    }
}
