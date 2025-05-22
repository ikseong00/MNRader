package com.example.mnrader.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavHost(
    padding: PaddingValues,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MAIN,
        modifier = Modifier.padding(padding),
    ) {

    }
}

object Routes {
    // 온보딩, 회원가입, 로그인, 메인화면, 애니멀페이지, 알림 , add
    const val ONBOARDING = "onboarding"
    const val REGISTER = "register"
    const val LOGIN = "login"
    const val MAIN = "main"
    const val ANIMAL_DETAIL = "animal_detail"
    const val NOTIFICATION = "notification"
    const val ADD = "add"
    const val MYPAGE = "mypage"
    const val SETTING = "setting"
}