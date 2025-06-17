package com.example.mnrader.ui.add.addScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.R
import com.example.mnrader.ui.add.model.RegisterScreens
import com.example.mnrader.ui.add.model.RegisterViewModel
import com.example.mnrader.ui.theme.Gray
import com.example.mnrader.ui.theme.Green1

@Composable
fun AnimalTypeScreen(
    navController: NavController,
    viewModel: RegisterViewModel
) {
    var selected by remember { mutableStateOf("") }

    val animalList = listOf(
        Pair("강아지", R.drawable.dog),
        Pair("고양이", R.drawable.cat),
        Pair("기타동물", R.drawable.rabbit)
    )
    Scaffold(
        topBar = {
            RegisterTopBar(
                currentStep = 3
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
                            viewModel.registerData =
                                viewModel.registerData.copy(animalType = selected)
                            navController.navigate(RegisterScreens.ReportOrLost.route)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Green1),
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
                .padding(innerPadding)
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
               modifier =  Modifier.fillMaxSize()
            ) {
                animalList.forEach { (label, imageRes) ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(color = Gray)
                            .clickable {
                                selected = label
                            }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            RadioButton(
                                selected = selected == label,
                                onClick = { selected = label }
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = label,fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(60.dp))
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = label,
                                modifier = Modifier.size(80.dp)
                            )

                        }
                    }
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