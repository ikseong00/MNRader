package com.example.mnrader.ui.mypage.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun MyPageColumn(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MNRaderTheme.typography.medium.copy(
                fontSize = 18.sp
            )
        )
        Text(
            text = "전체보기 >",
            style = MNRaderTheme.typography.medium.copy(
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.5f)
            )
        )


    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageColumnPreview() {
    MyPageColumn(
        text = "My Page Column",
        onClick = {}
    )
}