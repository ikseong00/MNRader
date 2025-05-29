package com.example.mnrader.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyPageScreen(modifier: Modifier = Modifier) {
    // dummy 데이터
    val dummyPosts = listOf(
        MyPost(
            id = "1",
            name = "인절미",
            gender = "암컷",
            region = "서울 광진구",
            date = "2025-03-24",
            imageUrl = "https://example.com/dog.png"
        )
    )

    val dummyScraps = listOf(
        Scrap(
            id = "scrap1",
            title = "치와와",
            region = "서울 광진구",
            date = "2024.06.07",
            imageUrl = "https://example.com/dog.png"
        ),
        Scrap(
            id = "scrap2",
            title = "고양이",
            region = "서울 동작구",
            date = "2025.04.01",
            imageUrl = "https://example.com/cat.png"
        )
    )

    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        UserInfoSection(email = "123konkuk@konkuk.ac.kr", location = "서울 광진구")
        OwningPetSection()
        MyPostSection(
            posts = dummyPosts,
            postClick = { postId -> println("Clicked post: $postId") },
            allPostsClick = { println("Clicked 전체보기 for posts") }
        )
        MyScrapSection(
            scraps = dummyScraps,
            scrapClick = { scrapId -> println("Clicked scrap: $scrapId") },
            allScrapClick = { println("Clicked 전체보기 for scraps") }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {
    MyPageScreen()
}