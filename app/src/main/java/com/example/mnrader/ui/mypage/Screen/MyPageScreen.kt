package com.example.mnrader.ui.mypage.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mnrader.ui.mypage.viewmodel.MyPageViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.navigation.Routes
import com.example.mnrader.ui.mypage.component.MyPostSection
import com.example.mnrader.ui.mypage.component.MyScrapSection
import com.example.mnrader.ui.mypage.component.OwningPetSection
import com.example.mnrader.ui.mypage.component.UserInfoSection

@Composable
fun MyPageScreen(
    navController: NavHostController,
    viewModel: MyPageViewModel = viewModel()
) {
    // 예시: 로그인된 사용자 이메일
    val loggedInEmail = "123@konkuk.ac.kr"

    // 화면 진입 시 이메일 기반 데이터 로딩
    LaunchedEffect(loggedInEmail) {
        viewModel.loadDummyUserData(loggedInEmail)
    }

    val user by viewModel.user.collectAsState()
    val pets by viewModel.pets.collectAsState()
    val posts by viewModel.posts.collectAsState()
    val scraps by viewModel.scraps.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        UserInfoSection(email = user.email, location = user.region, navController = navController)

        OwningPetSection(
            pets = pets,
            onPetClick = { pet ->
                navController.navigate("animal_detail/${pet.id}")
            }
        )

        MyPostSection(
            posts = posts,
            postClick = { postId -> println("Clicked post: $postId") },
            allPostsClick = {
                navController.navigate(Routes.POST_LIST)
            }
        )

        MyScrapSection(
            scraps = scraps,
            scrapClick = { scrapId -> println("Clicked scrap: $scrapId") },
            allScrapClick = {
                navController.navigate(Routes.SCRAP_LIST)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    // 임시 NavController (Preview용)
    val fakeNavController = rememberNavController()
    MyPageScreen(navController = fakeNavController)
}
