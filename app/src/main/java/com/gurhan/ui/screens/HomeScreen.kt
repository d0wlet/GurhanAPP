package com.gurhan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gurhan.data.model.Surah
import com.gurhan.data.model.Verse
import com.gurhan.ui.components.HeroSection
import com.gurhan.ui.components.SurahCard
import com.gurhan.ui.theme.BackgroundCream
import com.gurhan.viewmodel.QuranViewModel

@Composable
fun HomeScreen(
    viewModel: QuranViewModel = viewModel(),
    onSurahClick: (Surah) -> Unit,
    onSettingsClick: () -> Unit,
    onVerseClick: (Surah, Verse) -> Unit
) {
    val surahs by viewModel.surahs.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    
    // Last Read State
    val lastReadSurah by viewModel.lastReadSurah.collectAsState()
    val lastReadVerse by viewModel.lastReadVerse.collectAsState()
    val lastReadSurahId by viewModel.lastReadSurahId.collectAsState()
    
    // Search State
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundCream) // Global background color
    ) {
        // Scrollable Content
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp) // Space for floating bottom bar
        ) {
            // Hero Section item
            item {
                HeroSection(
                    lastReadSurah = lastReadSurah,
                    lastReadVerse = lastReadVerse,
                    onContinueClick = {
                        // Resume reading: Navigate to last read Surah
                         // We need a way to navigate to specific verse, but for now just open the Surah
                         // Assuming we can pass surahId
                         // We need onSurahClick logic but for specific ID
                         // But onVerseClick signature is (Surah, Verse).
                         // Let's just use onSurahClick with a recreated Surah object or fetch it.
                         // Or better, expose onContinueClick to navigate using ID.
                         // Since navigation logic is in MainScreen usually, HomeScreen just calls callback.
                         // But callback is (Surah)->Unit.
                         // We can search surah from list?
                        val surah = surahs.find { it.id == lastReadSurahId } ?: surahs.firstOrNull()
                        if (surah != null) {
                            onSurahClick(surah)
                        }
                    }
                )
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
                        .padding(bottom = 16.dp),
                    placeholder = { Text("Sure gözle...") },
                    leadingIcon = { 
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.Search, 
                            contentDescription = "Search",
                            tint = com.gurhan.ui.theme.TextSecondary
                        )
                    },
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.TextFieldDefaults.colors(
                        focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                        unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
                        focusedIndicatorColor = com.gurhan.ui.theme.PrimaryGreen,
                        unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
                    ),
                    singleLine = true
                )
            }
            
            // Surah List
            if (isLoading) {
                item {
                    Box(modifier = Modifier.fillParentMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = com.gurhan.ui.theme.PrimaryGreen)
                    }
                }
            } else {
                items(surahs) { surah ->
                    SurahCard(
                        surah = surah,
                        onClick = { onSurahClick(surah) },
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}
