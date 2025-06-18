package com.example.mnrader.ui.setting.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mnrader.model.CatBreed
import com.example.mnrader.model.DogBreed
import com.example.mnrader.ui.home.model.Gender
import com.example.mnrader.ui.setting.screen.AnimalType
import com.example.mnrader.ui.theme.Green2

@Composable
fun FormSection(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormDropdown(
    value: String,
    placeholder: String = "",
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            placeholder = if (placeholder.isNotEmpty()) {
                { Text(placeholder) }
            } else null,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Green2,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest
        ) {
            content()
        }
    }
}

@Composable
fun AnimalTypeDropdown(
    selectedAnimalType: AnimalType?,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onAnimalTypeSelected: (AnimalType) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    FormDropdown(
        value = selectedAnimalType?.displayName ?: "",
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        AnimalType.entries.forEach { animalType ->
            DropdownMenuItem(
                text = { Text(animalType.displayName) },
                onClick = {
                    onAnimalTypeSelected(animalType)
                }
            )
        }
    }
}

@Composable
fun BreedDropdown(
    selectedAnimalType: AnimalType?,
    selectedBreed: String?,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onBreedSelected: (String) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    FormDropdown(
        value = selectedBreed ?: "",
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        when (selectedAnimalType) {
            AnimalType.DOG -> {
                DogBreed.entries.forEach { breed ->
                    DropdownMenuItem(
                        text = { Text(breed.breedName) },
                        onClick = {
                            onBreedSelected(breed.breedName)
                        }
                    )
                }
            }
            AnimalType.CAT -> {
                CatBreed.entries.forEach { breed ->
                    DropdownMenuItem(
                        text = { Text(breed.breedName) },
                        onClick = {
                            onBreedSelected(breed.breedName)
                        }
                    )
                }
            }
            AnimalType.OTHER -> {
                DropdownMenuItem(
                    text = { Text("기타") },
                    onClick = {
                        onBreedSelected("기타")
                    }
                )
            }
            null -> {
                // 동물 종류가 선택되지 않은 경우
            }
        }
    }
}

@Composable
fun GenderDropdown(
    selectedGender: Gender?,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onGenderSelected: (Gender) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    FormDropdown(
        value = selectedGender?.value ?: "",
        placeholder = "수컷",
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        Gender.entries.forEach { gender ->
            DropdownMenuItem(
                text = { Text(gender.value) },
                onClick = {
                    onGenderSelected(gender)
                }
            )
        }
    }
}

@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    minHeight: Int? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.Gray) },
        modifier = modifier
            .fillMaxWidth()
            .let { if (minHeight != null) it.height(minHeight.dp) else it },
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Green2,
            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f)
        )
    )
}

@Composable
fun ImagePicker(
    selectedImageUri: Uri?,
    onImageSelected: (Uri?) -> Unit,
    modifier: Modifier = Modifier
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        onImageSelected(uri)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "사진 첨부",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )

        Box(
            modifier = Modifier
                .size(48.dp)
                .border(
                    BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f)),
                    RoundedCornerShape(8.dp)
                )
                .clickable {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "사진 추가",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
} 