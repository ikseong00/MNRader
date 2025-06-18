package com.example.mnrader.ui.setting.viewmodel

import com.example.mnrader.model.City
import com.example.mnrader.ui.mypage.viewmodel.MyAnimal

data class SettingUiState(
    val email: String = "",
    val address: City = City.SEOUL,
    val myAnimalList: List<MyAnimal> = emptyList(),
    val isAnimalExpanded: Boolean = false,
    val isEmailExpanded: Boolean = false,
    val isAddressExpanded: Boolean = false,
    val isNotificationEnabled: Boolean = false,
)
