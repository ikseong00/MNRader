package com.example.mnrader.ui.add.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.mnrader.ui.add.model.AddUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

class AddViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    fun updateType(type: String) {
        _uiState.update { it.copy(type = type) }
    }

    fun updateName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun updateContact(contact: String) {
        _uiState.update { it.copy(contact = contact) }
    }

    fun updateAnimalType(animalType: String) {
        _uiState.update { it.copy(animalType = animalType) }
    }

    fun updateBreed(breed: String) {
        _uiState.update { it.copy(breed = breed) }
    }

    fun updateGender(gender: String) {
        _uiState.update { it.copy(gender = gender) }
    }

    fun updateLocation(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun updateDetailAddress(detailAddress: String) {
        _uiState.update { it.copy(detailAddress = detailAddress) }
    }

    fun updateDateTime(dateTime: LocalDateTime) {
        _uiState.update { it.copy(dateTime = dateTime) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun updateImageUri(imageUri: Uri?) {
        _uiState.update { it.copy(imageUri = imageUri) }
    }

    fun reset() {
        _uiState.value = AddUiState()
    }
}

data class AddUiState(
    val type: String = "",  // "report" or "lost"
    val name: String = "",
    val contact: String = "",
    val animalType: String = "", // 강아지, 고양이, 기타동물
    val breed: String = "",
    val gender: String = "",
    val location: String = "",
    val detailAddress: String = "",
    val dateTime: LocalDateTime = LocalDateTime.now(),
    val description: String = "",
    val imageUri: Uri? = null
)