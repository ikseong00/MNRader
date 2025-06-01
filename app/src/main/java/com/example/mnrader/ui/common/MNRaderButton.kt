package com.example.mnrader.ui.common

import android.R.attr.onClick
import android.R.attr.text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mnrader.ui.theme.Green1
import com.example.mnrader.ui.theme.MNRaderTheme

@Composable
fun MNRaderButton(
    modifier: Modifier = Modifier,
    text: String,
    cornerShape: Dp = 8.dp,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(cornerShape),
        colors = ButtonDefaults.buttonColors(
            containerColor = Green1
        )
    ) {
        Text(
            text = text,
            style = MNRaderTheme.typography.medium.copy(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                letterSpacing = 0.1.sp,
                color = Color.White
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MNRaderButtonPreview() {
    MNRaderButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 20.dp)
            .height(43.dp),
        text = "로그인"
    )
}