package com.example.mnrader.ui.setting.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.mnrader.data.repository.UserAnimalRepository
import com.example.mnrader.ui.common.CommonTopBar
import com.example.mnrader.ui.common.MNRaderButton
import com.example.mnrader.ui.setting.component.FormSection
import com.example.mnrader.ui.setting.viewmodel.MyPetDetailViewModel
import com.example.mnrader.ui.setting.viewmodel.MyPetDetailViewModelFactory

@Composable
fun MyPetDetailScreen(
    animalId: Int,
    padding: PaddingValues,
    onBack: () -> Unit = {},
    onEdit: (Int) -> Unit = {},
    viewModel: MyPetDetailViewModel = viewModel(
        factory = MyPetDetailViewModelFactory(
            userAnimalRepository = UserAnimalRepository(),
            animalId = animalId
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CommonTopBar(
            hasBackButton = true,
            title = "동물 상세",
            onBack = onBack,
        )
        Spacer(modifier = Modifier.height(24.dp))

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.errorMessage != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = uiState.errorMessage!!,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    MNRaderButton(
                        text = "다시 시도",
                        onClick = { viewModel.retry() }
                    )
                }
            }
            uiState.animalDetail != null -> {
                val animalDetail = uiState.animalDetail!!
                
                // 동물 종류
                FormSection(label = "동물 종류") {
                    Text(
                        text = animalDetail.animalType.displayName,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 품종
                FormSection(label = "품종") {
                    Text(
                        text = animalDetail.breed,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 성별
                FormSection(label = "성별") {
                    Text(
                        text = animalDetail.gender.value,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 나이
                FormSection(label = "나이") {
                    Text(
                        text = "${animalDetail.age}세",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 특징
                FormSection(label = "특징") {
                    Text(
                        text = animalDetail.detail,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 사진
                FormSection(label = "사진") {
                    AsyncImage(
                        model = animalDetail.imageUrl,
                        contentDescription = "동물 사진",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                MNRaderButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                        .height(43.dp),
                    text = "수정",
                    cornerShape = 8.dp,
                    onClick = {
                        onEdit(animalId)
                    },
                )
            }
        }
    }
} 