package com.example.mnrader.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
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
import com.example.mnrader.ui.home.screen.HomeScreen
import com.example.mnrader.ui.onboarding.screen.OnboardingScreen

@Composable
fun MainNavHost(
    navController: NavHostController,
    padding: PaddingValues,
) {

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN,
        modifier = Modifier.padding(padding),
    ) {
        // 온보딩
        composable(
            route = Routes.ONBOARDING
        ) {
            OnboardingScreen(
                padding = padding,
                navigateToLogin = { navController.navigate(Routes.LOGIN) },
                navigateToSignUp = { navController.navigate(Routes.REGISTER) }
            )
        }

        // 회원가입
        composable(
            route = Routes.REGISTER
        ) { }

        // 로그인
        composable(
            route = Routes.LOGIN
        ) { }

        // 메인화면
        composable(
            route = Routes.MAIN
        ) {
            HomeScreen(
                padding = padding,
                navigateToAnimalDetail = { animalId ->
                    navController.navigate("animal_detail/{animalId}")
                }
            )
        }

        // 애니멀페이지
        composable(
            route = Routes.ANIMAL_DETAIL
        ) { navBackStackEntry ->

            // 애니멀 ID를 가져오기
            val animalId = navBackStackEntry.arguments?.getString("animalId")?.toIntOrNull()
            // AnimalDetailScreen(id = animalId)
        }

        // 알림
        composable(
            route = Routes.NOTIFICATION
        ) { }

        // 등록하기
        composable(
            route = Routes.ADD
        ) {
            AnimalRegister(navController)
        }

        // 마이페이지
        composable(
            route = Routes.MYPAGE
        ) { }

        // 설정
        composable(
            route = Routes.SETTING
        ) { }

    }
}

object Routes {
    const val ONBOARDING = "onboarding"
    const val REGISTER = "register"
    const val LOGIN = "login"
    const val MAIN = "main"
    const val ANIMAL_DETAIL = "animal_detail/{animalId}"
    const val NOTIFICATION = "notification"
    const val ADD = "add"
    const val MYPAGE = "mypage"
    const val SETTING = "setting"
}


@Composable
fun AnimalRegister(
    rootNavController: NavHostController,
    viewModel: RegisterViewModel = RegisterViewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RegisterScreens.SelectType.route
    ) {
        composable(RegisterScreens.SelectType.route) {
            SelectTypeScreen(navController,rootNavController, viewModel)
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
            SubmitSuccessScreen(navController, rootNavController,viewModel)
        }
    }
}