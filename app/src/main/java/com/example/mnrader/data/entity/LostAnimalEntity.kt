package com.example.mnrader.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mnrader.ui.home.model.Gender

@Entity(tableName = "lost_animals")
data class LostAnimalEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val kindCd: String,
    val orgNm: String,
    val popfile: String,
    val callTel: String,
    val sexCd: Gender,
    val specialMark: String,
    val location: String,
    val happenAddr: String,
    val happenDate: String,
)