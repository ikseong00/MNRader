package com.example.mnrader.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.repository.DataPortalRepository
import com.example.mnrader.data.repository.NaverRepository
import com.example.mnrader.mapper.toUiModel
import com.example.mnrader.ui.home.model.AnimalDataType
import com.example.mnrader.ui.home.model.HomeAnimalData
import com.example.mnrader.ui.home.model.MapAnimalData
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataPortalRepository: DataPortalRepository,
    private val naverRepository: NaverRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getDataPortalAnimalData()
//        _uiState.update {
//            it.copy(
//                animalDataList = HomeAnimalData.dummyHomeAnimalData,
//                shownAnimalDataList = HomeAnimalData.dummyHomeAnimalData
//            )
//        }
    }

    fun setSelectedAnimal(animalData: MapAnimalData) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedAnimal = animalData,
                cameraLatLng = animalData.latLng
            )
        }
    }

    private fun getDataPortalAnimalData() {
        viewModelScope.launch {
            dataPortalRepository.fetchAbandonedAnimals().fold(
                onSuccess = { animalDataList ->
                    val homeAnimalDataList = animalDataList.toUiModel()
                    updateLatLngByLocation(homeAnimalDataList)
                },
                onFailure = { error ->
                    Log.d("HomeViewModel", "getDataPortalAnimalData: $error")
                }
            )
        }

        viewModelScope.launch {
            dataPortalRepository.fetchLostAnimals().fold(
                onSuccess = { animalDataList ->
                    val homeAnimalDataList = animalDataList.map { it.toUiModel() }
                    updateLatLngByLocation(homeAnimalDataList)
                },
                onFailure = { error ->
                    Log.d("HomeViewModel", "getLostAnimalData: $error")
                }
            )
        }
    }


    private fun updateLatLngByLocation(homeAnimalDataList: List<HomeAnimalData>) {
        viewModelScope.launch {

            val mapAnimalData: List<MapAnimalData> = homeAnimalDataList
                .groupBy { it.location } // Map<String, List<HomeAnimalData>>
                .map { (location, groupList) ->
                    val first = groupList.first()
                    MapAnimalData(
                        id = first.id,
                        imageUrl = first.imageUrl,
                        name = first.name,
                        location = location,
                        latLng = first.latLng,
                        type = first.type,
                        date = first.date,
                        gender = first.gender,
                        count = groupList.size
                    )
                }


            mapAnimalData.forEach { animalData ->
                naverRepository.getLatLngByLocation(animalData.location).fold(
                    onSuccess = { response ->
                        animalData.latLng = LatLng(
                            response.addresses.firstOrNull()?.y?.toDouble() ?: 0.0,
                            response.addresses.firstOrNull()?.x?.toDouble() ?: 0.0
                        )
                    },
                    onFailure = { error ->
                        Log.d("HomeViewModel", "updateLatLngByLocation: $error")
                    }
                )
            }
            mapAnimalData.forEach {
                Log.d("HomeViewModel", "MapAnimalData: ${it.name}, LatLng: ${it.latLng}")
            }

            _uiState.update { currentState ->
                currentState.copy(
                    animalDataList = currentState.animalDataList + homeAnimalDataList,
                    shownAnimalDataList = currentState.shownAnimalDataList + homeAnimalDataList,
                    mapAnimalDataList = currentState.mapAnimalDataList + mapAnimalData,
                    shownMapAnimalDataList = currentState.mapAnimalDataList + mapAnimalData,
                    cameraLatLng = mapAnimalData[2].latLng
                )
            }
        }
    }


    fun setExpanded(isExpanded: Boolean) {
        _uiState.value = _uiState.value.copy(
            isExpanded = isExpanded,
            selectedAnimal = null,
        )
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
            val mapAnimalDataList =
                uiState.value.mapAnimalDataList.filter { animalData ->
                    (currentState.isLostShown && animalData.type == AnimalDataType.LOST) ||
                            (currentState.isProtectShown && animalData.type == AnimalDataType.PROTECT) ||
                            (currentState.isWitnessShown && animalData.type == AnimalDataType.WITNESS)
                }
            mapAnimalDataList.forEach {
                Log.d("HomeViewModel", ": ${it.name}, LatLng: ${it.latLng}")
            }
            currentState.copy(
                shownAnimalDataList = shownList,
                shownMapAnimalDataList = mapAnimalDataList,
            )
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

class HomeVieWModelFactory(
    private val dataPortalRepository: DataPortalRepository,
    private val naverRepository: NaverRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataPortalRepository, naverRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}