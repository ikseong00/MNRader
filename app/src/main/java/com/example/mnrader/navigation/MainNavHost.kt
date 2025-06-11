package com.example.mnrader.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.add.addScreens.AnimalTypeScreen
import com.example.mnrader.add.addScreens.RegisterInfoScreen
import com.example.mnrader.add.addScreens.ReportOrLostScreen
import com.example.mnrader.add.addScreens.SelectTypeScreen
import com.example.mnrader.add.addScreens.SubmitSuccessScreen
import com.example.mnrader.add.model.RegisterScreens
import com.example.mnrader.add.model.RegisterViewModel
import androidx.navigation.navArgument
import com.example.mnrader.ui.home.screen.HomeScreen
import com.example.mnrader.ui.mypage.screen.MyPageScreen
import com.example.mnrader.ui.mypage.screen.PetDetailScreen
import com.example.mnrader.ui.mypage.screen.PostListScreen
import com.example.mnrader.ui.mypage.screen.ScrapListScreen
import com.example.mnrader.ui.mypage.viewmodel.MyPageViewModel
import com.example.mnrader.ui.mypage.viewmodel.PetUploadViewModel
import com.example.mnrader.ui.notification.NotificationScreen
import com.example.mnrader.ui.onboarding.screen.OnboardingScreen

@SuppressLint("StateFlowValueCalledInComposition")
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

        // 동물 상세보기 페이지
        composable(
            route = Routes.ANIMAL_DETAIL_WITH_ARG,
            arguments = listOf(navArgument("petId") { type = NavType.StringType })
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getString("petId")?.toIntOrNull()

            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.MYPAGE)
            }
            val myPageViewModel: MyPageViewModel = viewModel(parentEntry)
            val uploadViewModel: PetUploadViewModel = viewModel() // 새로 생성

            val pet = myPageViewModel.pets.value.find { it.id == petId }

            if (pet != null) {
                PetDetailScreen(
                    pet = pet,
                    viewModel = uploadViewModel,
                    myPageViewModel = myPageViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                Text("해당 동물을 찾을 수 없습니다.")
            }
        }


        // 내가 올린 게시물
        composable(
            route = Routes.POST_LIST
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Routes.MYPAGE)
            }
            val viewModel: MyPageViewModel = viewModel(parentEntry)
            PostListScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onPostClick = { postId -> navController.navigate("animal_post_detail/$postId") }
            )
        }

        // 내가 스크랩 한 게시물
        composable(
            route = Routes.SCRAP_LIST
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Routes.MYPAGE)
            }
            val viewModel: MyPageViewModel = viewModel(parentEntry)
            ScrapListScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onScrapClick = { postId -> navController.navigate("animal_post_detail/$postId") }
            )
        }

        // todo 동물 상세 페이지 (figma: AnimalPage) 컴포저블 구현 이후 처리 예정
        composable(
            route = Routes.ANIMAL_POST_DETAIL,
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")
            Text("AnimalPostDetailScreen for Post ID: $postId") // 화면 미구현 대체
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
            AnimalRegister(navController)
        }

        // 마이페이지
        composable(
            Routes.MYPAGE
        ) {
            MyPageScreen(
                onNavigateToPetDetail = { petId -> navController.navigate("animal_detail/$petId") },
                onNavigateToPostDetail = { postId -> navController.navigate("animal_post_detail/$postId") },
                onNavigateToScrapDetail = { scrapId -> navController.navigate("animal_post_detail/$scrapId") },
                onNavigateToAllPosts = { navController.navigate(Routes.POST_LIST) },
                onNavigateToAllScraps = { navController.navigate(Routes.SCRAP_LIST) }
            )
        }

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
    const val ANIMAL_DETAIL_WITH_ARG = "animal_detail/{petId}"
    const val ANIMAL_POST_DETAIL = "animal_post_detail/{postId}"
    const val POST_LIST = "post_list"
    const val SCRAP_LIST = "scrap_list"
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
            SubmitSuccessScreen(rootNavController,viewModel)
        }
    }
}