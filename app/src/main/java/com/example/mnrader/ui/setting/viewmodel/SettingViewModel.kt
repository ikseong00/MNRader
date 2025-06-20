package com.example.mnrader.ui.setting.viewmodel

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.manager.FCMTokenManager
import com.example.mnrader.data.repository.UserRepository
import com.example.mnrader.mapper.toMyAnimals
import com.example.mnrader.model.City
import com.example.mnrader.model.City.Companion.fromCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingViewModel(
    private val userRepository: UserRepository,
    private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingUiState())
    val uiState: StateFlow<SettingUiState> = _uiState.asStateFlow()
    
    private val sharedPrefs = context.getSharedPreferences("setting_prefs", Context.MODE_PRIVATE)
    private val KEY_NOTIFICATION_ENABLED = "notification_enabled"

    fun loadMyPageData() {
        viewModelScope.launch {
            userRepository.getMyPage()
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        email = response.result!!.email,
                        address = fromCode(response.result.city),
                        myAnimalList = response.result.toMyAnimals(),
                        isNotificationEnabled = getNotificationSetting()
                    )
                }
                .onFailure { exception ->
                    // TODO: 에러 처리
                    // 실패해도 저장된 알림 설정은 복원
                    _uiState.update {
                        it.copy(isNotificationEnabled = getNotificationSetting())
                    }
                }
        }
    }
    
    private fun getNotificationSetting(): Boolean {
        val enabled = sharedPrefs.getBoolean(KEY_NOTIFICATION_ENABLED, false)
        Log.d("SettingViewModel", "SharedPreferences에서 알림 설정 불러오기: $enabled")
        return enabled
    }
    
    private fun saveNotificationSetting(enabled: Boolean) {
        sharedPrefs.edit { putBoolean(KEY_NOTIFICATION_ENABLED, enabled) }
        Log.d("SettingViewModel", "SharedPreferences에 알림 설정 저장: $enabled")
    }

    fun onEmailChange(newEmail: String) {
        _uiState.update {
            it.copy(email = newEmail)
        }
    }

    fun setAnimalExpanded() {
        _uiState.update {
            it.copy(isAnimalExpanded = !it.isAnimalExpanded)
        }
    }

    fun setEmailExpanded() {
        _uiState.update {
            it.copy(isEmailExpanded = !it.isEmailExpanded)
        }
    }

    fun setAddressExpanded() {
        _uiState.update {
            it.copy(isAddressExpanded = !it.isAddressExpanded)
        }
    }

    fun onAddressChange(newAddress: City) {
        _uiState.update {
            it.copy(address = newAddress)
        }
    }

    fun setNotificationEnabled(isEnabled: Boolean) {
        Log.d("SettingViewModel", "알림 설정 변경 요청: $isEnabled")
        
        // 즉시 UI 상태 업데이트
        _uiState.update {
            it.copy(isNotificationEnabled = isEnabled)
        }
        
        // 일단 로컬에 저장 (서버 실패 시 복구를 위해 임시)
        val previousSetting = getNotificationSetting()
        saveNotificationSetting(isEnabled)
        
        // 알림 설정을 서버로 전송
        viewModelScope.launch {
            try {
                val fcmToken = FCMTokenManager.getFCMTokenAsync()
                Log.d("SettingViewModel", "FCM 토큰 획득 성공: ${fcmToken.take(20)}...")
                
                userRepository.updateAlarm(isEnabled, fcmToken).fold(
                    onSuccess = {
                        Log.d("SettingViewModel", "서버 알림 설정 저장 성공: enabled=$isEnabled")
                        // 서버 성공 시 SharedPreferences 저장 확정
                        saveNotificationSetting(isEnabled)
                    },
                    onFailure = { error ->
                        Log.e("SettingViewModel", "서버 알림 설정 저장 실패: $error")
                        // 실패 시 이전 설정으로 복구
                        saveNotificationSetting(previousSetting)
                        _uiState.update {
                            it.copy(isNotificationEnabled = previousSetting)
                        }
                    }
                )
            } catch (e: Exception) {
                Log.e("SettingViewModel", "FCM 토큰 가져오기 실패: $e")
                // 실패 시 이전 설정으로 복구
                saveNotificationSetting(previousSetting)
                _uiState.update {
                    it.copy(isNotificationEnabled = previousSetting)
                }
            }
        }
    }

    fun saveEmail() {
        viewModelScope.launch {
            userRepository.updateEmail(_uiState.value.email).fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(email = _uiState.value.email)
                    }
                },
                onFailure = { error ->
                    Log.e("SettingViewModel", "Error updating email: $error")
                }
            )
        }
    }

    fun saveCity() {
        viewModelScope.launch {
            val address = _uiState.value.address
            userRepository.updateCity(address.code).fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(address = address)
                    }
                },
                onFailure = { error ->
                    Log.e("SettingViewModel", "Error updating address: $error")
                }
            )
        }
    }

}

class SettingViewModelFactory(
    private val userRepository: UserRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(userRepository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}