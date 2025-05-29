package com.example.mnrader.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mnrader.R

@Composable
fun PetDetailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 동물 정보 요약
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color.LightGray)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.dog_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text("강아지", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("동물 정보 바꾸기", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(8.dp))

        DropdownLabel("동물 종류", listOf("강아지", "고양이"))
        DropdownLabel("품종", listOf("몰티즈", "푸들"))
        DropdownLabel("성별", listOf("수컷", "암컷"))

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = "1개월",
            onValueChange = {},
            label = { Text("나이") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = "흰색, 사람이랑 친함",
            onValueChange = {},
            label = { Text("특징") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text("사진 수정하기")

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .border(1.dp, Color.LightGray)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.dog_icon),
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { /* 저장 처리 */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA5D6A7))
        ) {
            Text("저장")
        }
    }
}

@Preview
@Composable
private fun PetDetailScreenPreview() {
    PetDetailScreen()
}