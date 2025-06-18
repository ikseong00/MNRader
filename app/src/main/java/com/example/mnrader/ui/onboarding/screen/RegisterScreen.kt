package com.example.mnrader.ui.onboarding.screen

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.ui.common.AddressDropdown
import com.example.mnrader.ui.onboarding.viewmodel.RegisterViewModel
import com.example.mnrader.ui.onboarding.viewmodel.RegisterViewModelFactory
import com.example.mnrader.ui.theme.Green1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    navigateToLogin: () -> Unit = {},
    viewModel: RegisterViewModel = viewModel(
        factory = RegisterViewModelFactory(LocalContext.current.applicationContext as Application)
    )
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.isRegistered) {
        if (uiState.isRegistered) {
            navigateToLogin()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "회원가입",
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "back"
                        )
                    }
                }
            )

        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    "지역", fontWeight = FontWeight.Bold, fontSize = 16.sp,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                AddressDropdown(
                    selectedCity = uiState.city,
                    onCitySelected = { viewModel.updateCity(it) }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 이메일
                Text(
                    "이메일", fontWeight = FontWeight.Bold, fontSize = 16.sp,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = { viewModel.updateEmail(it) },
                    placeholder = { Text("이메일을 입력해주세요", color = Color.Gray) },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 비밀번호
                Text(
                    "비밀번호", fontWeight = FontWeight.Bold, fontSize = 16.sp,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = { viewModel.updatePassword(it) },
                    placeholder = { Text("비밀번호를 입력해주세요", color = Color.Gray) },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )


                Spacer(modifier = Modifier.height(40.dp))

                // 버튼
                Button(
                    onClick = { viewModel.register() },
                    colors = ButtonDefaults.buttonColors(containerColor = Green1),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("회원가입", fontSize = 16.sp)
                }
            }

            Text("email: 이메일 형식이어야 합니다.", fontSize = 12.sp)
            Text("password: 최소 8자리 ~ 최대 20자리까지 가능합니다. ", fontSize = 12.sp)
            Text("password: 소문자, 숫자, 특수문자가 적어도 하나씩은 있어야 합니다.", fontSize = 12.sp)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    val navController = rememberNavController()

    RegisterScreen(
        navController = navController,
    )
} 