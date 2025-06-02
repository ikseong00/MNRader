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
import androidx.navigation.navArgument
import com.example.mnrader.ui.mypage.Screen.MyPageScreen
import com.example.mnrader.ui.mypage.Screen.PetDetailScreen
import com.example.mnrader.ui.mypage.Screen.PostListScreen
import com.example.mnrader.ui.mypage.Screen.ScrapListScreen
import com.example.mnrader.ui.mypage.viewmodel.MyPageViewModel
import com.example.mnrader.ui.home.screen.HomeScreen
import com.example.mnrader.ui.onboarding.screen.OnboardingScreen
import com.example.mnrader.ui.setting.viewmodel.SettingViewModel
import com.example.mnrader.ui.settings.screen.AddMyPetScreen
import com.example.mnrader.ui.settings.screen.SettingScreen

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
            val petId = backStackEntry.arguments?.getString("petId")

            val parentEntry = remember(backStackEntry) {
                // MyPageScreen이 사용한 ViewModel의 NavBackStackEntry
                navController.getBackStackEntry(Routes.MYPAGE)
            }
            // MyPageScreen의 viewmodel 인스턴스 재사용
            // MyPageScreen에서 생성된 pets데이터 사용하기 위해
            val viewModel: MyPageViewModel = viewModel(parentEntry)

            val pet = viewModel.pets.value.find { it.id == petId }

            if (pet != null) {
                PetDetailScreen(pet = pet, viewModel = viewModel, navController = navController)
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
            PostListScreen(navController = navController, viewModel = viewModel)
        }

        // 내가 스크랩 한 게시물
        composable(
            route = Routes.SCRAP_LIST
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Routes.MYPAGE)
            }
            val viewModel: MyPageViewModel = viewModel(parentEntry)
            ScrapListScreen(navController = navController, viewModel = viewModel)
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
        ) { }

        // 등록하기
        composable(
            route = Routes.ADD
        ) { }

        // 마이페이지
        composable(
            route = Routes.MYPAGE
        ) {
            MyPageScreen(navController = navController)
        }

        // 설정
        composable(
            route = Routes.SETTING
        ) {
            SettingScreen(navController = navController)
        }

        composable(
            route = Routes.ADD_MY_PET
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Routes.SETTING)
            }
            val viewModel: SettingViewModel = viewModel(parentEntry)
            AddMyPetScreen(navController = navController, viewModel = viewModel)
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