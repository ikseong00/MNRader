package com.example.mnrader.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()


    fun setExpanded(isExpanded: Boolean) {
        _uiState.value = _uiState.value.copy(isExpanded = isExpanded)
    }

    fun setLostShown(isShown: Boolean) {
        _uiState.update {
            it.copy(isLostShown = !isShown)
        }
    }

    fun setProtectShown(isShown: Boolean) {
        _uiState.update {
            it.copy(isProtectShown = !isShown)
        }
    }

    fun setWitnessShown(isShown: Boolean) {
        _uiState.update {
            it.copy(isWitnessShown = !isShown)
        }
    }


}