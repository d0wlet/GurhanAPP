package com.gurhan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurhan.R
import com.gurhan.ui.theme.*
import java.time.chrono.HijrahDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HeroSection(
    verseOfTheDay: Pair<com.gurhan.data.model.Surah, com.gurhan.data.model.Verse>?,
    fontSizeScale: Float = 1.0f,
    onCalendarClick: () -> Unit
) {
    // Get current Hijri date (always needed)
    val hijriDate = remember {
        val today = java.time.LocalDate.now()
        val hDate = HijrahDate.from(today)
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale("tr"))
        hDate.format(formatter)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp)
            .clip(RoundedCornerShape(bottomStart = 48.dp, bottomEnd = 48.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                )
            )
    ) {
        // Decorative background element
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = (-40).dp, y = (-20).dp)
                .background(Color.White.copy(alpha = 0.05f), CircleShape)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Row with Hijri Date Card (Always interactive)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Hijri Date in a small transparent card
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onCalendarClick() }
                        .background(Color.White.copy(alpha = 0.15f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_quran_rehal),
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.8f),
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = hijriDate,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                
                Icon(
                    painter = painterResource(id = com.gurhan.R.drawable.ic_quran_rehal),
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Glassmorphism Card for Verse of the Day
            if (verseOfTheDay != null) {
                val (surah, verse) = verseOfTheDay
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .padding(20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_quran_rehal),
                                contentDescription = null,
                                tint = AccentGoldLight,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "Günüň Aýaty",
                                style = MaterialTheme.typography.labelLarge,
                                color = AccentGoldLight,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        // Turkmen Translation
                        Text(
                            text = verse.turkmenTranslation,
                            color = Color.White,
                            fontSize = (18 * fontSizeScale).sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            maxLines = 4,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleLarge.copy(lineHeight = 26.sp)
                        )

                        Text(
                            text = "— ${surah.name}, ${verse.verseNumber} —",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Light,
                            fontSize = (13 * fontSizeScale).sp
                        )
                    }
                }
            } else {
                // Loading state for verse
                CircularProgressIndicator(color = Color.White.copy(alpha = 0.5f))
            }
            
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
