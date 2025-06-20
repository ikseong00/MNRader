package com.example.mnrader.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val email: String,
    val password: String,
    val city: Int,
    val createdAt: Long = System.currentTimeMillis()
) 