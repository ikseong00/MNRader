package com.example.mnrader.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mnrader.ui.add.addScreens.AnimalTypeScreen
import com.example.mnrader.ui.add.addScreens.RegisterInfoScreen
import com.example.mnrader.ui.add.addScreens.ReportOrLostScreen
import com.example.mnrader.ui.add.addScreens.SelectTypeScreen
import com.example.mnrader.ui.add.addScreens.SubmitSuccessScreen
import com.example.mnrader.ui.add.model.RegisterScreens
import com.example.mnrader.ui.add.model.RegisterViewModel
import com.example.mnrader.ui.home.component.AnimalDetailScreen
import com.example.mnrader.ui.home.screen.HomeScreen
import com.example.mnrader.ui.notification.NotificationScreen
import com.example.mnrader.ui.onboarding.screen.OnboardingScreen
import com.example.mnrader.ui.userRegisterOrLogin.LoginScreen
import com.example.mnrader.ui.userRegisterOrLogin.UserRegisterScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainNavHost(
    navController: NavHostController,
    padding: PaddingValues,
    onLoginSuccess: () -> Unit
) {
    // 등록 화면 이전 경로 저장용 객체를 mainNavHost에서 생성
    val registerBackEntryManager = remember { RegisterBackEntryManager() }

    NavHost(
        navController = navController,
        startDestination = Routes.ONBOARDING,
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
        ) {
            UserRegisterScreen(
                navController = navController,
                onRegisterClick = { _, _, _ ->
                    // 회원가입 처리 후 로그인 화면으로 이동
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                }
            )
        }

        // 로그인
        composable(
            route = Routes.LOGIN
        ) {
            LoginScreen(
                navController = navController,
                onLoginClick = { _, _ ->
                    // 로그인 로직 처리 후 홈으로 이동
                    onLoginSuccess()
//                    navController.navigate(Routes.MAIN) {
//                        popUpTo(Routes.LOGIN) { inclusive = true }
//                    }
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        // 메인화면
        composable(
            route = Routes.MAIN
        ) {
            HomeScreen(
                padding = padding,
                navigateToAnimalDetail = { animalId ->
                    navController.navigate("animal_detail/$animalId")
                }
            )
        }

        // 애니멀페이지
        composable(
            route = Routes.ANIMAL_DETAIL + "/{animalId}",
            arguments = listOf(navArgument("animalId") { type = NavType.StringType })
        ) { navBackStackEntry ->

            // 애니멀 ID를 가져오기
            val animalId = navBackStackEntry.arguments?.getString("animalId")?.toLongOrNull()
            if (animalId != null) {
                AnimalDetailScreen(
                    animalId = animalId,
                    navController = navController,
                    onBack = { navController.popBackStack() }
                )
            } else {
                Text("잘못된 동물 ID입니다.")
            }
        }


        // 내가 올린 게시물
        composable(
            route = Routes.POST_LIST
        ) {

        }

        // 내가 스크랩 한 게시물
        composable(
            route = Routes.SCRAP_LIST
        ) {

        }

        composable(
            route = Routes.ANIMAL_POST_DETAIL,
        ) {

        }

        // 알림
        composable(
            route = Routes.NOTIFICATION
        ) {
            NotificationScreen(
                padding = padding,
                onBackClick = { navController.popBackStack() },
                onItemClick = { /* TODO : 상세 페이지로 이동 */},
            )
        }

        // 등록하기
        composable(
            route = Routes.ADD
        ) {
            AnimalRegister( rootNavController = navController,
                registerBackEntryManager = registerBackEntryManager
            )
        }

        // 마이페이지
        composable(
            Routes.MYPAGE
        ) {

        }

        composable(route = Routes.SETTING) {

        }

        composable(route = Routes.ADD_MY_PET) {

        }



    }
}

object Routes {
    const val ONBOARDING = "onboarding"
    const val REGISTER = "register"
    const val LOGIN = "login"
    const val MAIN = "main"
    const val ANIMAL_DETAIL = "animal_detail/{animalId}"
    const val ANIMAL_DETAIL_WITH_ARG = "animal_detail/{petId}"
    const val ANIMAL_POST_DETAIL = "animal_post_detail/{postId}"
    const val POST_LIST = "post_list"
    const val SCRAP_LIST = "scrap_list"
    const val NOTIFICATION = "notification"
    const val ADD = "add"
    const val MYPAGE = "mypage"
    const val SETTING = "setting"
    const val ADD_MY_PET = "add_my_pet"
}

@Composable
fun AnimalRegister(
    rootNavController: NavHostController,
    registerBackEntryManager: RegisterBackEntryManager
) {
    val navController = rememberNavController()
    val viewModel: RegisterViewModel = viewModel()
    LaunchedEffect(Unit) {
        if (registerBackEntryManager.prevRoute == null) {
            // 이전 경로가 설정되지 않았으면 기본값 지정
            registerBackEntryManager.prevRoute = Routes.MAIN
        }
    }
    NavHost(
        navController = navController,
        startDestination = RegisterScreens.SelectType.route
    ) {
        composable(RegisterScreens.SelectType.route) {
            SelectTypeScreen(
                navController = navController,
                viewModel = viewModel,
                onBackClick = {
                    registerBackEntryManager.prevRoute?.let {
                        rootNavController.navigate(it) {
                            popUpTo(Routes.ADD) { inclusive = true }
                        }
                    } ?: run {
                        if (!rootNavController.popBackStack()) {
                            rootNavController.navigate(Routes.MAIN)
                        }
                    }
                }
            )
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
            SubmitSuccessScreen(rootNavController,viewModel)
        }
    }
}

// 등록 화면 이전 경로 저장용
class RegisterBackEntryManager {
    var prevRoute: String? = null
}