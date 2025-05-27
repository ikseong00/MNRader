package com.example.mnrader.RegisterScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mnrader.model.RegisterScreens
import com.example.mnrader.model.RegisterViewModel
import java.time.LocalDateTime

@Composable
fun ReportOrLostScreen(navController: NavController, viewModel: RegisterViewModel) {
    val isReport = viewModel.registerData.type == "report"
    var breed by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var detailAddress by remember { mutableStateOf("") }
    var dateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var description by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        // 품종
        OutlinedTextField(value = breed, onValueChange = { breed = it }, label = { Text("품종") })
        // 성별
        Row {
            listOf("수컷", "암컷", "모름").forEach {
                Row {
                    RadioButton(selected = gender == it, onClick = { gender = it })
                    Text(it)
                }
            }
        }
        // 장소
        OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text(if (isReport) "목격 장소" else "잃어버린 장소") })
        OutlinedTextField(value = detailAddress, onValueChange = { detailAddress = it }, label = { Text("상세주소") })

        // 날짜 및 시간 선택
        Text("날짜/시간: ${dateTime.toString()}")
        // 상세 내용
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("상세내용") }, modifier = Modifier.height(100.dp))

        Button(onClick = {
            viewModel.registerData = viewModel.registerData.copy(
                breed = breed, gender = gender, location = location,
                detailAddress = detailAddress, dateTime = dateTime, description = description
            )
            navController.navigate(RegisterScreens.SubmitSuccess.route)
        }, modifier = Modifier.fillMaxWidth().padding(top = 24.dp)) {
            Text("다음")
        }
    }
}
