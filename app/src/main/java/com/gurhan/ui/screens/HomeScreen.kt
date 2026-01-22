package com.gurhan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
                    onContinueClick = {
                        // Logic to resume reading
                    }
                )
            }
            
            // Spacer
            item {
                Spacer(modifier = Modifier.height(24.dp))
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
