package com.example.mnrader.ui.home.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.request.transformations
import coil3.toBitmap
import coil3.transform.CircleCropTransformation
import com.example.mnrader.R
import com.example.mnrader.ui.home.model.HomeAnimalData
import com.example.mnrader.ui.util.createCircularBitmapWithBorder
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun ColumnScope.MapComponent(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    animalDataList: List<HomeAnimalData> = emptyList(),
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMarkerClick: (HomeAnimalData) -> Unit = {}
) {
    // 1) 동물별로 로드된 Bitmap을 저장할 StateMap (key: userId 또는 고유 문자열)
    val context = LocalContext.current
    val markerBitmaps = remember { mutableStateMapOf<Long, Bitmap?>() }

    LaunchedEffect(animalDataList) {
        animalDataList.forEach { animal ->
            // animal.id를 고유 키로 사용假정 (HomeAnimalData에 id 필드가 있다고 가정)
            val key = animal.id
            if (markerBitmaps[key] == null) {
                // 처음 로드 시에만 코루틴으로 이미지 가져오기
                launch(Dispatchers.IO) {
                    try {
                        val loader = ImageLoader.Builder(context)
                            .allowHardware(false)
                            .build()

                        val request = ImageRequest.Builder(context)
                            .data(animal.imageUrl)
                            .build()

                        when (val result = loader.execute(request)) {
                            is SuccessResult -> {
                                val originalImage = result.image.toBitmap()
                                val diameterPx = 80
                                val borderPx = 4
                                val borderColor = 0xFF88C4A8.toInt()
                                // 지도 마커용으로 크기를 조정 (예: 60×60px)
                                val circleBmp = createCircularBitmapWithBorder(
                                    originalImage,
                                    diameterPx,
                                    borderPx,
                                    borderColor
                                )
                                markerBitmaps[key] = circleBmp
                            }

                            else -> {
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("MarkerLoad", "Coil 3 로드 중 예외: ${e.message}")
                        markerBitmaps[key] = null
                    }
                }
            }
        }
    }
    // 1) Bitmap 상태를 저장할 변수
    var markerBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // 2) Coil ImageLoader를 사용해 백그라운드에서 이미지 로드
    LaunchedEffect(Unit) {
        try {
            // Coil ImageLoader 세팅 (allowHardware=false 로 해야 Drawable→Bitmap 변환이 매끄럽습니다)
            val loader = ImageLoader.Builder(context)
                .allowHardware(false)
                .build()

            val request = ImageRequest.Builder(context)
                .data("http://openapi.animal.go.kr/openapi/service/rest/fileDownloadSrvc/files/shelter/2025/04/202504151104820.jpg")
                .transformations(CircleCropTransformation())
                .build()

            when (val result = loader.execute(request)) {
                is SuccessResult -> {
                    // result.image가 coil3.Image → 내부적으로 Bitmap 객체입니다.
                    val originalImage = result.image.toBitmap()
                    val diameterPx = 80
                    val borderPx = 4
                    val borderColor = 0xFFFF5722.toInt()
                    // 지도 마커용으로 크기를 조정 (예: 60×60px)
                    val circleBmp = createCircularBitmapWithBorder(
                        originalImage,
                        diameterPx,
                        borderPx,
                        borderColor
                    )
                    markerBitmap = circleBmp
                }

                else -> {
                }
            }

        } catch (e: Exception) {
            Log.e("MarkerLoad", "이미지 로드 실패: ${e.message}")
        }
    }

    // 건국대학교 위치 (위도: 37.5407, 경도: 127.0791)
    val konkukUniversity = remember { LatLng(37.5407, 127.0791) }

    // 맵 속성 설정
    val mapProperties = remember {
        MapProperties(
            maxZoom = 18.0,
            minZoom = 5.0,
        )
    }

    NaverMap(
        modifier = modifier
            .fillMaxWidth()
            .padding(14.dp)
            .then(
                if (isExpanded) Modifier.weight(1f) else Modifier.aspectRatio(1f)
            ),
        cameraPositionState = cameraPositionState,
        properties = mapProperties,
    ) {
        // 건국대학교 위치에 마커 표시
        Marker(
            captionText = "건국대학교",
            state = MarkerState(
                position = konkukUniversity,
            ),
            icon = markerBitmap?.let { OverlayImage.fromBitmap(it) }
                ?: OverlayImage.fromResource(R.drawable.ic_home_pin)
        )
        animalDataList.forEachIndexed { index, animal ->
            // 각 동물 데이터에 대한 마커 표시
            Marker(
                captionText = animal.name,
                state = MarkerState(
                    position = animal.latLng,
                ),
                icon = markerBitmaps[animal.id]?.let { OverlayImage.fromBitmap(it) }
                    ?: OverlayImage.fromResource(R.drawable.ic_home_pin),
                onClick = {
                    onMarkerClick(animal)
                    true // 클릭 이벤트를 소비합니다.
                }
            )
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Preview
@Composable
private fun MapComponentPreview() {
    Column { MapComponent() }
}