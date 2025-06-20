package com.example.mnrader.ui.add.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.dto.animal.request.AnimalRegisterRequestDto
import com.example.mnrader.data.repository.AnimalRepository
import com.example.mnrader.ui.add.viewmodel.AddUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddViewModel(
    private val animalRepository: AnimalRepository,
    private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

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

    fun updateAge(age: Int) {
        _uiState.update { it.copy(age = age) }
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

    fun submitAnimalReport(onSuccess: () -> Unit = {}, onError: (String) -> Unit = {}) {
        val currentState = _uiState.value
        
        // 필수 필드 검증
        if (!validateInput(currentState)) {
            onError("모든 필수 정보를 입력해주세요.")
            return
        }

        _isLoading.update { true }
        _errorMessage.update { null }

        viewModelScope.launch {
            try {
                // URI를 File로 변환
                val imageFile = currentState.imageUri?.let { uri ->
                    uriToFile(uri, context)
                }

                if (imageFile == null) {
                    _isLoading.update { false }
                    onError("이미지를 선택해주세요.")
                    return@launch
                }

                // 주소 조합
                val fullAddress = if (currentState.detailAddress.isNotBlank()) {
                    "${currentState.location} ${currentState.detailAddress}"
                } else {
                    currentState.location
                }

                // DateTime을 String으로 변환
                val dateTimeString = currentState.dateTime.format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                )

                // AnimalRegisterRequestDto 생성
                val requestDto = AnimalRegisterRequestDto(
                    status = mapTypeToInt(currentState.type),
                    name = currentState.name,
                    contact = currentState.contact,
                    breed = mapBreedToInt(currentState.breed),
                    gender = mapGenderToInt(currentState.gender),
                    address = fullAddress,
                    occuredAt = dateTimeString,
                    detail = currentState.description
                )

                // API 호출
                animalRepository.registerAnimal(requestDto, imageFile)
                    .onSuccess { response ->
                        _isLoading.update { false }
                        Log.d("AddViewModel", "동물 등록 성공: ${response.message}")
                        onSuccess()
                    }
                    .onFailure { error ->
                        _isLoading.update { false }
                        val errorMsg = "동물 등록에 실패했습니다: ${error.message}"
                        Log.e("AddViewModel", errorMsg, error)
                        _errorMessage.update { errorMsg }
                        onError(errorMsg)
                    }

            } catch (e: Exception) {
                _isLoading.update { false }
                val errorMsg = "동물 등록 중 오류가 발생했습니다: ${e.message}"
                Log.e("AddViewModel", errorMsg, e)
                _errorMessage.update { errorMsg }
                onError(errorMsg)
            }
        }
    }

    // 기존 submitUserAnimal을 새로운 API로 리다이렉트
    fun submitUserAnimal(onSuccess: () -> Unit = {}, onError: (String) -> Unit = {}) {
        submitAnimalReport(onSuccess, onError)
    }

    private fun validateInput(state: AddUiState): Boolean {
        return state.type.isNotBlank() &&
                state.name.isNotBlank() &&
                state.contact.isNotBlank() &&
                state.animalType.isNotBlank() &&
                state.breed.isNotBlank() &&
                state.gender.isNotBlank() &&
                state.location.isNotBlank() &&
                state.description.isNotBlank() &&
                state.imageUri != null
    }

    private fun mapTypeToInt(type: String): Int {
        return when (type.lowercase()) {
            "lost" -> 1 // 실종
            "report" -> 2 // 목격
            else -> 2 // 기본값: 목격
        }
    }

    private fun mapBreedToInt(breed: String): Int {
        // 품종 매핑 - 실제 품종 코드가 필요하면 수정
        return when {
            breed.contains("골든") -> 1
            breed.contains("리트리버") -> 2
            breed.contains("시바") -> 3
            breed.contains("포메") -> 4
            breed.contains("말티즈") -> 5
            breed.contains("페르시안") -> 101
            breed.contains("러시안") -> 102
            breed.contains("브리티시") -> 103
            else -> 999 // 기타
        }
    }

    private fun mapGenderToInt(gender: String): Int {
        return when (gender) {
            "수컷", "MALE" -> 1
            "암컷", "FEMALE" -> 2
            else -> 1 // 기본값
        }
    }

    private fun uriToFile(uri: Uri, context: Context): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
            val outputStream = FileOutputStream(tempFile)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
            tempFile
        } catch (e: Exception) {
            Log.e("AddViewModel", "URI를 File로 변환하는 중 오류 발생", e)
            null
        }
    }

    fun clearError() {
        _errorMessage.update { null }
    }

    fun reset() {
        _uiState.value = AddUiState()
        _isLoading.value = false
        _errorMessage.value = null
    }
}

class AddViewModelFactory(
    private val animalRepository: AnimalRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(animalRepository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

