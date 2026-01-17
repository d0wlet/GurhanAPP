package com.gurhan.ui.screens

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

@Composable
fun HomeScreen() {
    val repository = remember { QuranRepository() }
    var searchQuery by remember { mutableStateOf("") }
    val allSurahs = remember { repository.getAllSurahs() }
    val filteredSurahs = remember(searchQuery) {
        if (searchQuery.isEmpty()) allSurahs
        else repository.searchSurahs(searchQuery)
    }
    
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
            
            // Surah List
            items(filteredSurahs) { surah ->
                SurahCard(
                    surah = surah,
                    onClick = { /* Navigate to detail */ },
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
