package com.example.mnrader.ui.setting.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.repository.UserRepository
import com.example.mnrader.model.City
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

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
        viewModelScope.launch {
            userRepository.updateEmail(_uiState.value.email).fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(email = _uiState.value.email)
                    }
                },
                onFailure = { error ->
                    Log.e("SettingViewModel", "Error updating email: $error")
                }
            )
        }
    }

    fun saveCity() {
        viewModelScope.launch {
            val address = _uiState.value.address
            userRepository.updateCity(address.code).fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(address = address)
                    }
                },
                onFailure = { error ->
                    Log.e("SettingViewModel", "Error updating address: $error")
                }
            )
        }
    }

}

class SettingViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}