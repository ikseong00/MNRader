package com.example.mnrader.ui.add.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.ui.add.component.RegisterTopBar
import com.example.mnrader.ui.add.viewmodel.AddViewModel
import com.example.mnrader.navigation.Routes
import com.example.mnrader.ui.theme.Green1

@Composable
fun SubmitSuccessScreen(
    rootNavController: NavHostController,
    viewModel: AddViewModel
) {
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
                            popUpTo(0) { inclusive = true } // 전체 스택 제거
                            launchSingleTop = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Green1),
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
                    tint = Green1,
                    modifier = Modifier.size(80.dp)
                )
                Text("제출 성공!", fontSize = 24.sp, modifier = Modifier.padding(16.dp))
            }
        }
    }
}
// Preview는 AddViewModel의 의존성 때문에 제거