package com.example.mnrader.RegisterScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mnrader.model.RegisterScreens
import com.example.mnrader.model.RegisterViewModel

@Composable
fun SubmitSuccessScreen(navController: NavController, viewModel: RegisterViewModel) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xff04A69F), modifier = Modifier.size(80.dp))
        Text("제출 성공!", fontSize = 24.sp, modifier = Modifier.padding(16.dp))
        Button(onClick = {
            viewModel.reset()
            navController.popBackStack(RegisterScreens.SelectType.route, inclusive = false)
        }) {
            Text("완료")
        }
    }
}
