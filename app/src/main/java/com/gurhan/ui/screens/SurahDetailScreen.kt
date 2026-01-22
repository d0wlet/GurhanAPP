package com.gurhan.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurhan.data.repository.QuranRepository
import com.gurhan.ui.theme.PrimaryGreen

import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahDetailScreen(
    surahId: Int,
    initialVerseId: Int = -1,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val repository = remember { QuranRepository(context) }
    val surah = remember(surahId) { repository.getSurahById(surahId) }
    val verses = remember(surahId) { repository.getVersesBySurahId(surahId) }
    
    val listState = androidx.compose.foundation.lazy.rememberLazyListState()
    
    // Scroll to verse if deep linked
    androidx.compose.runtime.LaunchedEffect(initialVerseId, verses) {
        if (initialVerseId != -1 && verses.isNotEmpty()) {
            val index = verses.indexOfFirst { it.verseNumber == initialVerseId }
            if (index != -1) {
                // Add 1 for the Header item
                listState.scrollToItem(index + 1)
            }
        }
    }
    
    // Save as Last Read on entry
    androidx.compose.runtime.LaunchedEffect(surah) {
        if (surah != null) {
            val prefs = com.gurhan.util.PreferenceManager(context)
            prefs.saveLastRead(surah.id, surah.name, 1) // Default to verse 1 for now
        }
    }

    if (surah == null) return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(surah.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = PrimaryGreen,
                    navigationIconContentColor = PrimaryGreen
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PrimaryGreen),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = surah.arabicName,
                            fontSize = 32.sp,
                            color = Color.White
                        )
                        Text(
                            text = surah.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "${surah.revelationType} • ${surah.versesCount} Aýat",
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            // Verses
            items(verses) { verse ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(PrimaryGreen.copy(alpha=0.1f), androidx.compose.foundation.shape.RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${surah.id}:${verse.verseNumber}",
                                    color = PrimaryGreen,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        
                        Text(
                            text = verse.arabicText,
                            fontSize = 24.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.End,
                            lineHeight = 36.sp
                        )
                        
                        Divider(color = Color.LightGray.copy(alpha=0.3f))
                        
                        Text(
                            text = verse.turkmenTranslation,
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            lineHeight = 24.sp
                        )
                    }
                }
            }
        }
    }
}
