package com.example.mnrader.ui.scrap.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScrapViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ScrapUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getScrapList()
    }

    private fun getScrapList() {
        _uiState.update {
            it.copy(
                scrapList = ScrapUiState.dummyList
            )
        }
    }

    fun refreshScrapList() {
        getScrapList()
    }
} 