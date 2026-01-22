package com.gurhan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurhan.R
import com.gurhan.ui.theme.*

@Composable
fun HeroSection(
    verseOfTheDay: Pair<com.gurhan.data.model.Surah, com.gurhan.data.model.Verse>?,
    fontSizeScale: Float = 1.0f
) {
    if (verseOfTheDay == null) return

    val (surah, verse) = verseOfTheDay

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp) // Taller for verse text
            .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary, 
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .padding(top = 40.dp), // System bar padding allowance
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
            Text(
                text = "Günüň Aýaty",
                style = MaterialTheme.typography.titleMedium,
                color = AccentGoldLight,
                fontSize = (16 * fontSizeScale).sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Turkmen Translation
            Text(
                text = verse.turkmenTranslation,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = (20 * fontSizeScale).sp, 
                fontWeight = FontWeight.SemiBold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${surah.name}, ${verse.verseNumber}-nji aýat",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                fontSize = (14 * fontSizeScale).sp
            )
        }
    }
}
