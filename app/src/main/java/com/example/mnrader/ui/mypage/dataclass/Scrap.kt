package com.example.mnrader.ui.mypage.dataclass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Scrap(
    val id: String,
    val pet: Pet,
    val region: String,
    val date: String
) : Parcelable
