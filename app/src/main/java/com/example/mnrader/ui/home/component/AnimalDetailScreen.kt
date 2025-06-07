package com.example.mnrader.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.example.mnrader.navigation.Routes
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
        Text("동물 정보를 찾을 수 없습니다.")
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("상세페이지") },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(
                isBookmarked = animal.isBookmarked,
                onBookmarkClick = { /* 북마크 로직 추가 */ },
                onReportClick = {
                    navController.navigate(Routes.ADD)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(text = "Animal ID: $animalId", style = MaterialTheme.typography.headlineMedium)
            // 여기에 동물 정보 표시 로직 추가
        }
    }
}


@Composable
fun BottomBar(
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    onReportClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onBookmarkClick,
            colors = if (isBookmarked) {
                ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            } else {
                ButtonDefaults.buttonColors()
            }
        ) {
            Text(if (isBookmarked) "스크랩됨" else "스크랩")
        }

        Button(
            onClick = onReportClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("동물 신고하기")
        }
    }
}