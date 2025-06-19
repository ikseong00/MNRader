package com.example.mnrader.ui.setting.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.dto.user.request.UpdateUserAnimalRequestDto
import com.example.mnrader.data.dto.user.request.UserAnimalRequestDto
import com.example.mnrader.data.repository.UserAnimalRepository
import com.example.mnrader.ui.home.model.Gender
import com.example.mnrader.ui.setting.screen.AnimalType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class AddMyPetViewModel(
    private val userAnimalRepository: UserAnimalRepository,
    private val context: Context
) : ViewModel() {

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
        val currentState = _uiState.value

        _uiState.update {
            it.copy(isLoading = true, errorMessage = null)
        }
        Log.d("AddMyPetViewModel", "Saving pet with state: $currentState")
        
        viewModelScope.launch {
            try {
                // URI를 File로 변환
                val imageFile = uriToFile(currentState.selectedImageUri!!)
                
                // 요청 DTO 생성
                val requestDto = UserAnimalRequestDto(
                    animal = mapAnimalTypeToInt(currentState.selectedAnimalType!!),
                    breed = mapBreedToInt(currentState.selectedBreed!!),
                    gender = mapGenderToInt(currentState.selectedGender!!),
                    name = currentState.petName,
                    age = currentState.petAge.toInt(),
                    detail = currentState.petCharacteristics
                )
                // API 호출
                userAnimalRepository.createUserAnimal(requestDto, imageFile)
                    .onSuccess { response ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = null,
                                addSuccess = true
                            )
                        }
                        // 성공 시 상태 초기화
                        resetForm()
                    }
                    .onFailure { exception ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = exception.message ?: "동물 등록에 실패했습니다."
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "알 수 없는 오류가 발생했습니다."
                    )
                }
                Log.e("AddMyPetViewModel", "Error saving pet: ${e.message}", e)
            }
        }
    }
    
    private fun mapAnimalTypeToInt(animalType: AnimalType): Int {
        return when (animalType) {
            AnimalType.DOG -> 1
            AnimalType.CAT -> 2
            AnimalType.OTHER -> 3
        }
    }
    
    private fun mapBreedToInt(breed: String): Int {
        // TODO: 실제 품종 매핑 로직 구현 필요
        return 1 // 임시값
    }
    
    private fun mapGenderToInt(gender: Gender): Int {
        return when (gender) {
            Gender.MALE -> 1
            Gender.FEMALE -> 2
        }
    }
    
    private fun uriToFile(uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
        val outputStream = FileOutputStream(tempFile)
        
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        
        return tempFile
    }
    
    private fun resetForm() {
        _uiState.update {
            it.copy(
                selectedAnimalType = null,
                selectedBreed = null,
                selectedGender = null,
                petName = "",
                petAge = "",
                petCharacteristics = "",
                selectedImageUri = null,
                isAnimalTypeExpanded = false,
                isBreedExpanded = false,
                isGenderExpanded = false
            )
        }
    }
    
    fun updatePet(animalId: Int) {
        val currentState = _uiState.value
        
        // 유효성 검사 (name은 제외, PATCH에서는 name 필드가 없음)
        if (currentState.selectedAnimalType == null ||
            currentState.selectedBreed == null ||
            currentState.selectedGender == null ||
            currentState.petAge.isBlank() ||
            currentState.petCharacteristics.isBlank() ||
            currentState.selectedImageUri == null
        ) {
            _uiState.update {
                it.copy(errorMessage = "모든 필드를 입력해주세요.")
            }
            return
        }

        _uiState.update {
            it.copy(isLoading = true, errorMessage = null)
        }
        
        viewModelScope.launch {
            try {
                // URI를 File로 변환
                val imageFile = uriToFile(currentState.selectedImageUri!!)
                
                // 요청 DTO 생성 (name 제외)
                val requestDto = UpdateUserAnimalRequestDto(
                    animal = mapAnimalTypeToInt(currentState.selectedAnimalType!!),
                    breed = mapBreedToInt(currentState.selectedBreed!!),
                    gender = mapGenderToInt(currentState.selectedGender!!),
                    age = currentState.petAge.toInt(),
                    detail = currentState.petCharacteristics
                )
                
                // API 호출
                userAnimalRepository.updateUserAnimal(animalId, requestDto, imageFile)
                    .onSuccess { response ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                        // 성공 시 상태 초기화
                        resetForm()
                    }
                    .onFailure { exception ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = exception.message ?: "동물 수정에 실패했습니다."
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "알 수 없는 오류가 발생했습니다."
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}

class AddMyPetViewModelFactory(
    private val userAnimalRepository: UserAnimalRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddMyPetViewModel::class.java)) {
            return AddMyPetViewModel(userAnimalRepository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 