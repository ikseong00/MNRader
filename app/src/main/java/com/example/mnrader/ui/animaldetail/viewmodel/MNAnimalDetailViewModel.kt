package com.example.mnrader.ui.animaldetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.repository.AnimalRepository
import com.example.mnrader.mapper.toMNAnimalDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MNAnimalDetailViewModel(
    private val animalRepository: AnimalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MNAnimalDetailUiState())
    val uiState: StateFlow<MNAnimalDetailUiState> = _uiState.asStateFlow()

    fun loadAnimalDetail(animalId: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            animalRepository.getAnimalDetail(animalId)
                .onSuccess { response ->
                    response.result?.let { animalDetailDto ->
                        _uiState.value = _uiState.value.copy(
                            animalDetail = animalDetailDto.toMNAnimalDetail(),
                            isLoading = false,
                            errorMessage = null
                        )
                    } ?: run {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = "동물 정보를 불러올 수 없습니다."
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "오류가 발생했습니다."
                    )
                }
        }
    }

    fun toggleScrap() {
        _uiState.value.animalDetail?.let { currentDetail ->
            _uiState.value = _uiState.value.copy(
                animalDetail = currentDetail.copy(
                    isScrapped = !currentDetail.isScrapped
                )
            )
            // TODO: 스크랩 API 호출
        }
    }
}

class MNAnimalDetailViewModelFactory(
    private val animalRepository: AnimalRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MNAnimalDetailViewModel::class.java)) {
            return MNAnimalDetailViewModel(animalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 