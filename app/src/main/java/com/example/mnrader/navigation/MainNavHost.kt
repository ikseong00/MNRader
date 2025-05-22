package com.example.mnrader.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun MainNavHost(
    padding : PaddingValues,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Main",
        modifier = Modifier.padding(padding),
    ) {

    }
}