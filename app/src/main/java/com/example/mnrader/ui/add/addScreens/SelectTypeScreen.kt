package com.example.mnrader.ui.add.addScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.ui.add.model.RegisterScreens
import com.example.mnrader.ui.add.model.RegisterViewModel
import com.example.mnrader.ui.theme.Green1

@Composable
fun SelectTypeScreen(
    navController: NavController,
    viewModel: RegisterViewModel,
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()) {
        RegisterTopBar(
            onBackClick = onBackClick,
            currentStep = 1 // 여기서 단계 조정: 1~5
        )
        Box(modifier = Modifier.fillMaxSize()
            .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("어떤 것을 하고 싶나요?",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth())
                Button(onClick = {
                    viewModel.registerData = viewModel.registerData.copy(type = "report")
                    navController.navigate(RegisterScreens.RegisterInfo.route)
                },
                    colors = ButtonDefaults.buttonColors(containerColor = Green1),
                    modifier = Modifier
                        .fillMaxWidth()) {
                    Text("유기동물 신고하기")
                }
                Spacer(Modifier.height(10.dp))
                Button(onClick = {
                    viewModel.registerData = viewModel.registerData.copy(type = "lost")
                    navController.navigate(RegisterScreens.RegisterInfo.route)
                },
                    colors = ButtonDefaults.buttonColors(containerColor = Green1),
                    modifier = Modifier
                        .fillMaxWidth()) {
                    Text("실종동물 등록하기")
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SelectTypeScreenPreview() {
    val navController = rememberNavController()
    val viewModel = remember { RegisterViewModel() }

    SelectTypeScreen(navController = navController,viewModel = viewModel, onBackClick = {})
}