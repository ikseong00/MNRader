package com.example.mnrader.ui.add.addScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.ui.add.model.RegisterViewModel
import com.example.mnrader.navigation.Routes

@Composable
fun SubmitSuccessScreen(
    rootNavController: NavHostController,
    viewModel: RegisterViewModel
) {
    val customButtonColor = Color(0xFF89C5A9)
    Scaffold(
        topBar = {
            RegisterTopBar(
                currentStep = 5
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
                        viewModel.reset()
                        rootNavController.navigate(Routes.MAIN) {
                            popUpTo(Routes.MAIN) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = customButtonColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("완료")
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
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xff90c5aa),
                    modifier = Modifier.size(80.dp)
                )
                Text("제출 성공!", fontSize = 24.sp, modifier = Modifier.padding(16.dp))
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SubmitPreview() {
    val navController = rememberNavController()
    val viewModel = remember { RegisterViewModel() }

    SubmitSuccessScreen(rootNavController = navController, viewModel = viewModel)
}