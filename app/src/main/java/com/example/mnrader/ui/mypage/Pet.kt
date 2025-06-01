package com.example.mnrader.ui.mypage
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet (
    val id: String,
    val name: String,
    val imageUrl: String, // 서버에 저장된 동물 이미지
    val species: String,
    val breed: String,
    val gender: String,
    val age: String,
    val description: String,
    val imageUri: String? = null // 로컬에서 선택한 업로드용 이미지
):Parcelable