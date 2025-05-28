package com.example.mnrader.ui.onboarding.screen

import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mnrader.R
import com.example.mnrader.ui.common.MNRaderButton
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun OnboardingScreen(
    padding: PaddingValues,
    navigateToLogin: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.weight(276f))
        Image(
            painter = painterResource(R.drawable.img_logo_onboarding),
            contentDescription = null,
        )
        Spacer(Modifier.weight(38f))
        Text(
            style = MNRaderTheme.typography.regular.copy(
                fontSize = 20.sp,
                lineHeight = 14.sp,
                color = Color(0xFF045955)
            ),
            text = "레이더처럼 유기동물의 위치를 파악한다",
        )
        Spacer(Modifier.weight(162f))

        MNRaderButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(43.dp),
            text = "로그인",
            onClick = navigateToLogin
        )
        Spacer(Modifier.weight(18f))
        MNRaderButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(bottom = 54.dp)
                .height(43.dp),
            onClick = navigateToSignUp,
            text = "회원가입"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    OnboardingScreen(
        padding = PaddingValues(),
        navigateToLogin = { },
        navigateToSignUp = { }
    )
}