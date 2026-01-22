package com.gurhan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gurhan.ui.animations.AnimationSpecs
import com.gurhan.ui.components.ModernBottomBar
import com.gurhan.ui.screens.HomeScreen
import com.gurhan.ui.screens.SettingsScreen
import com.gurhan.ui.screens.SurahDetailScreen
import com.gurhan.ui.screens.TasbihScreen
import com.gurhan.ui.theme.GurhanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
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

    Scaffold(
        bottomBar = {
            ModernBottomBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues),
            enterTransition = { AnimationSpecs.pageEnterTransition },
            exitTransition = { AnimationSpecs.pageExitTransition },
            popEnterTransition = { AnimationSpecs.pagePopEnterTransition },
            popExitTransition = { AnimationSpecs.pagePopExitTransition }
        ) {
            // Home Screen
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
            
            // Tasbih Screen
            composable("tasbih") {
                TasbihScreen()
            }
            
            // Bookmarks Screen (placeholder for now)
            composable("bookmarks") {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        text = "Bellikler\nTez wagtda goşular",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Settings Screen
            composable("settings") {
                SettingsScreen()
            }
            
            // Surah Detail Screen
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
