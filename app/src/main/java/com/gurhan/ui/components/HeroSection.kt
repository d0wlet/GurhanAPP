package com.gurhan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurhan.data.model.Surah
import com.gurhan.data.model.Verse
import com.gurhan.ui.theme.TealGradientEnd
import com.gurhan.ui.theme.TealGradientStart

@Composable
fun HeroSection(
    verseOfTheDay: Pair<Surah, Verse>?,
    onSettingsClick: () -> Unit,
    onVerseClick: (Surah, Verse) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(TealGradientStart, TealGradientEnd)
                ),
                shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Row (Date and Settings)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Date badge
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.2f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        // Dynamic Date logic could be added here, hardcoded for now or use java.time
                        Text(
                            text = "Günün Aýaty",
                            color = Color.White,
                            fontSize = 13.sp
                        )
                    }
                }
                
                // Settings Icon
                IconButton(
                    onClick = onSettingsClick,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Sazlamalar"
                    )
                }
            }
            
            // Verse of the Day Card
            if (verseOfTheDay != null) {
                val (surah, verse) = verseOfTheDay
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "GÜNÜN AÝATY",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TealGradientStart,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "${surah.name} ${verse.verseNumber}",
                                fontSize = 11.sp,
                                color = Color.Gray,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                        
                        // Arabic text is empty in our current JSON, relying on Turkmen text
                        // Text(
                        //     text = verse.arabicText,
                        //     fontSize = 22.sp,
                        //     modifier = Modifier.fillMaxWidth(),
                        //     textAlign = androidx.compose.ui.text.style.TextAlign.End,
                        //     lineHeight = 36.sp
                        // )
                        
                        Text(
                            text = verse.turkmenTranslation,
                            fontSize = 15.sp,
                            color = Color.DarkGray,
                            lineHeight = 22.sp,
                            maxLines = 4,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                        
                        Button(
                            onClick = { onVerseClick(surah, verse) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.background
                            )
                        ) {
                            Text(
                                "Doly Oka →",
                                color = TealGradientStart,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

