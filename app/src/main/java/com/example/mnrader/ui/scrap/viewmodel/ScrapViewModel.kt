package com.example.mnrader.ui.scrap.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mnrader.data.repository.UserRepository
import com.example.mnrader.mapper.toScrapModels
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScrapViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScrapUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getScrapList()
    }

    private fun getScrapList() {
        viewModelScope.launch {
            userRepository.getScrapList().fold(
                onSuccess = { scrapDtoList ->
                    Log.d("ScrapViewModel", "Scrap list loaded successfully: ${scrapDtoList.size} items")
                    _uiState.update {
                        it.copy(
                            scrapList = scrapDtoList.toScrapModels(),
                        )
                    }
                },
                onFailure = { exception ->
                    Log.e(
                        "ScrapViewModel",
                        "Error loading scrap list: ${exception.message}"
                    )
                })
        }
    }
}

class ScrapViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScrapViewModel::class.java)) {
            return ScrapViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}