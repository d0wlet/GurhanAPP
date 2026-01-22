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
    lastReadSurah: String = "Fatiha Süresi",
    lastReadVerse: Int = 1,
    onContinueClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp) // Taller header for impact
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
                .padding(top = 24.dp), // System bar padding allowance
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header: Greeting & Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Esselamu Aleyküm",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextOnPrimary.copy(alpha = 0.8f),
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Musulman Dogan", // Placeholder User Name
                        style = MaterialTheme.typography.headlineMedium,
                        color = TextOnPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                // Hijri Date Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "1446 AH",
                        color = TextOnPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            // Last Read Card (Floating inside Hero)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.15f))
                    .clickable { onContinueClick() }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Soňky Okalan",
                        color = TextOnPrimary.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$lastReadSurah, Aýat $lastReadVerse",
                        color = TextOnPrimary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                }
                
                // Play/Arrow Icon
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(AccentGold),
                    contentAlignment = Alignment.Center
                ) {
                   // Using Material Icon fallback since script might not have fetched 'play' perfectly or ID is unknown
                   Icon(
                       imageVector = androidx.compose.material.icons.Icons.Filled.PlayArrow,
                       contentDescription = "Continue",
                       tint = Color.White
                   )
                }
            }
        }
    }
}
