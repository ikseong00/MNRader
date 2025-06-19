package com.example.mnrader.ui.mypost.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.repository.UserRepository
import com.example.mnrader.mapper.toMyPostModelList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyPostViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPostUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getMyPostList()
    }

    fun getMyPostList() {
        viewModelScope.launch {
            userRepository.getMyPostList().fold(
                onSuccess = { response ->
                    response.result?.let { myPostResponseDto ->
                        val myPostModelList = myPostResponseDto.toMyPostModelList()
                        _uiState.update { currentState ->
                            currentState.copy(myPostList = myPostModelList)
                        }
                    }
                },
                onFailure = { error ->
                    Log.d("MyPostViewModel", "getMyPostList: $error")
                }
            )
        }
    }
}

class MyPostViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyPostViewModel::class.java)) {
            return MyPostViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 