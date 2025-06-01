package com.example.mnrader.ui.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mnrader.R

@Composable
fun OwningPetSection(
    pets: List<Pet>,
    onPetClick: (Pet) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text("보유중인 동물", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))

        pets.chunkedPairs().forEach { rowPets ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowPets.forEach { pet ->
                    PetCard(pet = pet, modifier = Modifier.weight(1f)) {
                        onPetClick(pet)
                    }
                }

                // 짝수 맞추기 위해 빈 공간 추가
                if (rowPets.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

fun <T> List<T>.chunkedPairs(): List<List<T>> =
    this.chunked(2)
