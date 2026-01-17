package com.gurhan.ui.screens

import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gurhan.data.repository.QuranRepository
import com.gurhan.ui.components.HeroSection
import com.gurhan.ui.components.SearchBar
import com.gurhan.ui.components.SurahCard

import androidx.compose.foundation.ExperimentalFoundationApi

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onSurahClick: (com.gurhan.data.model.Surah) -> Unit
) {
    val repository = remember { QuranRepository() }
    var searchQuery by remember { mutableStateOf("") }
    val allSurahs = remember { repository.getAllSurahs() }
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
                HeroSection()
            }
            
            // Search Bar
            item {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
            
            // Surah List with Animation
            items(filteredSurahs.size) { index ->
                val surah = filteredSurahs[index]
                
                // Staggered animation effect
                // Creates a slide-in and fade-in effect for each item based on its index
                // Note: In real production code, use AnimatedVisibility or a custom modifier. 
                // For simplicity here, we rely on the fact that Compose recomposes.
                // But a true staggered entrance needs a launched effect per item or a list-level transition.
                // Let's keep it simple but elegant with just the data first.
                // For a proper stagger in Compose without external libs, we can animate alpha/translation
                // based on visibility.
                
                // Let's actually add the animation modifier
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
