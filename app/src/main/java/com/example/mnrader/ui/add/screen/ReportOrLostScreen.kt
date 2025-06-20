package com.example.mnrader.ui.add.screen

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mnrader.R
import com.example.mnrader.model.CatBreed
import com.example.mnrader.model.DogBreed
import com.example.mnrader.ui.add.component.RegisterTopBar
import com.example.mnrader.ui.add.viewmodel.AddScreens
import com.example.mnrader.ui.add.viewmodel.AddViewModel
import com.example.mnrader.ui.theme.Green1
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportOrLostScreen(navController: NavController, viewModel: AddViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val breedOptions = when (uiState.animalType) {
        "강아지" -> DogBreed.entries.map { it.breedName }
        "고양이" -> CatBreed.entries.map { it.breedName }
        else -> listOf("기타")
    }

    val isReport = uiState.type == "report"
    //날짜/시간 context
    val context = LocalContext.current

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
                            // UserAnimalService로 POST 요청 보내기
                            viewModel.submitUserAnimal(
                                onSuccess = {
                                    navController.navigate(AddScreens.SubmitSuccess.route)
                                },
                                onError = { errorMessage ->
                                    // 에러 처리 (Toast 또는 Snackbar로 에러 메시지 표시 가능)
                                    Log.e("ReportOrLostScreen", "동물 등록 실패: $errorMessage")
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Green1),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading // 로딩 중일 때 버튼 비활성화
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = Color.White
                            )
                        } else {
                            Text("다음")
                        }
                    }
                    TextButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading // 로딩 중일 때 뒤로가기도 비활성화
                    ) {
                        Text(
                            text = "되돌아가기",
                            fontSize = 12.sp,
                            color = if (isLoading) Color.Gray else Color.Black
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
                            value = uiState.breed,
                            onValueChange = { },
                            label = { Text("품종 선택") },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier.fillMaxWidth().menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            breedOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        viewModel.updateBreed(option)
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
                                    selected = uiState.gender == it,
                                    onClick = { viewModel.updateGender(it) }
                                )
                                Text(it)
                            }
                        }
                    }
                }
                }
                
                // 나이
                item {
                    Text("나이", modifier = Modifier.padding(top = 16.dp, start = 4.dp))
                    OutlinedTextField(
                        value = uiState.age.toString(),
                        onValueChange = { newValue ->
                            val age = newValue.toIntOrNull() ?: 1
                            if (age in 1..30) { // 나이 범위 제한
                                viewModel.updateAge(age)
                            }
                        },
                        label = { Text("나이 (년)") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
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
                            value = uiState.location,
                            onValueChange = { viewModel.updateLocation(it) },
                            leadingIcon = {
                                Icon(
                                    painterResource(id = R.drawable.ic_add_search),
                                    contentDescription = "검색 아이콘"
                                )
                            },
                            placeholder = { Text("장소를 검색하세요") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // 날짜/시간
                item {
                    Text("날짜", modifier = Modifier.padding(top = 16.dp, start = 4.dp))

                    OutlinedTextField(
                        value = uiState.dateTime.toLocalDate().toString(),
                        onValueChange = {},
                        readOnly = true,
                        leadingIcon = {
                            IconButton(onClick = {
                                showDatePickerDialog(context, uiState.dateTime) { selectedDate ->
                                    viewModel.updateDateTime(selectedDate)
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_add_calendar),
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
                            value = uiState.description,
                            onValueChange = { viewModel.updateDescription(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            placeholder = { Text("상세 내용을 기술해 주세요") }
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                // 사진 추가 아이콘
                item {
                    val photoPickerLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent()
                    ) { uri: Uri? ->
                        uri?.let {
                            Log.d("PhotoPicker", "사진 선택됨: $it")
                            viewModel.updateImageUri(it)
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            onClick = {
                                photoPickerLauncher.launch("image/*")
                            },
                            shape = RoundedCornerShape(3.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "사진 추가",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_photo),
                                contentDescription = "사진 추가",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
// datePickerDialog 생성 함수
fun showDatePickerDialog(context: Context, currentDateTime: LocalDateTime, onDateSelected: (LocalDateTime) -> Unit) {
    DatePickerDialog(
        context,
        { _, year, month, day ->
            val selectedDate = LocalDateTime.of(year, month + 1, day, currentDateTime.hour, currentDateTime.minute)
            onDateSelected(selectedDate)
        },
        currentDateTime.year,
        currentDateTime.monthValue - 1,
        currentDateTime.dayOfMonth
    ).apply {
        datePicker.maxDate = System.currentTimeMillis()
    }.show()
}

// Preview는 AddViewModel의 의존성 때문에 제거
