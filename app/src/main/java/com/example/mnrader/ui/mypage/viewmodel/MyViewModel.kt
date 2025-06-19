package com.example.mnrader.ui.mypage.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.repository.UserRepository
import com.example.mnrader.mapper.toMyAnimals
import com.example.mnrader.model.City.Companion.fromCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyUiState())
    val uiState: StateFlow<MyUiState> = _uiState.asStateFlow()

    fun loadMyPageData() {
        viewModelScope.launch {
            userRepository.getMyPage()
                .onSuccess { response ->
                    _uiState.value = _uiState.value.copy(
                        email = response.result!!.email,
                        address = fromCode(response.result.city)!!.name,
                        myAnimalList = response.result.toMyAnimals()
                    )
                }
                .onFailure { exception ->
                    // TODO: 에러 처리
                }
        }
    }

    fun updateEmail(newEmail: String) {
        viewModelScope.launch {
            userRepository.updateEmail(newEmail)
                .onSuccess { response ->
                    // 이메일 업데이트 성공 시 UI 상태 업데이트
                    _uiState.value = _uiState.value.copy(
                        email = newEmail
                    )
                    // TODO: 성공 메시지 표시
                }
                .onFailure { exception ->
                    // TODO: 에러 처리
                }
        }
    }

}

class MyViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}