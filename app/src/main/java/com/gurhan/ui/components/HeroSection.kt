package com.gurhan.ui.components

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurhan.data.model.Surah
import com.gurhan.data.model.Verse
import com.gurhan.ui.animations.AnimationSpecs
import com.gurhan.ui.icons.CustomIcons

/**
 * Modern Hero Section with animated gradient and verse of the day
 */
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
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF0D9488),
                        Color(0xFF14B8A6),
                        Color(0xFF2DD4BF)
                    )
                ),
                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Date badge with glassmorphic effect
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White.copy(alpha = 0.25f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Günün Aýaty",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                // Settings button
                val view = LocalView.current
                IconButton(
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        onSettingsClick()
                    }
                ) {
                    Icon(
                        imageVector = CustomIcons.Settings,
                        contentDescription = "Sazlamalar",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            // Verse of the Day Card
            if (verseOfTheDay != null) {
                val (surah, verse) = verseOfTheDay
                VerseCard(
                    surah = surah,
                    verse = verse,
                    onClick = { onVerseClick(surah, verse) }
                )
            }
        }
    }
}

@Composable
private fun VerseCard(
    surah: Surah,
    verse: Verse,
    onClick: () -> Unit
) {
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = AnimationSpecs.fastSpring(),
        label = "verseCardScale"
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = androidx.compose.material.ripple.rememberRipple()
                ) {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    onClick()
                }
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = CustomIcons.Quran,
                        contentDescription = null,
                        tint = Color(0xFF0D9488),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "GÜNÜN AÝATY",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D9488),
                        letterSpacing = 1.2.sp
                    )
                }
                
                Text(
                    text = "${surah.name} ${verse.verseNumber}",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B),
                    fontWeight = FontWeight.Medium
                )
            }
            
            // Arabic text (if available)
            if (verse.arabicText.isNotBlank()) {
                Text(
                    text = verse.arabicText,
                    fontSize = 22.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.End,
                    lineHeight = 36.sp,
                    color = Color(0xFF0F172A),
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif
                )
            }
            
            // Translation
            Text(
                text = verse.turkmenTranslation,
                fontSize = 15.sp,
                color = Color(0xFF475569),
                lineHeight = 24.sp,
                maxLines = 4,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            
            // Read more button
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF0D9488).copy(alpha = 0.1f)
            ) {
                Box(
                    modifier = Modifier.padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Doly Oka →",
                        color = Color(0xFF0D9488),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
