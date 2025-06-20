package com.example.mnrader.ui.home.screen

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mnrader.data.datastore.LastAnimalDataStore
import com.example.mnrader.data.repository.AnimalRepository
import com.example.mnrader.data.repository.DataPortalRepository
import com.example.mnrader.data.repository.HomeRepository
import com.example.mnrader.data.repository.NaverRepository
import com.example.mnrader.ui.common.MNRaderButton
import com.example.mnrader.ui.common.NotificationPermissionDialog
import com.example.mnrader.ui.home.component.HomeAnimalList
import com.example.mnrader.ui.home.component.HomeFilter
import com.example.mnrader.ui.home.component.HomeTopBar
import com.example.mnrader.ui.home.component.MapAnimalInfo
import com.example.mnrader.ui.home.component.MapComponent
import com.example.mnrader.ui.home.model.AnimalDataType
import com.example.mnrader.ui.home.viewmodel.HomeViewModel
import com.example.mnrader.ui.home.viewmodel.HomeViewModelFactory
import com.example.mnrader.ui.theme.Green2
import com.example.mnrader.ui.util.NotificationPermissionUtil
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(
    navigateToMNAnimalDetail: (Long) -> Unit = {},
    navigateToPortalLostDetail: (Long) -> Unit = {},
    navigateToPortalProtectDetail: (Long) -> Unit = {},
    navigateToNotification: (Int) -> Unit = {},
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            DataPortalRepository(LocalContext.current),
            NaverRepository(),
            HomeRepository(LocalContext.current),
            LastAnimalDataStore(LocalContext.current),
            AnimalRepository()
        )
    ),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    val cameraPositionState = rememberCameraPositionState()
    
    // 알림 권한 요청 런처
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // 권한 요청 완료 처리
        viewModel.onNotificationPermissionRequestCompleted(context)
        
        Log.d("HomeScreen", "Notification permission granted: $isGranted")
    }
    
    // 카메라 위치 업데이트
    LaunchedEffect(uiState.cameraLatLng) {
        cameraPositionState.position = CameraPosition(
            uiState.cameraLatLng,
            15.0,
            0.0,
            0.0
        )
    }
    
    // 화면이 다시 보이게 될 때 새로운 알림 상태 확인 및 권한 체크
    LaunchedEffect(Unit) {
        viewModel.checkNewNotificationStatus()
        viewModel.checkNotificationPermission(context)
    }

    // 알림 권한 다이얼로그
    NotificationPermissionDialog(
        showDialog = uiState.showNotificationPermissionDialog,
        onRequestPermission = {
            if (NotificationPermissionUtil.isNotificationPermissionRequired()) {
                notificationPermissionLauncher.launch(NotificationPermissionUtil.getNotificationPermission())
            } else {
                // Android 13 미만에서는 권한 요청 없이 완료 처리
                viewModel.onNotificationPermissionRequestCompleted(context)
            }
        },
        onDismiss = {
            // "나중에" 버튼 클릭 시에도 완료 처리
            viewModel.onNotificationPermissionRequestCompleted(context)
        }
    )
    Scaffold(
        topBar = {
            HomeTopBar(
                modifier = Modifier.shadow(1.dp),
                hasNewNotification = uiState.hasNewNotification,
                onNotificationClick = { 
                    navigateToNotification(uiState.lastAnimal) 
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (uiState.isExpanded) {
                            Modifier
                        } else {
                            Modifier
                                .verticalScroll(state = scrollState)
                        }
                    )
            ) {

                Spacer(Modifier.height(5.dp))
                HomeFilter(
                    city = uiState.locationFilter,
                    breed = uiState.breedFilter,
                    onLocationUpdate = { viewModel.setLocation(it) },
                    onBreedUpdate = { viewModel.setBreed(it) },
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
                    animalDataList = uiState.shownMapAnimalDataList,
                    onMarkerClick = { viewModel.setSelectedAnimal(it) },
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
                    
                    // 검색 결과 표시 또는 빈 상태 메시지
                    if (!uiState.isLoading && uiState.shownAnimalDataList.isEmpty()) {
                        // 검색 결과가 없을 때 메시지 표시
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "검색 결과가 없습니다.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                    } else if (!uiState.isLoading) {
                        // 검색 결과가 있을 때 리스트 표시
                        HomeAnimalList(
                            animalDataList = uiState.shownAnimalDataList,
                            onAnimalClick = { animalData ->
                                Log.d(
                                    "HomeScreen",
                                    "onAnimalClick: ${animalData.id}, type: ${animalData.type}"
                                )
                                when (animalData.type) {
                                    AnimalDataType.PORTAL_LOST -> {
                                        navigateToPortalLostDetail(animalData.id)
                                    }

                                    AnimalDataType.PORTAL_PROTECT -> {
                                        navigateToPortalProtectDetail(animalData.id)
                                    }

                                    AnimalDataType.MN_LOST,
                                    AnimalDataType.MY_WITNESS -> {
                                        navigateToMNAnimalDetail(animalData.id)
                                    }
                                }
                            },
                            onBookmarkClick = { animalData ->
                                viewModel.setBookmark(animalData)
                            },
                        )
                    }
                }

                // 로딩 프로그레스바 - 중앙에 표시 (Material 3 스타일)
                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicator(
                            modifier = Modifier
                                .size(48.dp),
                            color = Green2,
                        )
                    }
                }
            }

            if (uiState.isExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val selectedAnimal = uiState.selectedAnimal
                    if (selectedAnimal != null) {
                        MapAnimalInfo(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 100.dp),
                            animalData = selectedAnimal,
                            onItemClick = { animalData ->
                                when (animalData.type) {
                                    AnimalDataType.PORTAL_LOST -> {
                                        navigateToPortalLostDetail(animalData.id)
                                    }

                                    AnimalDataType.PORTAL_PROTECT -> {
                                        navigateToPortalProtectDetail(animalData.id)
                                    }

                                    AnimalDataType.MN_LOST,
                                    AnimalDataType.MY_WITNESS -> {
                                        navigateToMNAnimalDetail(animalData.id)
                                    }
                                }
                            },
                        )
                    }
                    IconButton(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 40.dp)
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
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
    )
}