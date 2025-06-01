package com.example.mnrader.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mnrader.data.repository.DataPortalRepository
import com.example.mnrader.ui.home.model.AnimalDataType
import com.example.mnrader.ui.home.model.HomeAnimalData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val dataPortalRepository: DataPortalRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                animalDataList = HomeAnimalData.dummyHomeAnimalData,
                shownAnimalDataList = HomeAnimalData.dummyHomeAnimalData
            )
        }
    }


    fun setExpanded(isExpanded: Boolean) {
        _uiState.value = _uiState.value.copy(isExpanded = isExpanded)
    }

    fun setLostShown(isShown: Boolean) {
        _uiState.update {
            it.copy(isLostShown = !isShown)
        }
        setShownAnimalDataList()
    }

    fun setProtectShown(isShown: Boolean) {
        _uiState.update {
            it.copy(isProtectShown = !isShown)
        }
        setShownAnimalDataList()
    }

    fun setWitnessShown(isShown: Boolean) {
        _uiState.update {
            it.copy(isWitnessShown = !isShown)
        }
        setShownAnimalDataList()
    }

    fun setShownAnimalDataList() {
        _uiState.update { currentState ->
            val shownList = currentState.animalDataList.filter { animalData ->
                (currentState.isLostShown && animalData.type == AnimalDataType.LOST) ||
                        (currentState.isProtectShown && animalData.type == AnimalDataType.PROTECT) ||
                        (currentState.isWitnessShown && animalData.type == AnimalDataType.WITNESS)
            }
            currentState.copy(shownAnimalDataList = shownList)
        }
    }

    fun setBookmark(animalData: HomeAnimalData) {
        _uiState.update { currentState ->
            val updatedList = currentState.shownAnimalDataList.map { data ->
                if (data.id == animalData.id) {
                    data.copy(isBookmarked = !data.isBookmarked)
                } else {
                    data
                }
            }
            currentState.copy(shownAnimalDataList = updatedList)
        }
    }


}

class HomeVieWModelFactory(private val dataPortalRepository: DataPortalRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataPortalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}