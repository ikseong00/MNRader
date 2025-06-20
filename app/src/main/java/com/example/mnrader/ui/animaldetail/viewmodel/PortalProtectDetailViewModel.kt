package com.example.mnrader.ui.animaldetail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.entity.ScrapEntity
import com.example.mnrader.data.repository.DataPortalRepository
import com.example.mnrader.mapper.toPortalAnimalDetailData
import com.example.mnrader.ui.home.model.AnimalDataType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PortalProtectDetailViewModel(
    private val dataPortalRepository: DataPortalRepository,
    private val animalId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(PortalProtectDetailUiState())
    val uiState: StateFlow<PortalProtectDetailUiState> = _uiState.asStateFlow()

    init {
        loadAnimalDetail()
        checkScrapStatus()
    }

    private fun loadAnimalDetail() {
        viewModelScope.launch {
            val desertionNo = animalId.toString()
            dataPortalRepository.getAbandonedAnimalByDesertionNo(desertionNo)
                .onSuccess { response ->
                    val animalDetail = response.toPortalAnimalDetailData()
                    _uiState.update {
                        it.copy(
                            animalDetail = animalDetail
                        )
                    }
                }
                .onFailure { exception ->
                    Log.e(
                        "PortalProtectDetailViewModel",
                        "Error loading animal detail: ${exception.message}"
                    )
                }
        }
    }

    private fun checkScrapStatus() {
        viewModelScope.launch {
            // desertionNo를 id로 사용
            dataPortalRepository.isScrapExist(animalId.toString())
                .onSuccess { isScrapExist ->
                    _uiState.update {
                        it.copy(isBookmarked = isScrapExist)
                    }
                }
                .onFailure { exception ->
                    Log.e("PortalProtectDetailViewModel", "Error checking scrap status: ${exception.message}")
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
                // 스크랩 추가 - desertionNo를 id로 사용
                val animalDetail = _uiState.value.animalDetail
                val scrapEntity = ScrapEntity(
                    id = "", // DataPortalRepository에서 생성
                    animalId = animalId.toString(), // desertionNo
                    userEmail = "", // DataPortalRepository에서 설정
                    animalType = AnimalDataType.PORTAL_PROTECT,
                    imageUrl = animalDetail?.imageUrl,
                    name = animalDetail?.breed ?: "",
                    location = animalDetail?.location ?: "",
                    date = animalDetail?.date ?: "",
                    gender = animalDetail?.gender ?: "성별 미상",
                    city = 1, // TODO: 실제 지역 정보로 변경 필요
                    isScrapped = true
                )
                dataPortalRepository.insertScrap(scrapEntity)
                    .onFailure { exception ->
                        // 실패 시 원래 상태로 되돌리기
                        _uiState.update {
                            it.copy(isBookmarked = currentBookmarkState)
                        }
                        Log.e("PortalProtectDetailViewModel", "Error inserting scrap: ${exception.message}")
                    }
            } else {
                // 스크랩 제거
                dataPortalRepository.deleteScrapById(animalId.toString())
                    .onFailure { exception ->
                        // 실패 시 원래 상태로 되돌리기
                        _uiState.update {
                            it.copy(isBookmarked = currentBookmarkState)
                        }
                        Log.e("PortalProtectDetailViewModel", "Error deleting scrap: ${exception.message}")
                    }
            }
        }
    }

}

class PortalProtectDetailViewModelFactory(
    private val dataPortalRepository: DataPortalRepository,
    private val animalId: Long
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PortalProtectDetailViewModel::class.java)) {
            return PortalProtectDetailViewModel(dataPortalRepository, animalId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 