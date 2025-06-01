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
fun ScrapListScreen(
    navController: NavHostController,
    viewModel: MyPageViewModel
) {
    val scraps by viewModel.scraps.collectAsState()

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
            Text("스크랩", fontSize = 20.sp)
        }

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(scraps) { scrap ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(vertical = 12.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(scrap.imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .padding(end = 12.dp)
                    )
                    Column {
                        Text(text = "${scrap.name} / ${scrap.region}", fontSize = 16.sp)
                        Text(text = scrap.date, fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScrapListScreenPreview() {
    ScrapListScreenPreviewContent()
}

@Composable
private fun ScrapListScreenPreviewContent() {
    val dummyScraps = listOf(
        Scrap("1", "푸들", "서울 강남구", "2025.06.01", "https://example.com/poodle.png"),
        Scrap("2", "페르시안", "서울 종로구", "2025.05.30", "https://example.com/persian.png")
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
