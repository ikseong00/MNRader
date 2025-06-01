package com.example.mnrader.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mnrader.ui.common.MNRaderButton
import com.example.mnrader.ui.home.component.HomeAnimalList
import com.example.mnrader.ui.home.component.HomeFilter
import com.example.mnrader.ui.home.component.HomeTopBar
import com.example.mnrader.ui.home.component.MapAnimalInfo
import com.example.mnrader.ui.home.component.MapComponent
import com.example.mnrader.ui.home.model.HomeAnimalData
import com.example.mnrader.ui.home.viewmodel.HomeViewModel
import com.example.mnrader.ui.theme.Green2
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.rememberCameraPositionState

@Composable
fun HomeScreen(
    padding: PaddingValues,
    navigateToAnimalDetail: (Int) -> Unit = {},
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()
    val cameraPositionState = rememberCameraPositionState()
    cameraPositionState.position = CameraPosition(
        LatLng(37.5407, 127.0791),
        15.0,
        0.0,
        0.0
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScroll(state = scrollState)
    ) {
        HomeTopBar(
            onNotificationClick = { /*TODO*/ },
        )
        Spacer(Modifier.height(5.dp))
        HomeFilter(
            onLocationClick = { /*TODO*/ },
            onBreedClick = { /*TODO*/ },
            onWitnessClick = { viewModel.setWitnessShown(it) },
            onLostClick = { viewModel.setLostShown(it) },
            onProtectClick = { viewModel.setProtectShown(it) },
            isWitnessShown = uiState.isWitnessShown,
            isLostShown = uiState.isLostShown,
            isProtectShown = uiState.isProtectShown,
        )
        Spacer(Modifier.height(15.dp))
        MapComponent(
            isExpanded = uiState.isExpanded,
            cameraPositionState = cameraPositionState,
            animalDataList = uiState.shownAnimalDataList,
        )
        if (!uiState.isExpanded) {
            MNRaderButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp),
                text = "전체보기",
            ) {
                viewModel.setExpanded(true)
            }
            HomeAnimalList(
                animalDataList = uiState.shownAnimalDataList,
                onAnimalClick = { animalData ->
                    navigateToAnimalDetail(animalData.id.toInt())
                },
                onBookmarkClick = { animalData ->
                    viewModel.setBookmark(animalData)
                },
            )
        }
    }
    if (uiState.isExpanded) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            MapAnimalInfo(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 100.dp)
                    .shadow(2.dp),
                animalData = HomeAnimalData.dummyHomeAnimalData.first(),
                onItemClick = { /*TODO*/ }
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 60.dp)
                    .background(
                        color = Green2,
                        shape = CircleShape
                    ),
                onClick = {
                    viewModel.setExpanded(false)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        padding = PaddingValues(),
    )
}