package com.example.mnrader.ui.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mnrader.ui.mypage.dataclass.Pet
import com.example.mnrader.ui.mypage.viewmodel.MyPageViewModel
import com.example.mnrader.ui.settings.dataclass.SettingUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    fun toggleSection(section: String) {
        _uiState.update {
            when (section) {
                "pet" -> it.copy(isPetSectionExpanded = !it.isPetSectionExpanded)
                "email" -> it.copy(isEmailSectionExpanded = !it.isEmailSectionExpanded)
                "region" -> it.copy(isRegionSectionExpanded = !it.isRegionSectionExpanded)
                else -> it
            }
        }
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updateCity(city: String) {
        _uiState.update { it.copy(selectedCity = city) }
    }

    fun toggleNotification(enabled: Boolean) {
        _uiState.update { it.copy(isNotificationEnabled = enabled) }
    }

    fun addPet(pet: Pet) {
        _uiState.update { it.copy(pets = it.pets + pet) }
    }

    fun saveSettings() {
        _uiState.update { it.copy(showSuccessPopup = true) }
        viewModelScope.launch {
            delay(2000)
            _uiState.update { it.copy(showSuccessPopup = false) }
        }
    }

    fun initWithMyPagePets(email: String, pets: List<Pet>) {
        _uiState.update {
            it.copy(
                email = email,
                pets = pets,
                selectedCity = "서울시"
            )
        }
    }

}