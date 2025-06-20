package com.example.mnrader.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun CommonTopBar(
    modifier: Modifier = Modifier,
    hasBackButton: Boolean = true,
    title: String = "",
    onBack: () -> Unit = { }
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .shadow(elevation = 1.dp)
        ,
    ) {
        if (hasBackButton) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                    contentDescription = "Back"
                )
            }
        }
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = title,
            style = MNRaderTheme.typography.semiBold.copy(
                fontSize = 16.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CommonTopBarPreview() {
    CommonTopBar(
        title = "Common Top Bar",
        onBack = { /* Do nothing */ }
    )
}