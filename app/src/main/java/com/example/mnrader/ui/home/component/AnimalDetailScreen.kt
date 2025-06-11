package com.example.mnrader.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.R
import com.example.mnrader.ui.home.model.HomeAnimalData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimalDetailScreen(
    animalId: Long,
    navController: NavController,
    onBack: () -> Unit
) {

    val animal = remember {
        HomeAnimalData.dummyHomeAnimalData.find { it.id == animalId }
    }

    if (animal == null) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("상세페이지")},
                    navigationIcon = {
                        IconButton(onClick = { onBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("동물 정보를 찾을 수 없습니다.")
            }
        }
        return
    }

    var isBookmarked by remember { mutableStateOf(animal.isBookmarked) }


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("상세페이지")},
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { isBookmarked = !isBookmarked }) {
                    Icon(
                        painterResource(id = R.drawable.ic_animal_stars),
                        contentDescription = "Bookmark",
                        tint = if (isBookmarked) Color(0xFF90c5aa) else Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Button(
                    onClick = {
                        navController.navigate("add")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF90c5aa)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("신고하러 가기")
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .size(360.dp)
                ) {
                    // 동물 이미지
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground), // 실제 동물 이미지를 볼러오기
                        contentDescription = "Animal Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(360.dp)
                            .clip(CircleShape)
                            .border(
                                width = 10.dp,
                                shape = CircleShape,
                                color = animal.type.color
                            )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                // 동물 정보
                AnimalInfoSection(animal)
            }
        }
    }
}


@Composable
fun AnimalInfoSection(animal: HomeAnimalData) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(animal.name,
            fontSize = 24.sp,
            color = Color.Black)
        Text("성별 "+animal.gender.value,
            fontSize = 16.sp,
            color = Color.Gray)
        Text("품종 breed",
            fontSize = 16.sp,
            color = Color.Gray)
        InfoRow(label = "장소", value = animal.location)
        InfoRow(label = "등록 날짜", value = animal.date)
        InfoRow(label = "연락처", value = "")//contact
        InfoRow(label = "상세 내용", value = "")//description
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange ={},
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AnimalDetailScreenPreview() {
    val dummyAnimal = HomeAnimalData.dummyHomeAnimalData[0]  // id = 1L

    AnimalDetailScreen(
        animalId = dummyAnimal.id,
        navController = rememberNavController(),
        onBack = {}
    )
}
