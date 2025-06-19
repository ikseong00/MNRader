package com.example.mnrader.ui.add.model

import android.net.Uri
import java.time.LocalDateTime

//동물 등록하기 data
data class AddUiState(
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
