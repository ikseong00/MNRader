package com.example.mnrader.ui.home.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mnrader.ui.common.DropdownFilterChip
import com.example.mnrader.ui.common.FilterChip

@Composable
fun HomeFilter(
    modifier: Modifier = Modifier,
    onLocationClick: (String) -> Unit = {},
    onBreedClick: (String) -> Unit = {},
    onWitnessClick: () -> Unit = {},
    onLostClick: () -> Unit = {},
    onProtectClick: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
            .shadow(1.dp)
            .padding(vertical = 7.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        DropdownFilterChip(
            text = "지역",
            onClick = { onLocationClick(it) }
        )
        DropdownFilterChip(
            text = "품종",
            onClick = { onBreedClick(it) }
        )
        FilterChip(
            text = "목격한 동물",
            onClick = onWitnessClick
        )
        FilterChip(
            text = "실종 동물",
            onClick = onLostClick
        )
        FilterChip(
            text = "보호중",
            onClick = onProtectClick
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeFilterPreview() {
    HomeFilter()
}