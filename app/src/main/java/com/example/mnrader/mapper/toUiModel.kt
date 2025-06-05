package com.example.mnrader.mapper

import com.example.mnrader.data.dto.AbandonedResponseDto
import com.example.mnrader.data.entity.LostAnimalEntity
import com.example.mnrader.ui.home.model.AnimalDataType
import com.example.mnrader.ui.home.model.Gender
import com.example.mnrader.ui.home.model.HomeAnimalData
import com.naver.maps.geometry.LatLng

fun AbandonedResponseDto.toUiModel(): List<HomeAnimalData> =
    this.response.body.items.item.map {
        HomeAnimalData(
            id = it.desertionNo.toLong(),
            imageUrl = it.popfile1,
            name = it.kindFullNm,
            gender = when (it.sexCd) {
                "F" -> Gender.FEMALE
                else -> Gender.MALE
            },
            location = it.orgNm,
            date = it.updTm.split(" ").first(),
            type = AnimalDataType.PROTECT,
            latLng = LatLng(0.0, 0.0), // TODO: 위치 정보를 업데이트해야함.
            isBookmarked = false // TODO: 나중에 북마크 정보를 다시 할당해야함.
        )
    }

fun LostAnimalEntity.toUiModel(): HomeAnimalData =
    HomeAnimalData(
        id = this.id,
        imageUrl = this.popfile,
        name = this.kindCd,
        gender = this.sexCd,
        location = this.orgNm,
        date = this.happenDate.split(" ").first(),
        type = AnimalDataType.LOST,
        latLng = LatLng(0.0, 0.0), // TODO: 위치 정보를 업데이트해야함.
        isBookmarked = false // TODO: 나중에 북마크 정보를 다시 할당해야함.
    )
