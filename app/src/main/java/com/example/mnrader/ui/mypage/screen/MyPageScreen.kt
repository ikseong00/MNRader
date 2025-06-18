package com.example.mnrader.ui.mypage.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mnrader.ui.common.CommonTopBar
import com.example.mnrader.ui.mypage.component.MyAnimalCardList
import com.example.mnrader.ui.mypage.component.MyPageColumn
import com.example.mnrader.ui.mypage.component.UserInfo
import com.example.mnrader.ui.mypage.viewmodel.MyAnimal
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun MyPageScreen(
    padding: PaddingValues,
    navigateToSettings: () -> Unit = {},
    navigateToMyArticles: () -> Unit = {},
    navigateToScrap: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 12.dp)
    ) {
        CommonTopBar(
            hasBackButton = false,
            title = "MY PAGE",
        )

        Spacer(modifier = Modifier.width(20.dp))

        UserInfo(
            modifier = Modifier.padding(horizontal = 16.dp),
            imgUrl = "https://example.com/user_profile.jpg",
            email = "",
            address = "서울시 강남구 역삼동",
            onSettingsClick = navigateToSettings
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = "보유 중인 동물",
            style = MNRaderTheme.typography.medium.copy(
                fontSize = 24.sp
            )
        )

        Spacer(modifier = Modifier.width(20.dp))

        MyAnimalCardList(
            animalList = MyAnimal.dummyMyAnimalList
        )
        Spacer(modifier = Modifier.width(20.dp))

        MyPageColumn(
            text = "내가 올린 게시물",
            onClick = navigateToMyArticles
        )
        HorizontalDivider(
            thickness = 1.dp
        )
        MyPageColumn(
            text = "스크랩",
            onClick = navigateToScrap
        )
    }
}

@Preview
@Composable
private fun MyPageScreenPreview() {
//    MyPageScreen()
}