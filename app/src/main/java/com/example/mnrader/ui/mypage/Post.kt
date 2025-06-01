package com.example.mnrader.ui.mypage

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val id: String,
    val name: String,
    val gender: String,
    val region: String,
    val date: String,
    val imageUrl: String
) : Parcelable
