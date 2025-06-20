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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import coil3.compose.AsyncImage
import com.example.mnrader.R
import com.example.mnrader.data.repository.DataPortalRepository
import com.example.mnrader.ui.animaldetail.model.PortalAnimalDetailData
import com.example.mnrader.ui.animaldetail.viewmodel.PortalProtectDetailViewModel
import com.example.mnrader.ui.animaldetail.viewmodel.PortalProtectDetailViewModelFactory
import com.example.mnrader.ui.home.model.AnimalDataType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortalProtectDetailScreen(
    animalId: Long,
    onBack: () -> Unit,
    viewModel: PortalProtectDetailViewModel = viewModel(
        factory = PortalProtectDetailViewModelFactory(
            dataPortalRepository = DataPortalRepository(LocalContext.current),
            animalId = animalId
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("보호동물 상세")
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
                IconButton(onClick = { viewModel.toggleBookmark() }) {
                    Icon(
                        painterResource(id = R.drawable.ic_animal_stars),
                        contentDescription = "Bookmark",
                        tint = if (uiState.isBookmarked) Color(0xFF90c5aa) else Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        val animal = uiState.animalDetail
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    // 동물 이미지
                    AsyncImage(
                        model = animal.imageUrl,
                        contentDescription = "Animal Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(360.dp)
                            .clip(CircleShape)
                            .border(
                                width = 10.dp,
                                shape = CircleShape,
                                color = AnimalDataType.PORTAL_PROTECT.color
                            ),
                        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                        error = painterResource(id = R.drawable.ic_launcher_foreground)
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
private fun AnimalInfoSection(animal: PortalAnimalDetailData) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            animal.breed,
            fontSize = 24.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        InfoRow(label = "성별", value = animal.gender)
        InfoRow(label = "장소", value = animal.location)
        InfoRow(label = "등록 날짜", value = animal.date)
        InfoRow(label = "연락처", value = animal.contact)
        InfoRow(label = "특이사항", value = animal.detail)
    }
}

@Composable
@Preview(showBackground = true)
fun PortalProtectDetailScreenPreview() {
    PortalProtectDetailScreen(
        animalId = 1L,
        onBack = {}
    )
} 