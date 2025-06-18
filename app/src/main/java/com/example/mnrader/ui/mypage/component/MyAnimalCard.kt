package com.example.mnrader.ui.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mnrader.ui.common.AnimalCard
import com.example.mnrader.ui.mypage.viewmodel.MyAnimal

@Composable
fun MyAnimalCardList(
    modifier: Modifier = Modifier,
    animalList: List<MyAnimal>,
) {

    val columnCount = when {
        animalList.isEmpty() -> 1
        animalList.size < 3 -> animalList.size
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
                modifier = modifier,
                animal = animalList[index]
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyAnimalCardPreview() {
    MyAnimalCardList(
        animalList = MyAnimal.dummyMyAnimalList
    )
}