package com.example.mnrader.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mnrader.R

@Composable
fun OwningPetSection(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row {
            Text("보유중인 동물", fontSize = 20.sp)
            Spacer(Modifier.weight(1f))
            Button(onClick = {}, modifier = Modifier.weight(1f)) {
                Text(">", fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AnimalCard("강아지", R.drawable.dog_icon)
            AnimalCard("고양이", R.drawable.cat_icon)
        }
    }
}

@Composable
fun AnimalCard(name:String, imageRes: Int) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(12.dp))
           // .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = "pet 아이콘 이미지",
            modifier = Modifier.size(48.dp)
        )
        Text(name)
    }

}