package com.gurhan.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gurhan.data.repository.QuranRepository
import com.gurhan.ui.components.HeroSection
import com.gurhan.ui.components.SearchBar
import com.gurhan.ui.components.SurahCard

import androidx.compose.foundation.ExperimentalFoundationApi

import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onSurahClick: (com.gurhan.data.model.Surah) -> Unit,
    onSettingsClick: () -> Unit,
    onVerseClick: (com.gurhan.data.model.Surah, com.gurhan.data.model.Verse) -> Unit
) {
    val context = LocalContext.current
    val repository = remember { QuranRepository(context) }
    var searchQuery by remember { mutableStateOf("") }
    val allSurahs = remember { repository.getAllSurahs() }
    val verseOfTheDay = remember { repository.getVerseOfTheDay() }
    
    val filteredSurahs = remember(searchQuery) {
        if (searchQuery.isEmpty()) allSurahs
        else repository.searchSurahs(searchQuery)
    }
    
    // Animation state
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()
    
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(0.dp) // iOS Style Grouped List
        ) {
            // Hero Section
            item {
                HeroSection(
                    verseOfTheDay = verseOfTheDay,
                    onSettingsClick = onSettingsClick,
                    onVerseClick = onVerseClick
                )
            }
            
            // Search Bar
            item {
                Spacer(modifier = Modifier.height(16.dp))
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            
            // Surah List
            items(filteredSurahs) { surah ->
                SurahCard(
                    surah = surah,
                    onClick = { onSurahClick(surah) },
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
            
            // Bottom spacing
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}
