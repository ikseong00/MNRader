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
import coil3.compose.rememberAsyncImagePainter
import com.example.mnrader.ui.mypage.component.CommonTopBar
import com.example.mnrader.ui.setting.viewmodel.SettingViewModel
import com.example.mnrader.ui.mypage.dataclass.Pet
import com.example.mnrader.ui.mypage.viewmodel.MyPageViewModel
import com.example.mnrader.ui.settings.component.DropdownSelector


@Composable
fun AddMyPetScreen(
    viewModel: SettingViewModel,
    myPageViewModel: MyPageViewModel,
    onBackClick: () -> Unit,
    onSaveComplete: () -> Unit
) {
    var species by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val breedExamples = mapOf(
        "강아지" to listOf("말티즈", "푸들", "치와와", "포메라니안"),
        "고양이" to listOf("코숏", "러시안블루", "먼치킨", "스코티시 폴드"),
        "기타" to listOf("골든 햄스터", "드워프 햄스터",
            "러시안 육지거북", "헤르만 육지거북",
            "미니 렉스", "라이언헤드")
    )
    val breedOptions = breedExamples[species] ?: emptyList()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CommonTopBar(title = "설정", onBack = onBackClick)

        Text("보유중인 동물 추가", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(12.dp))

        DropdownSelector(
            label = "동물 종류",
            options = breedExamples.keys.toList(),
            selected = species,
            onSelected = {
                species = it
                breed = "" // 종이 바뀌면 품종 초기화
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        DropdownSelector(
            label = "품종",
            options = breedOptions,
            selected = breed,
            onSelected = { breed = it }
        )
        Spacer(modifier = Modifier.height(8.dp))

        DropdownSelector(
            label = "성별",
            options = listOf("수컷", "암컷"),
            selected = gender,
            onSelected = { gender = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("이름") },
            placeholder = { Text("이름을 입력하세요") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("나이") },
            placeholder = { Text("나이를 입력하세요") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("특징") },
            placeholder = { Text("특징을 입력하세요") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("사진 첨부")

        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray)
                .clickable { launcher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Text("사진 선택", color = Color.DarkGray)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (species.isNotBlank() && breed.isNotBlank()) {
                    val newPet = Pet(
                        id = 0,
                        name = name.ifBlank { breed },
                        imageUrl = null,
                        species = species,
                        breed = breed,
                        gender = gender,
                        age = age,
                        description = description,
                        imageUri = imageUri?.toString()
                    )

                    viewModel.addPet(
                        pet = newPet,
                        imageUri = imageUri,
                        onResult = { success ->
                            if (success) {
                                myPageViewModel.addPetFromSetting(newPet)
                                onSaveComplete()
                            } else {
                                // 실패 시 처리 (optional)
                                println("동물 추가 실패")
                            }
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA5D6A7))
        ) {
            Text("저장하기")
        }


        Spacer(modifier = Modifier.height(12.dp))

        SectionHeader("이메일", false) {}
        SectionHeader("지역", false) {}
        SectionHeader("알림 설정", false) {}
    }
}

