package com.example.mnrader.ui.setting.viewmodel

import android.net.Uri
import com.example.mnrader.ui.home.model.Gender
import com.example.mnrader.ui.setting.screen.AnimalType

data class AddMyPetUiState(
    val selectedAnimalType: AnimalType? = null,
    val selectedBreed: String? = null,
    val selectedGender: Gender? = null,
    val petName: String = "",
    val petAge: String = "",
    val petCharacteristics: String = "",
    val selectedImageUri: Uri? = null,
    val isAnimalTypeExpanded: Boolean = false,
    val isBreedExpanded: Boolean = false,
    val isGenderExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val addSuccess: Boolean = false,
    val errorMessage: String? = null
) 