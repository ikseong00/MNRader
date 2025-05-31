package com.example.mnrader.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun ColumnScope.MapComponent(
    modifier: Modifier = Modifier,
    isExpanded: Boolean = false,
    cameraPositionState: CameraPositionState = rememberCameraPositionState()
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
        onMapLoaded = {
            // 맵이 로드된 후 카메라 위치 설정
            cameraPositionState.position = CameraPosition(konkukUniversity, 15.0)
        }
    ) {
        // 건국대학교 위치에 마커 표시
        Marker(
            captionText = "건국대학교",
            state = MarkerState(
                position = konkukUniversity,
            )
        )
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Preview
@Composable
private fun MapComponentPreview() {
    Column { MapComponent() }
}