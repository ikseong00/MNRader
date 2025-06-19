package com.example.mnrader.ui.setting.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mnrader.data.repository.UserAnimalRepository
import com.example.mnrader.ui.common.CommonTopBar
import com.example.mnrader.ui.common.MNRaderButton
import com.example.mnrader.ui.setting.component.AnimalTypeDropdown
import com.example.mnrader.ui.setting.component.BreedDropdown
import com.example.mnrader.ui.setting.component.FormSection
import com.example.mnrader.ui.setting.component.FormTextField
import com.example.mnrader.ui.setting.component.GenderDropdown
import com.example.mnrader.ui.setting.component.ImagePicker
import com.example.mnrader.ui.setting.viewmodel.AddMyPetViewModel
import com.example.mnrader.ui.setting.viewmodel.AddMyPetViewModelFactory

enum class AnimalType(val displayName: String) {
    DOG("강아지"),
    CAT("고양이"),
    OTHER("기타")
}

@Composable
fun AddMyPetScreen(
    onBack: () -> Unit = {},
    viewModel: AddMyPetViewModel = viewModel(
        factory = AddMyPetViewModelFactory(
            userAnimalRepository = UserAnimalRepository(),
            context = LocalContext.current
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.addSuccess) {
        if (uiState.addSuccess) {
            onBack()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CommonTopBar(
            hasBackButton = true,
            title = "보유중인 동물 추가",
            onBack = onBack,
        )
        Spacer(modifier = Modifier.height(24.dp))

        // 동물 종류
        FormSection(label = "동물 종류") {
            AnimalTypeDropdown(
                selectedAnimalType = uiState.selectedAnimalType,
                expanded = uiState.isAnimalTypeExpanded,
                onExpandedChange = { viewModel.setAnimalTypeExpanded(it) },
                onAnimalTypeSelected = { animalType ->
                    viewModel.onAnimalTypeChange(animalType)
                    viewModel.setAnimalTypeExpanded(false)
                },
                onDismissRequest = { viewModel.setAnimalTypeExpanded(false) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 품종
        FormSection(label = "품종") {
            BreedDropdown(
                selectedAnimalType = uiState.selectedAnimalType,
                selectedBreed = uiState.selectedBreed,
                expanded = uiState.isBreedExpanded,
                onExpandedChange = { viewModel.setBreedExpanded(it) },
                onBreedSelected = { breed ->
                    viewModel.onBreedChange(breed)
                    viewModel.setBreedExpanded(false)
                },
                onDismissRequest = { viewModel.setBreedExpanded(false) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 성별
        FormSection(label = "성별") {
            GenderDropdown(
                selectedGender = uiState.selectedGender,
                expanded = uiState.isGenderExpanded,
                onExpandedChange = { viewModel.setGenderExpanded(it) },
                onGenderSelected = { gender ->
                    viewModel.onGenderChange(gender)
                    viewModel.setGenderExpanded(false)
                },
                onDismissRequest = { viewModel.setGenderExpanded(false) }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 이름
        FormSection(label = "이름") {
            FormTextField(
                value = uiState.petName,
                onValueChange = { viewModel.onPetNameChange(it) },
                placeholder = "이름을 입력하세요"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 나이
        FormSection(label = "나이") {
            FormTextField(
                value = uiState.petAge,
                onValueChange = { viewModel.onPetAgeChange(it) },
                placeholder = "나이를 입력하세요",
                keyboardType = KeyboardType.Number
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 특징
        FormSection(label = "특징") {
            FormTextField(
                value = uiState.petCharacteristics,
                onValueChange = { viewModel.onPetCharacteristicsChange(it) },
                placeholder = "특징을 입력하세요",
                maxLines = 4,
                minHeight = 100
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 사진 첨부
        ImagePicker(
            selectedImageUri = uiState.selectedImageUri,
            onImageSelected = { viewModel.onImageSelected(it) }
        )

        Spacer(modifier = Modifier.height(40.dp))

        MNRaderButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(43.dp),
            text = "저장",
            cornerShape = 8.dp,
            onClick = {
                viewModel.savePet()
            },
        )
    }
}
