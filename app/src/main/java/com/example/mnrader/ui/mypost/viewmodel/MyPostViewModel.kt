package com.example.mnrader.ui.mypost.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MyPostViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MyPostUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getMyPostList()
    }

    private fun getMyPostList() {
        _uiState.update {
            it.copy(
                myPostList = MyPostUiState.dummyList
            )
        }
    }



    fun refreshMyPostList() {
        getMyPostList()
    }
} 