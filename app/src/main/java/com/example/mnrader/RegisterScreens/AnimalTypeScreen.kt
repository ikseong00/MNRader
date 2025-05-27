package com.example.mnrader.RegisterScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
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
import com.example.mnrader.model.RegisterScreens
import com.example.mnrader.model.RegisterViewModel
import com.example.mnrader.navigation.RegisterTopBar

@Composable
fun AnimalTypeScreen(navController: NavController, viewModel: RegisterViewModel) {
    var selected by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        RegisterTopBar(
            onBackClick = { navController.popBackStack() },
            currentStep = 3 // 여기서 단계 조정: 1~5
        )
        Box(modifier = Modifier.fillMaxSize()
            .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center) {
            Column(Modifier.fillMaxSize().padding(16.dp)) {
                listOf("강아지", "고양이", "기타동물").forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(selected = selected == it, onClick = { selected = it })
                        Text(it)
                    }
                }

                Button(onClick = {
                    viewModel.registerData = viewModel.registerData.copy(animalType = selected)
                    navController.navigate(RegisterScreens.ReportOrLost.route)
                }, modifier = Modifier.fillMaxWidth().padding(top = 24.dp)) {
                    Text("다음")
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun AnimalTypePreview() {
    val navController = rememberNavController()
    val viewModel = remember { RegisterViewModel() }

    AnimalTypeScreen(navController = navController, viewModel = viewModel)
}