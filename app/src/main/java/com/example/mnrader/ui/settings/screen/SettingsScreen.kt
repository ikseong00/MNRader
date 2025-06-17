package com.example.mnrader.ui.settings.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mnrader.ui.mypage.component.CommonTopBar
import com.example.mnrader.ui.mypage.viewmodel.MyPageViewModel
import com.example.mnrader.ui.setting.viewmodel.SettingViewModel
import com.example.mnrader.ui.theme.Gray
import com.example.mnrader.ui.theme.Green1

@Composable
fun SettingScreen(
    viewModel: SettingViewModel,
    myPageViewModel: MyPageViewModel,
    onNavigateToAddPet: () -> Unit,
    onBackClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val petsFromMyPage by myPageViewModel.pets.collectAsState()

    LaunchedEffect(petsFromMyPage) {
        viewModel.initWithMyPagePets("123@konkuk.ac.kr", petsFromMyPage)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        CommonTopBar(title = "설정", onBack = { onBackClick() })
        Spacer(modifier = Modifier.height(12.dp))

        Text(state.email, fontSize = 16.sp, modifier = Modifier.padding(bottom = 8.dp))

        SectionHeader(
            title = "보유중인 동물",
            expanded = state.isPetSectionExpanded,
            onToggle = { viewModel.toggleSection("pet") }
        )

        if (state.isPetSectionExpanded) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 100.dp)
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.pets) { pet ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(pet.name)
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.LightGray, shape = MaterialTheme.shapes.medium)
                            .clickable {
                                onNavigateToAddPet()
                                       },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "추가")
                    }
                }
            }
        }

        SectionHeader(
            title = "이메일",
            expanded = state.isEmailSectionExpanded,
            onToggle = { viewModel.toggleSection("email") }
        )

        if (state.isEmailSectionExpanded) {
            OutlinedTextField(
                value = state.email,
                onValueChange = { viewModel.updateEmail(it) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        SectionHeader(
            title = "지역",
            expanded = state.isRegionSectionExpanded,
            onToggle = { viewModel.toggleSection("region") }
        )

        if (state.isRegionSectionExpanded) {
            val cityList = listOf("서울시", "경기도", "부산광역시", "인천광역시")
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                cityList.forEach { city ->
                    Text(
                        text = city,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.updateCity(city) }
                            .padding(vertical = 6.dp),
                        color = if (state.selectedCity == city) Green1 else Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.saveSettings() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Green1)
        ) {
            Text("저장하기")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("알림 설정", fontSize = 16.sp)
            Switch(
                checked = state.isNotificationEnabled,
                onCheckedChange = { viewModel.toggleNotification(it) }
            )
        }

        if (state.showSuccessPopup) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    color = Green1,
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 8.dp
                ) {
                    Text(
                        text = "저장 성공!",
                        modifier = Modifier.padding(16.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, expanded: Boolean, onToggle: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Gray, shape = MaterialTheme.shapes.medium)
            .clickable { onToggle() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 16.sp, modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = null
        )
    }
}
