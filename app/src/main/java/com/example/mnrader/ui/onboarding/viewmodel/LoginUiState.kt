package com.example.mnrader.ui.onboarding.viewmodel

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) 