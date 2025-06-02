package com.example.mnrader.ui.settings

import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.mnrader.navigation.Routes
import com.example.mnrader.ui.mypage.component.CommonTopBar
import com.example.mnrader.ui.mypage.dataclass.Pet
import com.example.mnrader.ui.setting.viewmodel.SettingViewModel

@Composable
fun SettingScreen(
    navController: NavHostController,
    viewModel: SettingViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initDummyData("123@konkuk.ac.kr")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            CommonTopBar(title = "설정", onBack = { navController.popBackStack() })

            Spacer(modifier = Modifier.height(8.dp))

            Text(state.email, fontSize = 16.sp, modifier = Modifier.padding(8.dp))

            // 보유중인 동물 Section
            SectionHeader("보유중인 동물", expanded = state.isPetSectionExpanded) {
                viewModel.toggleSection("pet")
            }

            if (state.isPetSectionExpanded) {
                LazyRow {
                    items(state.pets) { pet ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(end = 12.dp)) {
                            Surface(
                                modifier = Modifier.size(64.dp).clip(MaterialTheme.shapes.medium),
                                color = Color.LightGray
                            ) {
                                if (pet.imageUri != null) {
                                    Icon(Icons.Default.Add, contentDescription = null) // 이미지 없으면 아이콘 대체
                                } else {
                                    Icon(Icons.Default.Add, contentDescription = null)
                                }
                            }
                            Text(pet.species, fontSize = 12.sp)
                        }
                    }
                    item {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            IconButton(onClick = {
                                //navController.navigate(Routes.ADD_MY_PET)
                            }) {
                                Surface(
                                    modifier = Modifier.size(64.dp).clip(MaterialTheme.shapes.medium),
                                    color = Color(0xFFE0E0E0)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "추가", modifier = Modifier.align(Alignment.CenterHorizontally))
                                }
                            }
                            Text("추가", fontSize = 12.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 이메일 Section
            SectionHeader("이메일", expanded = state.isEmailSectionExpanded) {
                viewModel.toggleSection("email")
            }
            if (state.isEmailSectionExpanded) {
                var emailText by remember { mutableStateOf(TextFieldValue(state.email)) }
                OutlinedTextField(
                    value = emailText,
                    onValueChange = {
                        emailText = it
                        viewModel.updateEmail(it.text)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("이메일 입력") }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 지역 Section
            SectionHeader("지역", expanded = state.isRegionSectionExpanded) {
                viewModel.toggleSection("region")
            }
            if (state.isRegionSectionExpanded) {
                val cities = listOf("서울", "경기", "인천", "부산", "대구")
                var expanded by remember { mutableStateOf(false) }
                Box {
                    TextButton(onClick = { expanded = true }) {
                        Text(state.selectedCity.ifEmpty { "지역 선택" })
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        cities.forEach { city ->
                            DropdownMenuItem(text = { Text(city) }, onClick = {
                                viewModel.updateCity(city)
                                expanded = false
                            })
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // 저장 버튼
            Button(
                onClick = { viewModel.saveSettings() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA5D6A7))
            ) {
                Text("저장하기")
            }

            // 알림 설정 스위치
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("알림 설정", fontSize = 16.sp)
                Switch(
                    checked = state.isNotificationEnabled,
                    onCheckedChange = { viewModel.toggleNotification(it) }
                )
            }
        }

        // 저장 성공 팝업
        if (state.showSuccessPopup) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 180.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Surface(
                    color = Color(0xFF81C784),
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 8.dp
                ) {
                    Text(
                        text = "저장 성공!",
                        modifier = Modifier.padding(16.dp),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, expanded: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (expanded) Icons.Default.Add else Icons.Default.Add,
            contentDescription = null
        )
    }
}
