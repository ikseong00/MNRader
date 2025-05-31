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
            text = "지역",// TODO: 클릭 이벤트 처리
        )
        DropdownFilterChip(
            text = "품종",// TODO: 클릭 이벤트 처리
        )
        FilterChip(
            text = "목격한 동물"
        )
        FilterChip(
            text = "실종 동물"
        )
        FilterChip(
            text = "보호중"
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeFilterPreview() {
    HomeFilter()
}