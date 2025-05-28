package com.example.mnrader.ui.common

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FilterChip(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = { }
) {
    Row(
        modifier = modifier
            .height(32.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFCAC4D0)
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = text
        )
    }
}

@Composable
fun DropdownFilterChip(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = { }
) {
    Row(
        modifier = modifier
            .height(32.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFCAC4D0)
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(start = 16.dp)
        )
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterChipPreview() {
    FilterChip(text = "목격한 동물")
}

@Preview(showBackground = true)
@Composable
private fun DropdownFilterChipPreview() {
    DropdownFilterChip(text = "지역")
}

