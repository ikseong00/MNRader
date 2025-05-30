package com.example.mnrader.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mnrader.ui.onboarding.screen.OnboardingScreen

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

        // 애니멀페이지
        composable(
            route = Routes.ANIMAL_DETAIL
        ) { }

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
    const val ANIMAL_DETAIL = "animal_detail"
    const val POST_LIST = "post_list"
    const val SCRAP_LIST = "scrap_list"
    const val NOTIFICATION = "notification"
    const val ADD = "add"
    const val MYPAGE = "mypage"
    const val SETTING = "setting"
}