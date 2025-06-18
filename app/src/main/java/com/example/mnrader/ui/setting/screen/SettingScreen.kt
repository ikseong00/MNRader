package com.example.mnrader.ui.setting.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mnrader.ui.common.AddressDropdown
import com.example.mnrader.ui.common.CommonTopBar
import com.example.mnrader.ui.common.MNRaderButton
import com.example.mnrader.ui.mypage.viewmodel.MyAnimal
import com.example.mnrader.ui.setting.component.SettingAnimalCardList
import com.example.mnrader.ui.setting.component.SettingExpandable
import com.example.mnrader.ui.setting.viewmodel.SettingViewModel
import com.example.mnrader.ui.setting.viewmodel.SettingViewModelFactory
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun SettingScreen(
    padding: PaddingValues,
    onBack: () -> Unit = {},
    navigateToMyAnimalDetail: (MyAnimal) -> Unit = {},
    navigateToAddMyAnimal: () -> Unit = {},
    viewModel: SettingViewModel = viewModel(
        factory = SettingViewModelFactory()
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CommonTopBar(
                hasBackButton = true,
                title = "설정",
                onBack = onBack,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = uiState.email,
                    style = MNRaderTheme.typography.medium.copy(
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            SettingExpandable(
                text = "보유 중인 동물",
                isExpanded = uiState.isAnimalExpanded,
                onClick = { viewModel.setAnimalExpanded() },
            ) {
                SettingAnimalCardList(
                    modifier = Modifier.padding(10.dp),
                    animalList = uiState.myAnimalList,
                    onAnimalClick = { navigateToMyAnimalDetail(it) },
                    onAddAnimalClick = { navigateToAddMyAnimal() }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            SettingExpandable(
                text = "이메일",
                isExpanded = uiState.isEmailExpanded,
                onClick = { viewModel.setEmailExpanded() },
            ) {
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = { viewModel.onEmailChange(it) },
                    label = { Text("이메일") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    singleLine = true,
                )
                Spacer(modifier = Modifier.height(8.dp))
                MNRaderButton(
                    text = "저장",
                    cornerShape = 8.dp,
                    onClick = {
                        viewModel.saveEmail()
                        viewModel.setEmailExpanded()
                    },
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            SettingExpandable(
                text = "지역",
                isExpanded = uiState.isAddressExpanded,
                onClick = { viewModel.setAddressExpanded() },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    AddressDropdown(
                        modifier = Modifier.padding(16.dp),
                        selectedCity = uiState.address,
                    ) {
                        viewModel.onAddressChange(it)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    MNRaderButton(
                        text = "저장",
                        cornerShape = 8.dp,
                        onClick = {
                            viewModel.saveCity()
                            viewModel.setAddressExpanded()
                        },
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "알림 설정",
                    style = MNRaderTheme.typography.regular.copy(
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                )
                Switch(
                    checked = uiState.isNotificationEnabled,
                    onCheckedChange = { viewModel.setNotificationEnabled(it) },
                    modifier = Modifier.padding(end = 12.dp),
                )
            }
        }
    }
}