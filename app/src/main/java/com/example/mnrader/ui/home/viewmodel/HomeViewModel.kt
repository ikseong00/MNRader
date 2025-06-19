package com.example.mnrader.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.repository.DataPortalRepository
import com.example.mnrader.data.repository.HomeRepository
import com.example.mnrader.data.repository.NaverRepository
import com.example.mnrader.mapper.toHomeAnimalList
import com.example.mnrader.mapper.toUiModel
import com.example.mnrader.model.CatBreed
import com.example.mnrader.model.City
import com.example.mnrader.model.DogBreed
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
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getHomeData()
    }

    private fun getHomeData() {
        getDataPortalAnimalData()
        getMNAnimalData()
    }

    private fun getMNAnimalData() {
        viewModelScope.launch {
            val currentState = _uiState.value
            val breedId = getBreedId(currentState.breedFilter)
            val cityCode = currentState.locationFilter?.code
            
            homeRepository.getHomeData(breed = breedId, city = cityCode).fold(
                onSuccess = { response ->
                    response.result?.let { homeResponseDto ->
                        val homeAnimalDataList = homeResponseDto.toHomeAnimalList()
                        updateLatLngByLocation(homeAnimalDataList)
                    }
                },
                onFailure = { error ->
                    Log.d("HomeViewModel", "getMNAnimalData: $error")
                }
            )
        }
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
            val currentState = _uiState.value
            val kindCd = getKindCd(currentState.breedFilter)
            val orgCd = currentState.locationFilter?.orgCd
            
            dataPortalRepository.fetchAbandonedAnimals(uprCd = orgCd, kind = kindCd).fold(
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
            val currentState = _uiState.value
            val kindCd = getKindCd(currentState.breedFilter)
            val orgCd = currentState.locationFilter?.orgCd
            
            dataPortalRepository.fetchLostAnimals(uprCd = orgCd, kind = kindCd).fold(
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
                    cameraLatLng = mapAnimalData[0].latLng
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
                (currentState.isLostShown && animalData.type == AnimalDataType.PORTAL_LOST) ||
                        (currentState.isProtectShown && animalData.type == AnimalDataType.PORTAL_PROTECT) ||
                        (currentState.isWitnessShown && animalData.type == AnimalDataType.MY_WITNESS)
            }
            val mapAnimalDataList =
                uiState.value.mapAnimalDataList.filter { animalData ->
                    (currentState.isLostShown && animalData.type == AnimalDataType.PORTAL_LOST) ||
                            (currentState.isProtectShown && animalData.type == AnimalDataType.PORTAL_PROTECT) ||
                            (currentState.isWitnessShown && animalData.type == AnimalDataType.MY_WITNESS)
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

    fun setLocation(location: City) {
        _uiState.update { currentState ->
            currentState.copy(
                locationFilter = location,
                animalDataList = emptyList(),
                shownAnimalDataList = emptyList(),
                mapAnimalDataList = emptyList(),
                shownMapAnimalDataList = emptyList(),
            )
        }
        // 새로운 필터로 데이터 다시 로드
        getHomeData()
    }

    fun setBreed(breed: String) {
        _uiState.update { currentState ->
            currentState.copy(
                breedFilter = breed,
                animalDataList = emptyList(),
                shownAnimalDataList = emptyList(),
                mapAnimalDataList = emptyList(),
                shownMapAnimalDataList = emptyList(),
            )
        }
        // 새로운 필터로 데이터 다시 로드
        getHomeData()
    }
    
    private fun getBreedId(breedName: String?): Int? {
        if (breedName.isNullOrEmpty()) return null
        
        // 개 품종에서 검색
        DogBreed.entries.find { it.breedName == breedName }?.let {
            return it.id
        }
        
        // 고양이 품종에서 검색
        CatBreed.entries.find { it.breedName == breedName }?.let {
            return it.id
        }
        
        return null
    }
    
    private fun getKindCd(breedName: String?): String? {
        if (breedName.isNullOrEmpty()) return null
        
        // 개 품종에서 검색
        DogBreed.entries.find { it.breedName == breedName }?.let {
            return it.kindCd
        }
        
        // 고양이 품종에서 검색
        CatBreed.entries.find { it.breedName == breedName }?.let {
            return it.kindCd
        }
        
        return null
    }
}

class HomeViewModelFactory(
    private val dataPortalRepository: DataPortalRepository,
    private val naverRepository: NaverRepository,
    private val homeRepository: HomeRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataPortalRepository, naverRepository, homeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}