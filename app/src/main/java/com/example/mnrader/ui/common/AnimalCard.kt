package com.example.mnrader.ui.common

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mnrader.ui.mypage.viewmodel.MyAnimal
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun AnimalCard(
    modifier: Modifier = Modifier,
    animal: MyAnimal,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.1f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = animal.imgUrl,
            contentDescription = "Animal Image",
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = animal.name,
            style = MNRaderTheme.typography.regular.copy(
                fontSize = 12.sp
            )
        )
    }
}