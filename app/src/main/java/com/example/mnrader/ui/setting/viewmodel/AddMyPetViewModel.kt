package com.example.mnrader.ui.setting.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mnrader.ui.home.model.Gender
import com.example.mnrader.ui.setting.screen.AnimalType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddMyPetViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AddMyPetUiState())
    val uiState: StateFlow<AddMyPetUiState> = _uiState.asStateFlow()

    fun onAnimalTypeChange(animalType: AnimalType) {
        _uiState.update {
            it.copy(
                selectedAnimalType = animalType,
                selectedBreed = null // 동물 종류 변경시 품종 초기화
            )
        }
    }

    fun onBreedChange(breed: String) {
        _uiState.update {
            it.copy(selectedBreed = breed)
        }
    }

    fun onGenderChange(gender: Gender) {
        _uiState.update {
            it.copy(selectedGender = gender)
        }
    }

    fun onPetNameChange(name: String) {
        _uiState.update {
            it.copy(petName = name)
        }
    }

    fun onPetAgeChange(age: String) {
        _uiState.update {
            it.copy(petAge = age)
        }
    }

    fun onPetCharacteristicsChange(characteristics: String) {
        _uiState.update {
            it.copy(petCharacteristics = characteristics)
        }
    }

    fun onImageSelected(uri: Uri?) {
        _uiState.update {
            it.copy(selectedImageUri = uri)
        }
    }

    fun setAnimalTypeExpanded(expanded: Boolean) {
        _uiState.update {
            it.copy(isAnimalTypeExpanded = expanded)
        }
    }

    fun setBreedExpanded(expanded: Boolean) {
        _uiState.update {
            it.copy(isBreedExpanded = expanded)
        }
    }

    fun setGenderExpanded(expanded: Boolean) {
        _uiState.update {
            it.copy(isGenderExpanded = expanded)
        }
    }

    fun savePet() {
        _uiState.update {
            it.copy(isLoading = true, errorMessage = null)
        }
        
        // TODO: 반려동물 저장 로직 구현
        // Repository를 통해 서버에 저장하거나 로컬 DB에 저장
        
        _uiState.update {
            it.copy(isLoading = false)
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}

class AddMyPetViewModelFactory(
    // TODO: Repository 등 필요한 의존성 추가
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddMyPetViewModel::class.java)) {
            return AddMyPetViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 