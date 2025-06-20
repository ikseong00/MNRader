package com.example.mnrader.ui.animaldetail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.repository.AnimalRepository
import com.example.mnrader.mapper.toMNAnimalDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MNAnimalDetailViewModel(
    private val animalRepository: AnimalRepository,
    private val animalId: Long
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
        val currentDetail = _uiState.value.animalDetail
        val currentScrapState = currentDetail.isScrapped

        viewModelScope.launch {
            val result = if (currentScrapState) {
                // 현재 스크랩된 상태라면 스크랩 취소
                animalRepository.unscrapAnimal(animalId.toInt())
            } else {
                // 현재 스크랩되지 않은 상태라면 스크랩 추가
                animalRepository.scrapAnimal(animalId.toInt())
            }
            
            result.onSuccess {
                _uiState.update {
                    it.copy(
                        animalDetail = currentDetail.copy(
                            isScrapped = !currentScrapState
                        )
                    )
                }
            }.onFailure { exception ->
                Log.e(
                    "MNAnimalDetailViewModel",
                    "Error toggling scrap: ${exception.message}"
                )
            }
        }
    }
}

class MNAnimalDetailViewModelFactory(
    private val animalRepository: AnimalRepository,
    private val animalId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MNAnimalDetailViewModel::class.java)) {
            return MNAnimalDetailViewModel(animalRepository, animalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 