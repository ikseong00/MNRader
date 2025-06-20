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
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            userRepository.getMyPostList().fold(
                onSuccess = { response ->
                    Log.d("MyPostViewModel", "API Response: $response")
                    response.result?.let { myPostResponseDto ->
                        Log.d("MyPostViewModel", "PostAnimal list size: ${myPostResponseDto.postAnimal?.size}")
                        
                        // 각 항목의 상세 정보 로그 출력
                        myPostResponseDto.postAnimal?.forEachIndexed { index, animal ->
                            Log.d("MyPostViewModel", "Animal[$index]: id=${animal.animalId}, name='${animal.name}', status='${animal.status}', img='${animal.img}', gender='${animal.gender}', city=${animal.city}, occurredAt='${animal.occurredAt}'")
                        }
                        
                        val myPostModelList = myPostResponseDto.toMyPostModelList()
                        Log.d("MyPostViewModel", "Mapped MyPost list size: ${myPostModelList.size}")
                        
                        // 매핑된 결과도 로그 출력
                        myPostModelList.forEachIndexed { index, model ->
                            Log.d("MyPostViewModel", "MappedModel[$index]: id=${model.id}, name='${model.name}', address='${model.address}', type=${model.type}")
                        }
                        
                        _uiState.update { currentState ->
                            currentState.copy(
                                myPostList = myPostModelList,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    } ?: run {
                        Log.w("MyPostViewModel", "Response result is null")
                        _uiState.update { 
                            it.copy(
                                isLoading = false,
                                errorMessage = "게시물 데이터를 불러올 수 없습니다."
                            )
                        }
                    }
                },
                onFailure = { error ->
                    Log.e("MyPostViewModel", "getMyPostList error: ${error.message}", error)
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "게시물을 불러오는데 실패했습니다."
                        )
                    }
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