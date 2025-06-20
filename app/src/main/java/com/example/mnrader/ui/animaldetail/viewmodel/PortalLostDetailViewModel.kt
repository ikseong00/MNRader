package com.example.mnrader.ui.animaldetail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.entity.ScrapEntity
import com.example.mnrader.data.repository.DataPortalRepository
import com.example.mnrader.mapper.toPortalAnimalDetailData
import com.example.mnrader.model.City.Companion.fromDisplayName
import com.example.mnrader.ui.home.model.AnimalDataType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PortalLostDetailViewModel(
    private val dataPortalRepository: DataPortalRepository,
    private val animalId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(PortalLostDetailUiState())
    val uiState: StateFlow<PortalLostDetailUiState> = _uiState.asStateFlow()

    init {
        loadAnimalDetail()
        checkScrapStatus()
    }

    private fun loadAnimalDetail() {
        viewModelScope.launch {
            dataPortalRepository.getLostAnimalById(animalId)
                .onSuccess { lostAnimal ->
                    lostAnimal?.let { entity ->
                        val animalDetail = entity.toPortalAnimalDetailData()
                        _uiState.update {
                            it.copy(
                                animalDetail = animalDetail
                            )
                        }
                    }
                }
                .onFailure { exception ->
                    Log.e(
                        "PortalLostDetailViewModel",
                        "Error loading animal detail: ${exception.message}"
                    )
                }
        }
    }

    private fun checkScrapStatus() {
        viewModelScope.launch {
            dataPortalRepository.isScrapExist(animalId.toString())
                .onSuccess { isScrapExist ->
                    _uiState.update {
                        it.copy(isBookmarked = isScrapExist)
                    }
                }
                .onFailure { exception ->
                    Log.e("PortalLostDetailViewModel", "Error checking scrap status: ${exception.message}")
                }
        }
    }

    fun toggleBookmark() {
        val currentBookmarkState = _uiState.value.isBookmarked
        val newBookmarkState = !currentBookmarkState

        viewModelScope.launch {
            // UI 상태를 먼저 업데이트 (낙관적 업데이트)
            _uiState.update {
                it.copy(isBookmarked = newBookmarkState)
            }

            if (newBookmarkState) {
                // 스크랩 추가
                val animalDetail = _uiState.value.animalDetail
                val scrapEntity = ScrapEntity(
                    id = "", // DataPortalRepository에서 생성
                    animalId = animalId.toString(),
                    userEmail = "", // DataPortalRepository에서 설정
                    animalType = AnimalDataType.PORTAL_LOST,
                    imageUrl = animalDetail.imageUrl,
                    name = animalDetail.breed,
                    location = animalDetail.location,
                    date = animalDetail.date,
                    gender = animalDetail.gender,
                    city = fromDisplayName(animalDetail.location)?.code ?: 1,
                    isScrapped = true
                )
                dataPortalRepository.insertScrap(scrapEntity)
                    .onFailure { exception ->
                        // 실패 시 원래 상태로 되돌리기
                        _uiState.update {
                            it.copy(isBookmarked = currentBookmarkState)
                        }
                        Log.e("PortalLostDetailViewModel", "Error inserting scrap: ${exception.message}")
                    }
            } else {
                // 스크랩 제거
                dataPortalRepository.deleteScrapById(animalId.toString())
                    .onFailure { exception ->
                        // 실패 시 원래 상태로 되돌리기
                        _uiState.update {
                            it.copy(isBookmarked = currentBookmarkState)
                        }
                        Log.e("PortalLostDetailViewModel", "Error deleting scrap: ${exception.message}")
                    }
            }
        }
    }

}

class PortalLostDetailViewModelFactory(
    private val dataPortalRepository: DataPortalRepository,
    private val animalId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PortalLostDetailViewModel::class.java)) {
            return PortalLostDetailViewModel(dataPortalRepository, animalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 