package com.example.mnrader.ui.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.datastore.LastAnimalDataStore
import com.example.mnrader.data.repository.AnimalRepository
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
import com.example.mnrader.ui.util.NotificationPermissionUtil
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dataPortalRepository: DataPortalRepository,
    private val naverRepository: NaverRepository,
    private val homeRepository: HomeRepository,
    private val lastAnimalDataStore: LastAnimalDataStore,
    private val animalRepository: AnimalRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    // 각 API 호출의 완료 상태를 추적
    private var mnDataLoaded = false
    private var abandonedDataLoaded = false
    private var lostDataLoaded = false

    init {
        getHomeData()
    }

    private fun getHomeData() {
        // 로딩 시작 및 상태 초기화
        _uiState.update { it.copy(isLoading = true) }
        mnDataLoaded = false
        abandonedDataLoaded = false
        lostDataLoaded = false

        getDataPortalAnimalData()
        getMNAnimalData()
    }

    private fun checkLoadingComplete() {
        if (mnDataLoaded && abandonedDataLoaded && lostDataLoaded) {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun getMNAnimalData() {
        viewModelScope.launch {
            val currentState = _uiState.value
            val breedId = getBreedId(currentState.breedFilter)
            val cityCode = currentState.locationFilter?.code

            homeRepository.getHomeData(breed = breedId, city = cityCode).fold(
                onSuccess = { response ->
                    response.result?.let { homeResponseDto ->
                        val serverLastAnimal = homeResponseDto.lastAnimal.toInt()
                        val localLastAnimal = lastAnimalDataStore.getLastAnimal()
                        
                        // 서버의 lastAnimal이 로컬보다 크다면 새로운 알림이 있음
                        val hasNewNotification = serverLastAnimal > localLastAnimal
                        
                        _uiState.update {
                            it.copy(
                                lastAnimal = serverLastAnimal,
                                hasNewNotification = hasNewNotification
                            )
                        }
                        val homeAnimalDataList = homeResponseDto.toHomeAnimalList()
                        updateLatLngByLocation(homeAnimalDataList)
                    }
                },
                onFailure = { error ->
                    Log.d("HomeViewModel", "getMNAnimalData: $error")
                }
            )

            // MN 데이터 로딩 완료
            mnDataLoaded = true
            checkLoadingComplete()
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
        // 구조동물 데이터 로딩
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

            // 구조동물 데이터 로딩 완료
            abandonedDataLoaded = true
            checkLoadingComplete()
        }

        // 실종동물 데이터 로딩
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

            // 실종동물 데이터 로딩 완료
            lostDataLoaded = true
            checkLoadingComplete()
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
        viewModelScope.launch {
            val currentBookmarkState = animalData.isBookmarked
            val newBookmarkState = !currentBookmarkState
            
            // UI 상태를 먼저 업데이트 (낙관적 업데이트)
            _uiState.update { currentState ->
                val updatedList = currentState.shownAnimalDataList.map { data ->
                    if (data.id == animalData.id) {
                        data.copy(isBookmarked = newBookmarkState)
                    } else {
                        data
                    }
                }
                val updatedMapList = currentState.shownMapAnimalDataList.map { mapData ->
                    if (mapData.id == animalData.id) {
                        mapData.copy(isBookmarked = newBookmarkState)
                    } else {
                        mapData
                    }
                }
                currentState.copy(
                    shownAnimalDataList = updatedList,
                    shownMapAnimalDataList = updatedMapList
                )
            }
            
            // 동물 타입에 따른 스크랩 처리
            when (animalData.type) {
                AnimalDataType.PORTAL_LOST, AnimalDataType.PORTAL_PROTECT -> {
                    // Portal 동물들 - 로컬 DB에 저장
                    handlePortalAnimalBookmark(animalData, newBookmarkState, currentBookmarkState)
                }
                AnimalDataType.MN_LOST, AnimalDataType.MY_WITNESS -> {
                    // MN 동물들 - 서버에 요청
                    handleMNAnimalBookmark(animalData, newBookmarkState, currentBookmarkState)
                }
            }
        }
    }
    
    private suspend fun handlePortalAnimalBookmark(
        animalData: HomeAnimalData,
        newBookmarkState: Boolean,
        currentBookmarkState: Boolean
    ) {
        if (newBookmarkState) {
            // 스크랩 추가
            val scrapEntity = com.example.mnrader.data.entity.ScrapEntity(
                id = "", // DataPortalRepository에서 생성
                animalId = animalData.id.toString(),
                userEmail = "", // DataPortalRepository에서 설정
                animalType = animalData.type,
                imageUrl = animalData.imageUrl,
                name = animalData.name,
                location = animalData.location,
                date = animalData.date,
                gender = when (animalData.gender) {
                    com.example.mnrader.ui.home.model.Gender.FEMALE -> "암컷"
                    com.example.mnrader.ui.home.model.Gender.MALE -> "수컷"
                },
                city = 1, // TODO: 실제 지역 정보로 변경 필요
                isScrapped = true
            )
            
            dataPortalRepository.insertScrap(scrapEntity)
                .onFailure { exception ->
                    // 실패 시 원래 상태로 되돌리기
                    revertBookmarkState(animalData.id, currentBookmarkState)
                    Log.e("HomeViewModel", "Error inserting portal scrap: ${exception.message}")
                }
        } else {
            // 스크랩 제거
            dataPortalRepository.deleteScrapById(animalData.id.toString())
                .onFailure { exception ->
                    // 실패 시 원래 상태로 되돌리기
                    revertBookmarkState(animalData.id, currentBookmarkState)
                    Log.e("HomeViewModel", "Error deleting portal scrap: ${exception.message}")
                }
        }
    }
    
    private suspend fun handleMNAnimalBookmark(
        animalData: HomeAnimalData,
        newBookmarkState: Boolean,
        currentBookmarkState: Boolean
    ) {
        if (newBookmarkState) {
            // 스크랩 추가 - 서버 API 호출
            animalRepository.scrapAnimal(animalData.id.toInt())
                .onFailure { exception ->
                    // 실패 시 원래 상태로 되돌리기
                    revertBookmarkState(animalData.id, currentBookmarkState)
                    Log.e("HomeViewModel", "Error scrapping MN animal: ${exception.message}")
                }
        } else {
            // 스크랩 제거 - 서버 API 호출
            animalRepository.unscrapAnimal(animalData.id.toInt())
                .onFailure { exception ->
                    // 실패 시 원래 상태로 되돌리기
                    revertBookmarkState(animalData.id, currentBookmarkState)
                    Log.e("HomeViewModel", "Error unscrapping MN animal: ${exception.message}")
                }
        }
    }
    
    private fun revertBookmarkState(animalId: Long, originalState: Boolean) {
        _uiState.update { currentState ->
            val updatedList = currentState.shownAnimalDataList.map { data ->
                if (data.id == animalId) {
                    data.copy(isBookmarked = originalState)
                } else {
                    data
                }
            }
            val updatedMapList = currentState.shownMapAnimalDataList.map { mapData ->
                if (mapData.id == animalId) {
                    mapData.copy(isBookmarked = originalState)
                } else {
                    mapData
                }
            }
            currentState.copy(
                shownAnimalDataList = updatedList,
                shownMapAnimalDataList = updatedMapList
            )
        }
    }

    fun setLocation(location: City) {
        _uiState.update { currentState ->
            currentState.copy(
                locationFilter = if (location == City.ALL) null else location,
                animalDataList = emptyList(),
                shownAnimalDataList = emptyList(),
                mapAnimalDataList = emptyList(),
                shownMapAnimalDataList = emptyList(),
            )
        }
        // 새로운 필터로 데이터 다시 로드
        getHomeData()
    }

    fun setBreed(breed: String?) {
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

    // 알림 확인 후 새로운 알림 상태를 업데이트
    fun checkNewNotificationStatus() {
        val currentLastAnimal = _uiState.value.lastAnimal
        val savedLastAnimal = lastAnimalDataStore.getLastAnimal()
        
        val hasNewNotification = currentLastAnimal > savedLastAnimal
        _uiState.update {
            it.copy(hasNewNotification = hasNewNotification)
        }
    }

    // 알림 권한 확인
    fun checkNotificationPermission(context: Context) {
        val shouldShow = NotificationPermissionUtil.shouldShowPermissionDialog(context)
        _uiState.update {
            it.copy(showNotificationPermissionDialog = shouldShow)
        }
    }
    
    // 알림 권한 요청 완료 처리
    fun onNotificationPermissionRequestCompleted(context: Context) {
        NotificationPermissionUtil.setPermissionRequested(context, true)
        _uiState.update {
            it.copy(showNotificationPermissionDialog = false)
        }
    }
}

class HomeViewModelFactory(
    private val dataPortalRepository: DataPortalRepository,
    private val naverRepository: NaverRepository,
    private val homeRepository: HomeRepository,
    private val lastAnimalDataStore: LastAnimalDataStore,
    private val animalRepository: AnimalRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(dataPortalRepository, naverRepository, homeRepository, lastAnimalDataStore, animalRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}