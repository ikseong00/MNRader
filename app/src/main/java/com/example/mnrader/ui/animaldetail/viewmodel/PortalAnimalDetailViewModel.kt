package com.example.mnrader.ui.animaldetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.repository.DataPortalRepository
import com.example.mnrader.mapper.toUiModel
import com.example.mnrader.ui.home.model.AnimalDataType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PortalAnimalDetailViewModel(
    private val dataPortalRepository: DataPortalRepository,
    private val animalId: Long,
    private val animalType: AnimalDataType
) : ViewModel() {

    private val _uiState = MutableStateFlow(PortalAnimalDetailUiState())
    val uiState: StateFlow<PortalAnimalDetailUiState> = _uiState.asStateFlow()

    init {
        loadAnimalDetail()
    }

    private fun loadAnimalDetail() {
        _uiState.update {
            it.copy(isLoading = true, errorMessage = null)
        }

        viewModelScope.launch {
            when (animalType) {
                AnimalDataType.PORTAL_LOST -> {
                    // 실종동물 - Room에서 조회
                    dataPortalRepository.getLostAnimalById(animalId)
                        .onSuccess { lostAnimal ->
                            lostAnimal?.let { entity ->
                                val homeAnimalData = entity.toUiModel()
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        animalDetail = homeAnimalData
                                    )
                                }
                            } ?: run {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = "실종동물 정보를 찾을 수 없습니다."
                                    )
                                }
                            }
                        }
                        .onFailure { exception ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = exception.message ?: "실종동물 정보를 불러오는데 실패했습니다."
                                )
                            }
                        }
                }
                
                AnimalDataType.PORTAL_PROTECT -> {
                    // 구조동물 - 공공데이터 API에서 조회
                    val desertionNo = animalId.toString()
                    dataPortalRepository.getAbandonedAnimalByDesertionNo(desertionNo)
                        .onSuccess { response ->
                            val animalList = response.toUiModel()
                            if (animalList.isNotEmpty()) {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        animalDetail = animalList.first()
                                    )
                                }
                            } else {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        errorMessage = "구조동물 정보를 찾을 수 없습니다."
                                    )
                                }
                            }
                        }
                        .onFailure { exception ->
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = exception.message ?: "구조동물 정보를 불러오는데 실패했습니다."
                                )
                            }
                        }
                }
                
                else -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "지원하지 않는 동물 타입입니다."
                        )
                    }
                }
            }
        }
    }

    fun toggleBookmark() {
        _uiState.update {
            it.copy(isBookmarked = !it.isBookmarked)
        }
        // TODO: 북마크 상태를 서버나 로컬 DB에 저장하는 로직 추가
    }

    fun retry() {
        loadAnimalDetail()
    }

    fun clearError() {
        _uiState.update {
            it.copy(errorMessage = null)
        }
    }

    fun loadFromRoom(animalId: Long) {
        _uiState.update {
            it.copy(isLoading = true, errorMessage = null)
        }

        viewModelScope.launch {
            dataPortalRepository.getLostAnimalById(animalId)
                .onSuccess { lostAnimal ->
                    lostAnimal?.let { entity ->
                        val homeAnimalData = entity.toUiModel()
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                animalDetail = homeAnimalData
                            )
                        }
                    } ?: run {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "실종동물 정보를 찾을 수 없습니다."
                            )
                        }
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "실종동물 정보를 불러오는데 실패했습니다."
                        )
                    }
                }
        }
    }

    fun loadFromApi(animalId: Long) {
        _uiState.update {
            it.copy(isLoading = true, errorMessage = null)
        }

        viewModelScope.launch {
            val desertionNo = animalId.toString()
            dataPortalRepository.getAbandonedAnimalByDesertionNo(desertionNo)
                .onSuccess { response ->
                    val animalList = response.toUiModel()
                    if (animalList.isNotEmpty()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                animalDetail = animalList.first()
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "구조동물 정보를 찾을 수 없습니다."
                            )
                        }
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "구조동물 정보를 불러오는데 실패했습니다."
                        )
                    }
                }
        }
    }
}

class PortalAnimalDetailViewModelFactory(
    private val dataPortalRepository: DataPortalRepository,
    private val animalId: Long,
    private val animalType: AnimalDataType
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PortalAnimalDetailViewModel::class.java)) {
            return PortalAnimalDetailViewModel(dataPortalRepository, animalId, animalType) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 