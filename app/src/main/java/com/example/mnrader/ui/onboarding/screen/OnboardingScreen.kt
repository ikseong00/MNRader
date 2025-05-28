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
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.padding(top = 276.dp),
                painter = painterResource(R.drawable.img_logo_onboarding),
                contentDescription = null,
            )
            Spacer(Modifier.height(38.dp))
            Text(
                style = MNRaderTheme.typography.regular.copy(
                    fontSize = 20.sp,
                    lineHeight = 14.sp
                ),
                text = "레이더처럼 유기동물의 위치를 파악한다",
            )
        }

        Column {
            MNRaderButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .height(43.dp),
                text = "로그인"
            )
            Spacer(Modifier.height(18.dp))
            MNRaderButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .padding(bottom = 54.dp)
                    .height(43.dp),
                text = "회원가입"
            )
        }
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