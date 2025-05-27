package com.example.mnrader.RegisterScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.model.RegisterViewModel
import com.example.mnrader.model.RegisterScreens
import com.example.mnrader.navigation.RegisterTopBar

@Composable
fun RegisterInfoScreen(navController: NavController, viewModel: RegisterViewModel) {
    var name by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            RegisterTopBar(
                onBackClick = { navController.popBackStack() },
                currentStep = 1
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        navController.navigate(RegisterScreens.AnimalType.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("다음")
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
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text("이름")
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = viewModel.registerData.name,
                    onValueChange = {
                        viewModel.registerData = viewModel.registerData.copy(name = it)
                    },
                    label = { Text("이름") }
                )

                Spacer(Modifier.height(10.dp))

                Text("연락처")
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value = viewModel.registerData.contact,
                    onValueChange = {
                        viewModel.registerData = viewModel.registerData.copy(contact = it)
                    },
                    label = { Text("전화번호") }
                )
            }
        }
    }

//    Column(Modifier.fillMaxSize().padding(16.dp)) {
//        RegisterTopBar(
//            onBackClick = { navController.popBackStack() },
//            currentStep = 2 // 여기서 단계 조정: 1~5
//        )
//        Box(modifier = Modifier.fillMaxSize()
//            .padding(horizontal = 10.dp),
//            contentAlignment = Alignment.Center) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("등록인") })
//                OutlinedTextField(value = contact, onValueChange = { contact = it }, label = { Text("연락처") })
//                Button(onClick = {
//                    viewModel.registerData = viewModel.registerData.copy(name = name, contact = contact)
//                    navController.navigate(RegisterScreens.AnimalType.route)
//                }, modifier = Modifier.fillMaxWidth().padding(top = 24.dp)) {
//                    Text("다음")
//                }
//            }
//
//        }
//
//    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterInfoScreen() {
    val navController = rememberNavController()
    val viewModel = remember { RegisterViewModel() }

    RegisterInfoScreen(navController = navController, viewModel = viewModel)
}
