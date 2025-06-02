package com.example.mnrader.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mnrader.ui.home.model.HomeAnimalData
import com.example.mnrader.ui.home.model.MapAnimalData
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun MapAnimalInfo(
    modifier: Modifier = Modifier,
    animalData: MapAnimalData,
    onItemClick: (MapAnimalData) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onItemClick(animalData) }
            .padding(horizontal = 20.dp, vertical = 15.dp),
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
            model = animalData.imageUrl,
            contentDescription = null,
        )

        Column {
            Text(
                text = animalData.name,
                style = MNRaderTheme.typography.medium.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                )
            )
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

@Preview
@Composable
private fun MapAnimalInfoPreview() {
    val homeAnimalData = HomeAnimalData.dummyHomeAnimalData.first()
    MapAnimalInfo(
        animalData = MapAnimalData(
            id = homeAnimalData.id,
            imageUrl = homeAnimalData.imageUrl,
            name = homeAnimalData.name,
            location = homeAnimalData.location,
            latLng = homeAnimalData.latLng,
            type = homeAnimalData.type,
            date = homeAnimalData.date,
            gender = homeAnimalData.gender
        )
    )
}