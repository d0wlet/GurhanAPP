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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.background

class MainActivity : ComponentActivity() {
    private lateinit var preferenceManager: com.gurhan.util.PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = com.gurhan.util.PreferenceManager(this)
        
        // Enable edge-to-edge display
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            val darkMode by preferenceManager.darkModeFlow.collectAsState()
            val isDark = when (darkMode) {
                1 -> false // Light
                2 -> true  // Dark
                else -> androidx.compose.foundation.isSystemInDarkTheme()
            }
            
            // Keep Screen On logic
            val keepScreenOn by preferenceManager.keepScreenOnFlow.collectAsState()
            androidx.compose.runtime.DisposableEffect(keepScreenOn) {
                if (keepScreenOn) {
                    window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                } else {
                    window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
                onDispose {}
            }

            GurhanTheme(darkTheme = isDark) {
                GlobalCrashGuard {
                    MainScreen(preferenceManager)
                }
            }
        }
    }
}

@Composable
fun GlobalCrashGuard(content: @Composable () -> Unit) {
    var error by remember { mutableStateOf<Throwable?>(null) }

    if (error != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1A1A1A))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "⚠️ Uygulama Hatasy",
                    color = Color.Red,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Bagyşlaň, garaşylmadyk bir ýalňyşlyk ýüze çykdy. Bu maglumaty ant-gravity d0wlet-e iberiň:",
                    color = Color.White,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black)
                        .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = error?.stackTraceToString() ?: "Bilinmeýän ýalňyşlyk",
                        color = Color(0xFF00FF00),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { error = null },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                ) {
                    Text("Tazeden synanyş (Reset)")
                }
            }
        }
    } else {
        try {
            content()
        } catch (t: Throwable) {
            error = t
        }
    }
}

@Composable
fun MainScreen(preferenceManager: com.gurhan.util.PreferenceManager) {
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
                    preferenceManager = preferenceManager,
                    onSurahClick = { surah ->
                        navController.navigate("surah/${surah.id}")
                    },
                    onSettingsClick = {
                        navController.navigate("settings")
                    },
                    onVerseClick = { surah, verse ->
                        navController.navigate("surah/${surah.id}?verseId=${verse.verseNumber}")
                    },
                    onCalendarClick = {
                        navController.navigate("calendar")
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
                SettingsScreen(preferenceManager)
            }
            
            // Calendar Screen
            composable("calendar") {
                com.gurhan.ui.screens.CalendarScreen(
                    onBackClick = { navController.popBackStack() }
                )
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
