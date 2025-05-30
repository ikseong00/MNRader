package com.example.mnrader.ui.mypage
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet (
    val id: String,
    val name: String,
    val imageUrl: String
):Parcelable