package com.example.mnrader.ui.setting.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mnrader.ui.mypage.dataclass.Pet
import com.example.mnrader.ui.mypage.repository.PetRepository
import com.example.mnrader.ui.settings.dataclass.SettingUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File

class SettingViewModel(application: Application) : AndroidViewModel(application) {

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

    fun addPet(
        pet: Pet,
        imageUri: Uri?,
        onResult: (Boolean) -> Unit
    ) {
        val animalCode = when (pet.species) {
            "강아지" -> 1
            "고양이" -> 2
            else -> 3
        }

        val genderCode = when (pet.gender) {
            "수컷" -> 1
            "암컷" -> 2
            else -> 3
        }

        val statusCode = when (pet.status) {
            "실종" -> 1
            "보호중" -> 2
            "목격중" -> 3
            else -> 4 // None이 디폴트
        }


        val parsedAge = pet.age.filter { it.isDigit() }.toIntOrNull() ?: 0

        val repository = PetRepository()

        repository.addPetData(
            imageUri = imageUri,
            animal = animalCode,
            breed = pet.breed,
            gender = genderCode,
            name = pet.name.ifBlank { pet.breed },
            age = parsedAge,
            detail = pet.description ?: "",
            status = statusCode,
            getFileFromUri = { uri -> uriToFile(uri) },
            onResult = onResult
        )
    }

    private fun uriToFile(uri: Uri): File? {
        val context = getApplication<Application>().applicationContext
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
        file.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }
        return file
    }


}