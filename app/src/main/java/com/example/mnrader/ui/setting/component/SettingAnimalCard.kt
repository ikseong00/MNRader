package com.example.mnrader.ui.setting.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mnrader.ui.common.AnimalCard
import com.example.mnrader.ui.mypage.viewmodel.MyAnimal
import com.example.mnrader.ui.theme.Green1
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun SettingAnimalCardList(
    modifier: Modifier = Modifier,
    animalList: List<MyAnimal>,
    onAnimalClick: (MyAnimal) -> Unit = { },
    onAddAnimalClick: () -> Unit = { }
) {

    val columnCount = when {
        animalList.size < 3 -> animalList.size + 1
        else -> 3
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(animalList.size) { index ->
            AnimalCard(
                modifier = Modifier,
                animal = animalList[index],
                onClick = { onAnimalClick(animalList[index]) }
            )
        }
        item {
            AddAnimalButton(
                onClick = { onAddAnimalClick() },
            )
        }
    }
}

@Composable
fun AddAnimalButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Column(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.1f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .border(
                    width = 3.dp,
                    color = Green1,
                    shape = CircleShape
                ),
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Center),
                tint = Green1
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "추가",
            style = MNRaderTheme.typography.regular.copy(
                fontSize = 12.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingAnimalCardPreview() {
    SettingAnimalCardList(
        animalList = MyAnimal.dummyMyAnimalList
    )
}