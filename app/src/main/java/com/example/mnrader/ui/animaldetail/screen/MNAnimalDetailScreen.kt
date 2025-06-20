package com.example.mnrader.ui.animaldetail.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.mnrader.R
import com.example.mnrader.data.repository.AnimalRepository
import com.example.mnrader.ui.animaldetail.viewmodel.MNAnimalDetail
import com.example.mnrader.ui.animaldetail.viewmodel.MNAnimalDetailViewModel
import com.example.mnrader.ui.animaldetail.viewmodel.MNAnimalDetailViewModelFactory
import com.example.mnrader.ui.home.model.HomeAnimalData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MNAnimalDetailScreen(
    animalId: Long,
    navController: NavController,
    onBack: () -> Unit,
    viewModel: MNAnimalDetailViewModel = viewModel(
        factory = MNAnimalDetailViewModelFactory(
            animalRepository = AnimalRepository(),
            animalId = animalId
        )
    )
) {

    LaunchedEffect(animalId) {
        viewModel.loadAnimalDetail(animalId)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val animal = uiState.animalDetail

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("상세페이지")
                },
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
                IconButton(onClick = { viewModel.toggleScrap() }) {
                    Icon(
                        painterResource(id = R.drawable.ic_animal_stars),
                        contentDescription = "Bookmark",
                        tint = if (uiState.animalDetail.isScrapped) Color(0xFF90c5aa) else Color.Gray,
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
                    AsyncImage(
                        model = animal.img,
                        contentDescription = "Animal Image",
                        contentScale = ContentScale.Crop,
                        error = painterResource(R.drawable.img_logo_onboarding),
                        modifier = Modifier
                            .size(360.dp)
                            .clip(CircleShape)
                            .border(
                                width = 10.dp,
                                shape = CircleShape,
                                color = uiState.animalDetail.status.color
                            ),
                        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
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
private fun AnimalInfoSection(animal: MNAnimalDetail) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            animal.breed,
            fontSize = 24.sp,
            color = Color.Black
        )
        Text(
            "성별 " + animal.gender,
            fontSize = 16.sp,
            color = Color.Gray
        )
        InfoRow(label = "장소", value = animal.address)
        InfoRow(label = "등록 날짜", value = animal.date)
        InfoRow(label = "연락처", value = animal.contact)
        InfoRow(label = "상세 내용", value = animal.detail)
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
            onValueChange = {},
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AnimalDetailScreenPreview() {
    val dummyAnimal = HomeAnimalData.dummyHomeAnimalData[0]  // id = 1L

    MNAnimalDetailScreen(
        animalId = dummyAnimal.id,
        navController = rememberNavController(),
        onBack = {}
    )
}
