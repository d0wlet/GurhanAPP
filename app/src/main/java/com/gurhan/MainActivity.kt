package com.gurhan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gurhan.ui.components.BottomNavigationBar
import com.gurhan.ui.navigation.BottomNavItem
import com.gurhan.ui.screens.HomeScreen
import com.gurhan.ui.screens.SettingsScreen
import com.gurhan.ui.screens.SurahDetailScreen
import com.gurhan.ui.theme.GurhanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GurhanTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Baş Sahypa",
            route = "home",
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            name = "Bellikler",
            route = "bookmarks",
            icon = Icons.Default.Star
        )
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavItems,
                navController = navController,
                onItemClick = { item ->
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen(
                    onSurahClick = { surah ->
                        navController.navigate("surah/${surah.id}")
                    },
                    onSettingsClick = {
                        navController.navigate("settings")
                    },
                    onVerseClick = { surah, verse ->
                        navController.navigate("surah/${surah.id}?verseId=${verse.verseNumber}")
                    }
                )
            }
            composable("settings") {
                SettingsScreen()
            }
            composable(
                route = "surah/{surahId}?verseId={verseId}",
                arguments = listOf(
                    androidx.navigation.navArgument("surahId") {
                        type = androidx.navigation.NavType.IntType
                    },
                    androidx.navigation.navArgument("verseId") {
                        type = androidx.navigation.NavType.IntType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                val surahId = backStackEntry.arguments?.getInt("surahId") ?: return@composable
                val verseId = backStackEntry.arguments?.getInt("verseId") ?: -1
                SurahDetailScreen(
                    surahId = surahId,
                    initialVerseId = verseId,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
