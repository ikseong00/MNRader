package com.example.mnrader.ui.onboarding.viewmodel

import com.example.mnrader.model.City

data class RegisterUiState(
    val city: City? = null,
    val email: String = "",
    val password: String = "",
    val isRegistered: Boolean = false,
    val errorMessage: String? = null
)