package com.example.mnrader.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mnrader.data.repository.AnimalRepository
import com.example.mnrader.ui.add.screen.AnimalTypeScreen
import com.example.mnrader.ui.add.screen.RegisterInfoScreen
import com.example.mnrader.ui.add.screen.ReportOrLostScreen
import com.example.mnrader.ui.add.screen.SelectTypeScreen
import com.example.mnrader.ui.add.screen.SubmitSuccessScreen
import com.example.mnrader.ui.add.viewmodel.AddScreens
import com.example.mnrader.ui.add.viewmodel.AddViewModel
import com.example.mnrader.ui.add.viewmodel.AddViewModelFactory
import com.example.mnrader.ui.animaldetail.screen.MNAnimalDetailScreen
import com.example.mnrader.ui.animaldetail.screen.PortalLostDetailScreen
import com.example.mnrader.ui.animaldetail.screen.PortalProtectDetailScreen
import com.example.mnrader.ui.home.screen.HomeScreen
import com.example.mnrader.ui.mypage.screen.MyPageScreen
import com.example.mnrader.ui.mypost.MyPostScreen
import com.example.mnrader.ui.notification.NotificationScreen
import com.example.mnrader.ui.onboarding.screen.LoginScreen
import com.example.mnrader.ui.onboarding.screen.OnboardingScreen
import com.example.mnrader.ui.onboarding.screen.RegisterScreen
import com.example.mnrader.ui.scrap.ScrapScreen
import com.example.mnrader.ui.setting.screen.AddMyPetScreen
import com.example.mnrader.ui.setting.screen.SettingScreen

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
        ) {
            RegisterScreen(
                navController = navController,
                navigateToLogin = {
                    navController.navigate(Routes.ONBOARDING) {
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
                onLoginSuccess = {
                    // 로그인 성공 후 홈으로 이동 - 모든 백스택 제거
                    onLoginSuccess()
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
                navigateToMNAnimalDetail = { animalId ->
                    navController.navigate("${Routes.MN_ANIMAL_DETAIL}/$animalId")
                },
                navigateToPortalLostDetail = { animalId ->
                    navController.navigate("${Routes.PORTAL_LOST_DETAIL}/$animalId")
                },
                navigateToPortalProtectDetail = { animalId ->
                    navController.navigate("${Routes.PORTAL_PROTECT_DETAIL}/$animalId")
                },
                navigateToNotification = { lastAnimal ->
                    navController.navigate("${Routes.NOTIFICATION}/$lastAnimal")
                },
            )
        }


        // MN 애니멀페이지
        composable(
            route = Routes.MN_ANIMAL_DETAIL + "/{animalId}",
            arguments = listOf(navArgument("animalId") { type = NavType.LongType })
        ) { navBackStackEntry ->

            // 애니멀 ID를 가져오기
            val animalId = navBackStackEntry.arguments?.getLong("animalId")
            if (animalId != null) {
                MNAnimalDetailScreen(
                    animalId = animalId,
                    navController = navController,
                    onBack = { navController.popBackStack() }
                )
            } else {
                Text("잘못된 동물 ID입니다.")
            }
        }

        // Portal 실종동물 상세페이지
        composable(
            route = Routes.PORTAL_LOST_DETAIL + "/{animalId}",
            arguments = listOf(navArgument("animalId") { type = NavType.LongType })
        ) { navBackStackEntry ->

            val animalId = navBackStackEntry.arguments?.getLong("animalId")
            if (animalId != null) {
                PortalLostDetailScreen(
                    animalId = animalId,
                    onBack = { navController.popBackStack() }
                )
            } else {
                Text("잘못된 동물 ID입니다.")
            }
        }

        // Portal 보호동물 상세페이지
        composable(
            route = Routes.PORTAL_PROTECT_DETAIL + "/{animalId}",
            arguments = listOf(navArgument("animalId") { type = NavType.LongType })
        ) { navBackStackEntry ->

            val animalId = navBackStackEntry.arguments?.getLong("animalId")
            if (animalId != null) {
                PortalProtectDetailScreen(
                    animalId = animalId,
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
            MyPostScreen(
                onBackClick = { navController.popBackStack() },
                onMNAnimalClick = { animalId ->
                    navController.navigate("${Routes.MN_ANIMAL_DETAIL}/$animalId")
                },
                onPortalLostClick = { animalId ->
                    navController.navigate("${Routes.PORTAL_LOST_DETAIL}/$animalId")
                },
                onPortalProtectClick = { animalId ->
                    navController.navigate("${Routes.PORTAL_PROTECT_DETAIL}/$animalId")
                },
            )
        }

        // 내가 스크랩 한 게시물
        composable(
            route = Routes.SCRAP_LIST
        ) {
            ScrapScreen(
                onBackClick = { navController.popBackStack() },
                onMNAnimalClick = { animalId ->
                    navController.navigate("${Routes.MN_ANIMAL_DETAIL}/$animalId")
                },
                onPortalLostClick = { animalId ->
                    navController.navigate("${Routes.PORTAL_LOST_DETAIL}/$animalId")
                },
                onPortalProtectClick = { animalId ->
                    navController.navigate("${Routes.PORTAL_PROTECT_DETAIL}/$animalId")
                }
            )
        }

        // 알림
        composable(
            route = Routes.NOTIFICATION + "/{lastAnimal}",
            arguments = listOf(navArgument("lastAnimal") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val lastAnimal = navBackStackEntry.arguments?.getInt("lastAnimal") ?: 0
            NotificationScreen(
                padding = padding,
                lastAnimal = lastAnimal,
                onBackClick = { navController.popBackStack() },
                onMNAnimalClick = { animalId ->
                    navController.navigate("${Routes.MN_ANIMAL_DETAIL}/$animalId")
                },
                onPortalLostClick = { animalId ->
                    navController.navigate("${Routes.PORTAL_LOST_DETAIL}/$animalId")
                },
                onPortalProtectClick = { animalId ->
                    navController.navigate("${Routes.PORTAL_PROTECT_DETAIL}/$animalId")
                },
            )
        }

        // 등록하기
        composable(
            route = Routes.ADD
        ) {
            AnimalRegister(
                rootNavController = navController,
                registerBackEntryManager = registerBackEntryManager
            )
        }

        // 마이페이지
        composable(
            Routes.MYPAGE
        ) {
            MyPageScreen(
                padding = padding,
                navigateToSettings = { navController.navigate(Routes.SETTING) },
                navigateToMyArticles = { navController.navigate(Routes.POST_LIST) },
                navigateToScrap = { navController.navigate(Routes.SCRAP_LIST) },
            )
        }

        composable(route = Routes.SETTING) {
            SettingScreen(
                padding = padding,
                onBack = { navController.popBackStack() },
                navigateToMyAnimalDetail = { navController.navigate("${Routes.MY_PET_DETAIL}/${it.id}") },
                navigateToAddMyAnimal = { navController.navigate(Routes.ADD_MY_PET) },
            )
        }

        composable(route = Routes.ADD_MY_PET) {
            AddMyPetScreen(
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            route = Routes.MY_PET_DETAIL_WITH_ARG,
            arguments = listOf(navArgument("petId") { type = NavType.IntType })
        ) { navBackStackEntry ->
            val petId = navBackStackEntry.arguments?.getInt("petId")
            if (petId != null) {
                // MyPetDetailScreen 구현 필요 시 추가
                Text("반려동물 상세 페이지 - ID: $petId")
            } else {
                Text("잘못된 반려동물 ID입니다.")
            }
        }

    }
}

object Routes {
    const val ONBOARDING = "onboarding"
    const val REGISTER = "register"
    const val LOGIN = "login"
    const val MAIN = "main"
    const val MN_ANIMAL_DETAIL = "mn_animal_detail"
    const val PORTAL_ANIMAL_DETAIL = "portal_animal_detail"
    const val PORTAL_LOST_DETAIL = "portal_lost_detail"
    const val PORTAL_PROTECT_DETAIL = "portal_protect_detail"
    const val POST_LIST = "post_list"
    const val SCRAP_LIST = "scrap_list"
    const val NOTIFICATION = "notification"
    const val ADD = "add"
    const val MYPAGE = "mypage"
    const val SETTING = "setting"
    const val ADD_MY_PET = "add_my_pet"
    const val MY_PET_DETAIL = "my_pet_detail"
    const val MY_PET_DETAIL_WITH_ARG = "my_pet_detail/{petId}"
}

@Composable
fun AnimalRegister(
    rootNavController: NavHostController,
    registerBackEntryManager: RegisterBackEntryManager
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: AddViewModel = viewModel(
        factory = AddViewModelFactory(
            animalRepository = AnimalRepository(),
            context = context
        )
    )
    LaunchedEffect(Unit) {
        if (registerBackEntryManager.prevRoute == null) {
            // 이전 경로가 설정되지 않았으면 기본값 지정
            registerBackEntryManager.prevRoute = Routes.MAIN
        }
    }
    NavHost(
        navController = navController,
        startDestination = AddScreens.SelectType.route
    ) {
        composable(AddScreens.SelectType.route) {
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
        composable(AddScreens.AddInfo.route) {
            RegisterInfoScreen(navController, viewModel)
        }
        composable(AddScreens.AnimalType.route) {
            AnimalTypeScreen(navController, viewModel)
        }
        composable(AddScreens.ReportOrLost.route) {
            ReportOrLostScreen(navController, viewModel)
        }
        composable(AddScreens.SubmitSuccess.route) {
            SubmitSuccessScreen(rootNavController, viewModel)
        }
    }
}

// 등록 화면 이전 경로 저장용
class RegisterBackEntryManager {
    var prevRoute: String? = null
}