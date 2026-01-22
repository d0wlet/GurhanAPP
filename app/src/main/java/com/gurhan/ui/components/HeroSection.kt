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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurhan.R
import com.gurhan.ui.theme.*

@Composable
fun HeroSection(
    verseOfTheDay: Pair<com.gurhan.data.model.Surah, com.gurhan.data.model.Verse>?
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
                        PrimaryGreenDark,
                        PrimaryGreen
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
                text = "Günün Aýaty",
                style = MaterialTheme.typography.titleMedium,
                color = AccentGold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Arabic Text
            Text(
                text = verse.turkmenTranslation,
                // Model Verse(surahId, verseNumber, turkmenTranslation, arabicText)
                // in code I see: verse.text is Turkmen? 
                // Let's check model definition or usage.
                // In parse_v5/v6: text is Turkmen. arabicText is "" for now (file didn't have it).
                // Wait, parse_v6 left arabicText empty.
                // And quran_final_linear.txt didn't have arabic.
                // User said "tek tek çek pdf den". PDF *had* arabic. But text file doesn't?
                // Text file snippet: "1. Süýnen...". Just Turkmen.
                // So Arabic is MISSING in text source.
                // I will use Turkmen text only for now.
                // Or try to use 'text' as Arabic if I was wrong.
                // In json_to_sqlite log: surah['verses'] has 'text' and 'arabicText'. 
                // parse_v6 sets arabicText = "".
                // So I only have Turkmen.
                // I will display Turkmen prominently.
                // text = verse.text
                style = MaterialTheme.typography.headlineMedium,
                color = TextOnPrimary,
                fontSize = 20.sp, 
                fontWeight = FontWeight.SemiBold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${surah.name}, ${verse.verseNumber}-nji aýat",
                style = MaterialTheme.typography.bodyMedium,
                color = TextOnPrimary.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }
    }
}
