package com.gurhan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gurhan.data.model.Surah
import com.gurhan.data.model.Verse
import com.gurhan.ui.components.HeroSection
import com.gurhan.ui.components.SurahCard
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.shadow
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.shape.RoundedCornerShape
import com.gurhan.ui.theme.*
import com.gurhan.viewmodel.QuranViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search

@Composable
fun HomeScreen(
    preferenceManager: com.gurhan.util.PreferenceManager,
    viewModel: QuranViewModel = viewModel(),
    onSurahClick: (Surah) -> Unit,
    onSettingsClick: () -> Unit,
    onVerseClick: (Surah, Verse) -> Unit
) {
    val surahs by viewModel.surahs.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val verseOfTheDay by viewModel.verseOfTheDay.collectAsState()
    val fontSizeScale = remember { preferenceManager.getFontSize() }
    
    // Search State
    var searchQuery by remember { mutableStateOf("") }
    
    // Parallax Scroll State
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
    
        // Parallax Header (Behind List)
        val headerHeight = 320.dp
        val headerHeightPx = with(androidx.compose.ui.platform.LocalDensity.current) { headerHeight.toPx() }
        
        val firstItemTranslationY by remember {
            derivedStateOf {
                if (listState.firstVisibleItemIndex == 0) {
                    listState.firstVisibleItemScrollOffset * 0.5f // Parallax factor
                } else {
                    0f // Hidden
                }
            }
        }
        
        val headerAlpha by remember {
            derivedStateOf {
                 if (listState.firstVisibleItemIndex == 0) {
                    val progress = listState.firstVisibleItemScrollOffset / headerHeightPx
                    (1f - progress * 1.5f).coerceIn(0f, 1f)
                } else {
                    0f
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .graphicsLayer {
                    alpha = headerAlpha
                    translationY = -firstItemTranslationY
                }
                .align(Alignment.TopCenter)
        ) {
            HeroSection(verseOfTheDay, fontSizeScale)
        }

        // Foreground List
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = listState,
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
             // Spacer for Header Visibility
            item {
                Spacer(modifier = Modifier.height(headerHeight - 20.dp))
            }
            
            // Search Bar
            item {
                androidx.compose.material3.OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { 
                        searchQuery = it
                        viewModel.onSearch(it)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 16.dp)
                        .shadow(8.dp, RoundedCornerShape(12.dp)),
                    placeholder = { Text("Sure gözle...") },
                    leadingIcon = { 
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Default.Search, 
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    singleLine = true
                )
            }
            
            if (isLoading) {
                item {
                    Box(modifier = Modifier.fillParentMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
            } else {
                items(surahs) { surah ->
                    SurahCard(
                        surah = surah,
                        onClick = { onSurahClick(surah) },
                        fontSizeScale = fontSizeScale,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}
