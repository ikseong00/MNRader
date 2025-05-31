package com.example.mnrader.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import coil3.compose.AsyncImage
import com.example.mnrader.R
import com.example.mnrader.ui.home.model.AnimalDataType
import com.example.mnrader.ui.home.model.HomeAnimalData
import com.example.mnrader.ui.home.model.HomeAnimalData.Companion.dummyHomeAnimalData
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun HomeAnimalItem(
    modifier: Modifier = Modifier,
    animalData: HomeAnimalData,
    onItemClick: (HomeAnimalData) -> Unit = {},
    onBookmarkClick: (HomeAnimalData) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemClick(animalData) },
        horizontalArrangement = Arrangement.spacedBy(19.dp),
    ) {
        AsyncImage(
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
                .border(
                    width = 6.dp,
                    shape = CircleShape,
                    color = animalData.type.color
                ),
            model = "https://picsum.photos/200/300",
            contentDescription = null,
        )

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = animalData.name,
                    style = MNRaderTheme.typography.medium.copy(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                    )
                )
                if (animalData.isBookmarked) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC30F),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onBookmarkClick(animalData)
                            }
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_outline_star_24),
                        contentDescription = null,
                        tint = Color(0xFFB3B3B3),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onBookmarkClick(animalData)
                            }
                    )
                }
            }
            Text(
                text = "성별: ${animalData.gender.value}",
                style = MNRaderTheme.typography.medium.copy(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = Color(0xFF000000).copy(alpha = 0.5f)
                )
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .background(color = Color.Black.copy(alpha = 0.05f))
                    .padding(horizontal = 4.dp, vertical = 2.dp),
                text = "지역: ${animalData.location}",
                style = MNRaderTheme.typography.medium.copy(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.25.sp,
                )
            )
            Text(
                text = animalData.date,
                style = MNRaderTheme.typography.medium.copy(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.25.sp,
                )
            )
        }
    }
}

@Composable
fun HomeAnimalList(
    modifier: Modifier = Modifier,
    animalDataList: List<HomeAnimalData>,
    onAnimalClick: (HomeAnimalData) -> Unit = {},
    onBookmarkClick: (HomeAnimalData) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "전체 동물",
                style = MNRaderTheme.typography.regular.copy(
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                )
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AnimalDataType.entries.forEach { type ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .border(
                                    width = 2.5.dp,
                                    shape = CircleShape,
                                    color = type.color
                                )
                        )
                        Text(
                            text = type.type,
                            style = MNRaderTheme.typography.semiBold.copy(
                                fontSize = 14.sp,
                                color = type.color
                            )
                        )
                    }
                }
            }
        }

        animalDataList.forEachIndexed { index, animalData ->
            HomeAnimalItem(
                animalData = animalData,
                onItemClick = { onAnimalClick(it) },
                onBookmarkClick = { onBookmarkClick(it) },
            )
            HorizontalDivider(
                color = Color.Black.copy(alpha = 0.1f)
            )
        }


    }
}

@Preview(showBackground = true)
@Composable
private fun AnimalListPreview() {
    HomeAnimalList(
        animalDataList = dummyHomeAnimalData
    )
}