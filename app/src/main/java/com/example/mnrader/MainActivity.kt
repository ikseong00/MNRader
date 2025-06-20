package com.example.mnrader

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mnrader.navigation.MainNavHost
import com.example.mnrader.navigation.MainTab
import com.example.mnrader.navigation.NavigationBar
import com.example.mnrader.navigation.Routes
import com.example.mnrader.ui.theme.MNRaderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            MNRaderTheme {
                val navController = rememberNavController()
                var currentTab by remember { mutableStateOf(MainTab.HOME) }
                val navBackStack by
                navController.currentBackStackEntryAsState()
                val currentDestination = navBackStack?.destination?.route

                var isLoggedIn by rememberSaveable { mutableStateOf(false) }
                
                // FCM 알림을 통해 앱이 열렸을 때 처리
                LaunchedEffect(Unit) {
                    handleFCMNotificationIntent(intent, navController)
                }

                // 현재 라우트 기반으로 currentTab을 자동 업데이트
                currentDestination?.let { route ->
                    MainTab.entries.find { it.route == route }?.let {
                        if (currentTab != it) currentTab = it
                    }
                }

                val navigationBarVisible = remember(currentDestination, isLoggedIn) {
                    derivedStateOf {
                        currentDestination == MainTab.HOME.route ||
                            currentDestination == MainTab.MY.route
                    }
                }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.systemBars.asPaddingValues()),
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
                        onLoginSuccess = {
                            isLoggedIn = true
                            navController.navigate(MainTab.HOME.route) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
    
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        // 앱이 이미 실행 중일 때 새로운 알림을 받은 경우 처리
        // TODO: 필요시 navController에 접근하여 화면 이동 구현
    }
    
    private fun handleFCMNotificationIntent(intent: Intent?, navController: androidx.navigation.NavController) {
        intent?.let { notificationIntent ->
            val animalId = notificationIntent.getStringExtra("animalId")
            val navigateTo = notificationIntent.getStringExtra("navigateTo")
            val notificationType = notificationIntent.getStringExtra("notificationType")
            
            Log.d("MainActivity", "FCM 알림 처리: animalId=$animalId, navigateTo=$navigateTo, type=$notificationType")
            
            // 특정 동물 상세 화면으로 이동
            if (animalId != null && navigateTo == "animalDetail") {
                // TODO: 로그인 상태 확인 후 해당 동물 상세 화면으로 이동
                Log.d("MainActivity", "동물 상세 화면으로 이동: $animalId")
            }
        }
    }
}
