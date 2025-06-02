package com.example.mnrader.addScreens

import android.app.DatePickerDialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.R
import com.example.mnrader.model.RegisterScreens
import com.example.mnrader.model.RegisterViewModel
import com.example.mnrader.navigation.RegisterTopBar
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportOrLostScreen(navController: NavController, viewModel: RegisterViewModel) {
    val isReport = viewModel.registerData.type == "report"
    val selectedAnimalType = viewModel.registerData.animalType
    var breed by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var dateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var description by remember { mutableStateOf("") }
    val customButtonColor = Color(0xFF89C5A9)

    // 동물 타입에 따라 품종 리스트 설정
    val breedOptions = when (selectedAnimalType) {
        "강아지" -> listOf("푸들", "말티즈", "진돗개")
        "고양이" -> listOf("코리안숏헤어", "러시안블루", "샴")
        "기타동물" -> listOf("햄스터", "토끼", "기타")
        else -> listOf("품종 없음")
    }
    var tempDateTime by remember { mutableStateOf(dateTime) }
    // DatePickerDialog
    val context = LocalContext.current
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                dateTime = dateTime.withYear(year).withMonth(month + 1).withDayOfMonth(day)
            },
            dateTime.year,
            dateTime.monthValue - 1,
            dateTime.dayOfMonth
        ).apply {
            setOnShowListener {
                // OK 버튼 누르면 dateTime 업데이트
                getButton(DatePickerDialog.BUTTON_POSITIVE).setOnClickListener {
                    dateTime = tempDateTime
                    dismiss()
                }
                // Cancel 버튼 누르면 원래 날짜로 복귀
                getButton(DatePickerDialog.BUTTON_NEGATIVE).setOnClickListener {
                    tempDateTime = dateTime // 기존 날짜 유지
                    dismiss()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            RegisterTopBar(
                currentStep = 4
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
                            // 데이터 저장
                            viewModel.registerData = viewModel.registerData.copy(
                                breed = breed,
                                gender = gender,
                                location = location,
                                dateTime = dateTime,
                                description = description)
                            navController.navigate(RegisterScreens.SubmitSuccess.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = customButtonColor),
                        modifier = Modifier.fillMaxWidth()
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
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                item {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Image(
                        painter = painterResource(id = R.drawable.pets),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                        )
                    }
                }
                item {
                    // 품종 (드롭다운)
                    Text("품종", modifier = Modifier.padding(top = 16.dp, start = 4.dp))
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = breed,
                            onValueChange = { breed = it },
                            label = { Text("품종 선택") },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            breedOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        breed = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
                item {
                // 성별
                Text("성별", modifier = Modifier.padding(top = 16.dp, start = 4.dp))
                OutlinedCard(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        listOf("수컷", "암컷", "모름").forEach {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                RadioButton(
                                    selected = gender == it,
                                    onClick = { gender = it }
                                )
                                Text(it)
                            }
                        }
                    }
                }
                }
                // 장소
                item {
                    Text(
                        if (isReport) "목격 장소" else "잃어버린 장소",
                        modifier = Modifier.padding(top = 16.dp, start = 4.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            placeholder = { Text("장소를 검색하세요") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // 날짜/시간
                item {
                    Text("날짜/시간", modifier = Modifier.padding(top = 16.dp, start = 4.dp))

                    OutlinedTextField(
                        value = dateTime.toLocalDate().toString(),
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = {
                                tempDateTime = dateTime
                                datePickerDialog.show()
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_calendar),
                                    contentDescription = "날짜 선택"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                }


                // 상세내용
                item {
                    Text("상세내용", modifier = Modifier.padding(top = 16.dp, start = 4.dp))
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                    }
                }
                // 사진 추가 아이콘
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        IconButton(onClick = {
                            // TODO: 사진 추가 기능 구현
                        })
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_add_photo_alternate_24),
                                contentDescription = "사진 추가",
                                modifier = Modifier.size(48.dp),
                                tint = Color.Gray
                            )
                        }
                        Text(
                            text = "사진 추가",
                            modifier = Modifier.padding(start = 8.dp),
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReportPreview() {
    val navController = rememberNavController()
    val viewModel = remember { RegisterViewModel() }

    ReportOrLostScreen(navController = navController, viewModel = viewModel)
}
