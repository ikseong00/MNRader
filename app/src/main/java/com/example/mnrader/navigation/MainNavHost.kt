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
import com.example.mnrader.ui.mypage.MyPageScreen
import com.example.mnrader.ui.mypage.Pet
import com.example.mnrader.ui.mypage.PetDetailScreen
import com.example.mnrader.ui.mypage.viewmodel.MyPageViewModel
import com.example.mnrader.ui.onboarding.screen.OnboardingScreen

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainNavHost(
    navController: NavHostController,
    padding: PaddingValues,
) {

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
        ) { }

        // 로그인
        composable(
            route = Routes.LOGIN
        ) { }

        // 메인화면
        composable(
            route = Routes.MAIN
        ) { }

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
                PetDetailScreen(pet = pet, viewModel = viewModel)
            } else {
                Text("해당 동물을 찾을 수 없습니다.")
            }

        }


        // 내가 올린 게시물
        composable(
            route = Routes.POST_LIST
        ) { }

        // 내가 스크랩 한 게시물
        composable(
            route = Routes.SCRAP_LIST
        ) { }

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
        ) { }

    }
}

object Routes {
    const val ONBOARDING = "onboarding"
    const val REGISTER = "register"
    const val LOGIN = "login"
    const val MAIN = "main"
    const val ANIMAL_DETAIL_WITH_ARG = "animal_detail/{petId}"
    const val POST_LIST = "post_list"
    const val SCRAP_LIST = "scrap_list"
    const val NOTIFICATION = "notification"
    const val ADD = "add"
    const val MYPAGE = "mypage"
    const val SETTING = "setting"
}