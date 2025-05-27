package com.example.mnrader.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person

object NavBarItems{
    val BarItems = listOf(
        BarItem(
            title = Routes.Home.route,
            selectIcon = Icons.Default.Home,
            onSelectedIcon = Icons.Outlined.Home,
            route = Routes.Home.route
        ),
        BarItem(
            title = Routes.Animals.route,
            selectIcon = Icons.Default.Person,
            onSelectedIcon = Icons.Outlined.Person,
            route = Routes.Animals.route
        ),
        BarItem(
            title = Routes.MyPage.route,
            selectIcon = Icons.Default.Favorite,
            onSelectedIcon = Icons.Outlined.FavoriteBorder,
            route = Routes.MyPage.route,
        )
    )
}