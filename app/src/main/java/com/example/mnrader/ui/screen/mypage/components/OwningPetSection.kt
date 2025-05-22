package com.example.mnrader.ui.screen.mypage.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
            AnimalCard("강아지")
            AnimalCard("고양이")
        }
    }
}

@Composable
fun AnimalCard(name:String, modifier: Modifier = Modifier) {
    Box() {
        Column {

        }
    }
}