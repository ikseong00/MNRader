package com.example.mnrader.addComponents

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.addScreens.AnimalTypeScreen
import com.example.mnrader.addScreens.RegisterInfoScreen
import com.example.mnrader.addScreens.ReportOrLostScreen
import com.example.mnrader.addScreens.SelectTypeScreen
import com.example.mnrader.addScreens.SubmitSuccessScreen
import com.example.mnrader.model.RegisterScreens
import com.example.mnrader.model.RegisterViewModel

@Composable
fun AnimalRegister(viewModel: RegisterViewModel = RegisterViewModel()) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RegisterScreens.SelectType.route
    ) {
        composable(RegisterScreens.SelectType.route) {
            SelectTypeScreen(navController, viewModel)
        }
        composable(RegisterScreens.RegisterInfo.route) {
            RegisterInfoScreen(navController, viewModel)
        }
        composable(RegisterScreens.AnimalType.route) {
            AnimalTypeScreen(navController, viewModel)
        }
        composable(RegisterScreens.ReportOrLost.route) {
            ReportOrLostScreen(navController, viewModel)
        }
        composable(RegisterScreens.SubmitSuccess.route) {
            SubmitSuccessScreen(navController, viewModel)
        }
    }
}

@Preview
@Composable
private fun AnimalRegisterPreview() {
    MaterialTheme {
        AnimalRegister()
    }
}