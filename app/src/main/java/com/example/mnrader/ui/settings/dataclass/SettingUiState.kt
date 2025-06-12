package com.example.mnrader.ui.settings.dataclass

import com.example.mnrader.ui.mypage.dataclass.Pet

data class SettingUiState(
    val email: String = "",
    val selectedCity: String = "",
    val pets: List<Pet> = emptyList(),
    val isNotificationEnabled: Boolean = false,
    val isPetSectionExpanded: Boolean = false,
    val isEmailSectionExpanded: Boolean = false,
    val isRegionSectionExpanded: Boolean = false,
    val showSuccessPopup: Boolean = false
)
