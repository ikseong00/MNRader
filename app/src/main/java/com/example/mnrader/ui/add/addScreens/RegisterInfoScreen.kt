package com.example.mnrader.ui.add.addScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.R
import com.example.mnrader.ui.add.model.RegisterViewModel
import com.example.mnrader.ui.add.model.RegisterScreens
import com.example.mnrader.ui.theme.Green1

@Composable
fun RegisterInfoScreen(navController: NavController, viewModel: RegisterViewModel) {

    val name = viewModel.registerData.name
    val contact = viewModel.registerData.contact

    val isFormValid = name.isNotBlank() && contact.isNotBlank()

    Scaffold(
        topBar = {
            RegisterTopBar(
                currentStep = 2
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Button(
                        onClick = {
                            navController.navigate(RegisterScreens.AnimalType.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Green1),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = isFormValid
                    ) {
                        Text("다음")
                    }
                    TextButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "되돌아가기",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        // 가운데 내용
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add_person),
                    contentDescription = "",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text("이름",modifier = Modifier.padding(top = 16.dp, start = 4.dp))
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            viewModel.registerData = viewModel.registerData.copy(name = it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("이름") }
                    )
                }
                Spacer(Modifier.height(10.dp))

                Text("연락처",modifier = Modifier.padding(top = 16.dp, start = 4.dp))
                Spacer(Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = contact,
                        onValueChange = {
                            // 숫자만 남기도록 필터링
                            val filtered = it.filter { char -> char.isDigit() }
                            viewModel.registerData = viewModel.registerData.copy(contact = filtered)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("전화번호") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterInfoScreen() {
    val navController = rememberNavController()
    val viewModel = remember { RegisterViewModel() }

    RegisterInfoScreen(navController = navController, viewModel = viewModel)
}
