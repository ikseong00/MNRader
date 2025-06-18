package com.example.mnrader.ui.mypost.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.mnrader.ui.mypost.viewmodel.MyPostModel
import com.example.mnrader.ui.mypost.viewmodel.MyPostUiState
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun MyPostAnimalCard(
    modifier: Modifier = Modifier,
    myPostModel: MyPostModel,
    onClick: (Long) -> Unit = { }
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(myPostModel.id) }
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
                    .size(90.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = myPostModel.type.color,
                        shape = CircleShape
                    ),
                model = myPostModel.imageUrl,
                contentDescription = "내 게시글 동물 이미지",
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = myPostModel.name,
                    style = MNRaderTheme.typography.medium.copy(
                        fontSize = 20.sp
                    ),
                )
                Text(
                    text = "성별 ${myPostModel.gender.value}",
                    style = MNRaderTheme.typography.medium.copy(
                        fontSize = 20.sp,
                        color = Color.Black.copy(alpha = 0.5f),
                    ),
                )
                Text(
                    text = "지역: ${myPostModel.address}",
                    style = MNRaderTheme.typography.medium.copy(
                        fontSize = 20.sp
                    ),
                    modifier = Modifier
                        .background(
                            color = Color.Black.copy(alpha = 0.05f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                )
                Text(
                    text = myPostModel.date,
                    style = MNRaderTheme.typography.medium.copy(
                        fontSize = 20.sp
                    ),
                )
            }

            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "내 게시글 상세로 이동",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPostAnimalCardPreview() {
    MyPostAnimalCard(
        myPostModel = MyPostUiState.dummyList.first()
    )
} 