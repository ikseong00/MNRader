package com.example.mnrader

import android.net.http.SslCertificate.restoreState
import android.net.http.SslCertificate.saveState
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.navigation.MainNavHost
import com.example.mnrader.navigation.MainTab
import com.example.mnrader.navigation.NavigationBar
import com.example.mnrader.ui.theme.MNRaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MNRaderTheme {
                val navController = rememberNavController()
                var currentTab by remember { mutableStateOf(MainTab.HOME) }
                val navBackStack by
                    navController.currentBackStackEntryAsState()
                val currentDestination = navBackStack?.destination?.route
                var navigationBarVisible = remember(currentDestination) {
                    derivedStateOf {
                        MainTab.entries.any { tab ->
                            tab.route == currentDestination
                        }
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(
                            modifier = Modifier,
                            visible = navigationBarVisible.value,
                            currentTab = currentTab,
                            tabs = MainTab.entries,
                            onTabSelected = { tab ->
                                currentTab = tab
                                navController.navigate(currentTab.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) { innerPadding ->

                    MainNavHost(
                        navController = navController,
                        padding = innerPadding,
                    )
                }
            }
        }
    }
}
