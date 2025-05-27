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
    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        UserInfoSection(email = "konkuk", location = "서울 광진구")
        OwningPetSection()
        MyPostSection()
        MyScrapSection()
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageScreenPreview() {

}