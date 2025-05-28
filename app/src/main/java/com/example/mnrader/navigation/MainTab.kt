package com.example.mnrader.navigation

import androidx.annotation.DrawableRes
import com.example.mnrader.R

enum class MainTab(
    @DrawableRes val iconResId: Int,
    val contentDescription: String,
    val route: String,
    val text: String?
) {
    HOME(
        iconResId = R.drawable.ic_nav_home,
        contentDescription = "홈",
        route = Routes.MAIN,
        text = "Home"
    ),
    ADD(
        iconResId = R.drawable.ic_nav_add,
        contentDescription = "추가",
        route = Routes.ADD,
        text = null
    ),
    MY(
        iconResId = R.drawable.ic_nav_my,
        contentDescription = "북마크",
        route = Routes.MYPAGE,
        text = "My Page"
    ),
}