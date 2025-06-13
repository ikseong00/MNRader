package com.example.mnrader.ui.add.addScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterTopBar(
            onBackClick:(() -> Unit)? = null,
            currentStep: Int, // 현재 단계 1~5
            totalSteps: Int = 5) {
    Column {
        // TopAppBar
        TopAppBar(
            title = {
                Text("등록하기",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center)
            },
            navigationIcon = {
                onBackClick?.let{
                        backClick ->
                    IconButton(onClick = { backClick() })  {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                }
            },
            actions = {
                if (onBackClick != null) {
                    Spacer(modifier = Modifier.width(48.dp))
                }
            }
        )

        // 진도 라인
        StepProgressIndicator(currentStep = currentStep, totalSteps = totalSteps)
    }
}

@Composable
fun StepProgressIndicator(currentStep: Int, totalSteps: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalSteps) { index ->
            val isActive = index < currentStep
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .weight(1f)
                    .clip(CircleShape)
                    .background(if (isActive) Color(0xFF89C5A9) else Color.LightGray)
            )
            if (index != totalSteps - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}
