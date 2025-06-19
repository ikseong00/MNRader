package com.example.mnrader.ui.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.repository.UserAnimalRepository
import com.example.mnrader.mapper.toUserAnimalDetailUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyPetDetailViewModel(
    private val userAnimalRepository: UserAnimalRepository,
    private val animalId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPetDetailUiState())
    val uiState: StateFlow<MyPetDetailUiState> = _uiState.asStateFlow()

    init {
        loadAnimalDetail()
    }

    private fun loadAnimalDetail() {
        _uiState.update {
            it.copy(isLoading = true, errorMessage = null)
        }

        viewModelScope.launch {
            userAnimalRepository.getUserAnimalDetail(animalId)
                .onSuccess { response ->
                    response.result?.let { animalDetailDto ->
                        val animalDetailUiModel = animalDetailDto.toUserAnimalDetailUiModel()
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                animalDetail = animalDetailUiModel
                            )
                        }
                    } ?: run {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "동물 정보를 불러올 수 없습니다."
                            )
                        }
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "알 수 없는 오류가 발생했습니다."
                        )
                    }
                }
        }
    }

    fun retry() {
        loadAnimalDetail()
    }

    fun clearError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }
}

class MyPetDetailViewModelFactory(
    private val userAnimalRepository: UserAnimalRepository,
    private val animalId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyPetDetailViewModel::class.java)) {
            return MyPetDetailViewModel(userAnimalRepository, animalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 