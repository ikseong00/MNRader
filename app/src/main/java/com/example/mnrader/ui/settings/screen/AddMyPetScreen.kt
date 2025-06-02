package com.example.mnrader.ui.settings.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mnrader.ui.setting.viewmodel.SettingViewModel
import com.example.mnrader.ui.mypage.dataclass.Pet
import java.util.*

@Composable
fun AddMyPetScreen(
    navController: NavHostController,
    viewModel: SettingViewModel
) {
    var species by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("동물 추가", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray)
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text("사진 선택", color = Color.DarkGray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = species, onValueChange = { species = it }, label = { Text("종") })
        OutlinedTextField(value = breed, onValueChange = { breed = it }, label = { Text("품종") })
        OutlinedTextField(value = gender, onValueChange = { gender = it }, label = { Text("성별") })
        OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("나이") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("설명") })

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (species.isNotBlank() && breed.isNotBlank()) {
                    val newPet = Pet(
                        id = UUID.randomUUID().toString(),
                        name = breed,
                        imageUrl = "",
                        species = species,
                        breed = breed,
                        gender = gender,
                        age = age,
                        description = description,
                        imageUri = imageUri?.toString()
                    )
                    viewModel.addPet(newPet)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("저장하기", fontSize = 16.sp)
        }
    }
}
