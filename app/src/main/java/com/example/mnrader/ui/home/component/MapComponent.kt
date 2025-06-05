package com.example.mnrader.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mnrader.R
import com.example.mnrader.ui.home.model.MapAnimalData
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MarkerComposable
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun ColumnScope.MapComponent(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    animalDataList: List<MapAnimalData> = emptyList(),
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    onMarkerClick: (MapAnimalData) -> Unit = {}
) {
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
        MarkerComposable(
            state = MarkerState(position = konkukUniversity),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .aspectRatio(1f),
                    painter = painterResource(R.drawable.ic_home_pin),
                    contentDescription = "Konkuk University",
                    tint = Color.Unspecified
                )
            }
        }
        animalDataList.forEachIndexed { index, animal ->
            MarkerComposable(
                captionText = "${animal.name} +${animal.count}",
                state = MarkerState(position = animal.latLng),
                onClick = {
                    onMarkerClick(animal)
                    true // 클릭 이벤트를 소비합니다.
                }
            ) {
                Box(
                    modifier = Modifier.size(96.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center),
                        painter = painterResource(R.drawable.ic_home_pin),
                        contentDescription = "Animal Marker",
                        tint = animal.type.color
                    )
//
//                    AsyncImage(
//                        modifier = Modifier
//                            .size(30.dp)
//                            .clip(CircleShape)
//                            .align(Alignment.Center)
//                            .padding(top = 40.dp),
//                        model = animal.imageUrl,
//                        contentDescription = "Animal Image",
//                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Preview
@Composable
private fun MapComponentPreview() {
    Column { MapComponent() }
}