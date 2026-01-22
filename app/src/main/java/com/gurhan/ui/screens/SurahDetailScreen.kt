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
    val prefs = remember { com.gurhan.util.PreferenceManager(context) }
    val fontSizeScale = remember { prefs.getFontSize() }
    
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
                        Icon(Icons.Default.ArrowBack, contentDescription = "Yza")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PrimaryGreen),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp)
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

            // Verses (Book Mode)
            items(verses) { verse ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp) // Minimal spacing
                ) {
                    // Verse Number & Text inline for flow, or slight separation
                    Row(modifier = Modifier.fillMaxWidth()) {
                          Text(
                            text = "${verse.verseNumber}. ${verse.turkmenTranslation}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = (18 * fontSizeScale).sp,
                            lineHeight = (28 * fontSizeScale).sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f)
                        )
                    }
                    
                    // If Arabic text exists (future proofing), show it
                    if (verse.arabicText.isNotBlank()) {
                          Text(
                            text = verse.arabicText,
                            style = MaterialTheme.typography.headlineMedium,
                            fontSize = (24 * fontSizeScale).sp,
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                            textAlign = androidx.compose.ui.text.style.TextAlign.End,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                // Optional: Thin separator
                Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f), thickness = 0.5.dp)
            }
        }
    }
}
