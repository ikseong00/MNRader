package com.example.mnrader.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.RegisterScreens.SelectTypeScreen
import com.example.mnrader.model.RegisterViewModel
import com.example.mnrader.model.Routes
import com.example.mnrader.uicomponents.AddAnimals
import com.example.mnrader.uicomponents.Animals
import com.example.mnrader.uicomponents.Home
import com.example.mnrader.uicomponents.MyPage

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ){
        composable(Routes.Home.route){
            Home()
        }
        composable(Routes.Animals.route){
            Animals()
        }

        composable(Routes.AddAnimals.route){
            AddAnimals()
        }
        composable(Routes.MyPage.route){
            MyPage()
        }
    }
}

@Preview
@Composable
private fun AppPreview() {
    MaterialTheme {
        SelectTypeScreen(navController = rememberNavController(), viewModel = RegisterViewModel())
    }
}