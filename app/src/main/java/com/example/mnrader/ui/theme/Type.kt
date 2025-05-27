package com.example.mnrader.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.mnrader.R

val RobotoBlack = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto_black)),
    fontWeight = FontWeight.Black,
)

val RobotoExtraBold = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto_extrabold)),
    fontWeight = FontWeight.ExtraBold,
)

val RobotoBold = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto_bold)),
    fontWeight = FontWeight.Bold,
)
val RobotoSemiBold = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto_semibold)),
    fontWeight = FontWeight.SemiBold,
)
val RobotoMedium = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto_medium)),
    fontWeight = FontWeight.Medium,
)
val RobotoRegular = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto_regular)),
    fontWeight = FontWeight.Normal,
)

val RobotoLight = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto_light)),
    fontWeight = FontWeight.Light,
)

val RobotoExtraLight = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto_extralight)),
    fontWeight = FontWeight.Thin,
)

@Immutable
data class MNRaderTypography(
    val black: TextStyle,
    val extraBold: TextStyle,
    val bold: TextStyle,
    val semiBold: TextStyle,
    val medium: TextStyle,
    val regular: TextStyle,
    val light: TextStyle,
    val extraLight: TextStyle,
)

internal val Typography = MNRaderTypography(
    black = RobotoBlack,
    extraBold = RobotoExtraBold,
    bold = RobotoBold,
    semiBold = RobotoSemiBold,
    medium = RobotoMedium,
    regular = RobotoRegular,
    light = RobotoLight,
    extraLight = RobotoExtraLight,
)

val LocalTypography = staticCompositionLocalOf {
    MNRaderTypography(
        black = RobotoBlack,
        extraBold = RobotoExtraBold,
        bold = RobotoBold,
        semiBold = RobotoSemiBold,
        medium = RobotoMedium,
        regular = RobotoRegular,
        light = RobotoLight,
        extraLight = RobotoExtraLight
    )
}