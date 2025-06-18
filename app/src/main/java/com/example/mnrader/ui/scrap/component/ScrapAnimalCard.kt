package com.example.mnrader.ui.scrap.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mnrader.ui.scrap.viewmodel.ScrapModel
import com.example.mnrader.ui.scrap.viewmodel.ScrapUiState
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun ScrapAnimalCard(
    modifier: Modifier = Modifier,
    scrapModel: ScrapModel,
    onClick: (Long) -> Unit = { }
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(scrapModel.id) }
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = scrapModel.type.color,
                        shape = CircleShape
                    ),
                model = scrapModel.imageUrl,
                contentDescription = "스크랩된 동물 이미지",
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${scrapModel.name} / ${scrapModel.address}",
                    style = MNRaderTheme.typography.medium.copy(
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = scrapModel.date,
                    style = MNRaderTheme.typography.regular.copy(
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                )
            }

            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "내 스크랩 상세로 이동",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScrapAnimalCardPreview() {
    ScrapAnimalCard(
        scrapModel = ScrapUiState.dummyList.first()
    )
} 