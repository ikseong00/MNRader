package com.example.mnrader.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.mnrader.ui.home.model.AnimalDataType

@Entity(
    tableName = "scraps",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["email"],
            childColumns = ["userEmail"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userEmail"])]
)
data class ScrapEntity(
    @PrimaryKey
    val id: String, // animalId + userEmail의 조합으로 유니크 ID 생성
    val animalId: String, // Portal Lost는 entity.id, Portal Protect는 desertionNo
    val userEmail: String, // 스크랩한 사용자의 이메일
    val animalType: AnimalDataType, // PORTAL_LOST 또는 PORTAL_PROTECT
    val imageUrl: String?,
    val name: String,
    val location: String,
    val date: String,
    val gender: String = "수컷", // 성별 정보 추가
    val city: Int = 1, // 도시 정보 추가
    val isScrapped: Boolean = true,
    val scrapTimestamp: Long = System.currentTimeMillis()
) 