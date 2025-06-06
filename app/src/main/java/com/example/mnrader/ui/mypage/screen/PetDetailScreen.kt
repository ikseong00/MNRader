package com.example.mnrader.ui.mypage.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mnrader.ui.mypage.component.CommonTopBar
import com.example.mnrader.ui.mypage.component.DropdownSelector
import com.example.mnrader.ui.mypage.dataclass.Pet
import com.example.mnrader.ui.mypage.viewmodel.PetUploadViewModel
import com.example.mnrader.ui.theme.Green1

@Composable
fun PetDetailScreen(
    pet: Pet,
    viewModel: PetUploadViewModel,
    onBackClick: () -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var species by remember { mutableStateOf(pet.species) }
    var breedQuery by remember { mutableStateOf(TextFieldValue(pet.breed)) }
    var gender by remember { mutableStateOf(pet.gender) }
    var age by remember { mutableStateOf(pet.age) }
    var description by remember { mutableStateOf(pet.description) }
    val scrollState = rememberScrollState()

    val breedExamples = mapOf(
        "강아지" to listOf("말티즈", "치와와", "푸들", "포메라니안", "요크셔 테리어"),
        "고양이" to listOf("코리안 숏헤어", "러시안 블루", "먼치킨", "스코티시 폴드"),
        "햄스터" to listOf("골든 햄스터", "드워프 햄스터"),
        "거북이" to listOf("러시안 육지거북", "헤르만 육지거북"),
        "토끼" to listOf("미니 렉스", "라이언헤드")
    )

    val filteredBreeds = breedExamples[species]?.filter {
        it.contains(breedQuery.text, ignoreCase = true)
    } ?: emptyList()

    val isBreedValid by remember(breedQuery.text, species) {
        mutableStateOf(
            breedExamples[species]?.contains(breedQuery.text) == true
        )
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        CommonTopBar(title = "동물 상세", onBack = { onBackClick() })

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0)),
                    contentAlignment = Alignment.Center
                ) {
                    val imagePainter = rememberAsyncImagePainter(
                        selectedImageUri ?: pet.imageUri ?: pet.imageUrl
                    )
                    Image(
                        painter = imagePainter,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(pet.name, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("동물 정보 바꾸기", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))

        DropdownSelector(
            label = "동물 종류",
            options = breedExamples.keys.toList(),
            selected = species,
            onSelected = {
                species = it
                breedQuery = TextFieldValue("")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = breedQuery,
            onValueChange = { breedQuery = it },
            label = { Text("품종", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth()
        )

        if (breedQuery.text.isNotBlank()) {
            filteredBreeds.forEach { breed ->
                Text(
                    text = breed,
                    modifier = Modifier
                        .clickable { breedQuery = TextFieldValue(breed) }
                        .padding(vertical = 4.dp),
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        DropdownSelector(
            label = "성별",
            options = listOf("수컷", "암컷"),
            selected = gender,
            onSelected = { gender = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("나이", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description ?:"",
            onValueChange = { description = it },
            label = { Text("특징", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text("사진 수정하기")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable { galleryLauncher.launch("image/*") }
                .border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            val previewPainter = rememberAsyncImagePainter(
                selectedImageUri ?: pet.imageUri ?: pet.imageUrl
            )
            Image(painter = previewPainter, contentDescription = null, modifier = Modifier.size(48.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isBreedValid) Green1 else Color.LightGray
            ),
            onClick = {
                viewModel.updatePet(
                    animalId = pet.id,
                    name = pet.name,
                    species = species,
                    breed = breedQuery.text,
                    gender = gender,
                    age = age,
                    description = description,
                    imageUri = selectedImageUri
                ) { success ->
                    if (success) {
                        onBackClick()
                    }
                }
            },
            enabled = isBreedValid,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("저장")
        }


        Spacer(modifier = Modifier.height(24.dp))
    }
}
