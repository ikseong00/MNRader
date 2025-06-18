package com.example.mnrader.ui.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mnrader.model.City
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail)
        }
    }

    fun setAnimalExpanded() {
        _uiState.update {
            it.copy(isAnimalExpanded = !it.isAnimalExpanded)
        }
    }
    fun setEmailExpanded() {
        _uiState.update {
            it.copy(isEmailExpanded = !it.isEmailExpanded)
        }
    }
    fun setAddressExpanded() {
        _uiState.update {
            it.copy(isAddressExpanded = !it.isAddressExpanded)
        }
    }

    fun onAddressChange(newAddress: City) {
        _uiState.update {
            it.copy(address = newAddress)
        }
    }

    fun setNotificationEnabled(isEnabled: Boolean) {
        _uiState.update {
            it.copy(isNotificationEnabled = isEnabled)
        }
    }

    fun saveEmail() {
        // Logic to save email
    }

    fun saveCity() {
        // Logic to save address
    }

}

class SettingViewModelFactory(

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}