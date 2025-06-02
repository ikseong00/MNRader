package com.example.mnrader.model

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
//동물 등록하기 data
data class RegisterData(
    var type: String = "",  // "report" or "lost"
    var name: String = "",
    var contact: String = "",
    var animalType: String = "", // 강아지, 고양이, 기타동물
    var breed: String = "",
    var gender: String = "",
    var location: String = "",
    var detailAddress: String = "",
    var dateTime: LocalDateTime = LocalDateTime.now(),
    var description: String = "",
    var imageUri: Uri? = null
)
class RegisterViewModel : ViewModel() {
    var registerData by mutableStateOf(RegisterData())
    fun reset() { registerData = RegisterData() }
}