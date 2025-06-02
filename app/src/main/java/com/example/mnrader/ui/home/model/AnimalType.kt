package com.example.mnrader.ui.home.model

import androidx.compose.ui.graphics.Color
import com.example.mnrader.ui.theme.Green2
import com.example.mnrader.ui.theme.Red
import com.example.mnrader.ui.theme.SkyBlue

enum class AnimalDataType(
    val type: String,
    val color: Color
) {
    LOST("실종", Red),
    PROTECT("보호중", SkyBlue),
    WITNESS("목격", Green2);
}