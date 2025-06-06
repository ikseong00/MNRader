package com.example.mnrader.ui.mypage.dataclass
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet (
    val id: Int,
    val name: String,
    val imageUrl: String,
    val species: String,
    val breed: String,
    val gender: String,
    val age: String,
    val description: String? = null,
    val imageUri: String? = null,
    val status: String = ""  // 실종, 보호중, 목격중 외에는 공란
) : Parcelable
