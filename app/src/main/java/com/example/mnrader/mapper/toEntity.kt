package com.example.mnrader.mapper

import com.example.mnrader.data.dto.LostPortalResponseDto
import com.example.mnrader.data.entity.LostAnimalEntity
import com.example.mnrader.ui.home.model.Gender

fun LostPortalResponseDto.toEntityList(): List<LostAnimalEntity> =
    this.response.body.items.item.map { item ->
        LostAnimalEntity(
            kindCd = item.kindCd,
            orgNm = item.orgNm,
            popfile = item.popfile,
            specialMark = item.specialMark,
            sexCd = when (item.sexCd) {
                "F" -> Gender.FEMALE
                else -> Gender.MALE
            },
            location = item.happenAddr,
            happenDate = item.happenDt,
        )
    }

