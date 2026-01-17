package com.gurhan.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurhan.data.model.Surah
import com.gurhan.ui.theme.TealPrimary
import com.gurhan.ui.theme.TextPrimary
import com.gurhan.ui.theme.TextSecondary
import com.gurhan.ui.theme.DividerColor

@Composable
fun SurahCard(
    surah: Surah,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // iOS Style List Item: Minimalist, clean, separated by divider
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Minimalist Number
                Text(
                    text = surah.id.toString(),
                    fontSize = 17.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.width(28.dp)
                )

                // Info
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = surah.name,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = surah.revelationType.uppercase(),
                            fontSize = 11.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.5.sp
                        )
                        Box(
                            modifier = Modifier
                                .size(3.dp)
                                .clip(androidx.compose.foundation.shape.CircleShape)
                                .background(TextSecondary.copy(alpha=0.5f))
                        )
                        Text(
                            text = "${surah.versesCount} AÝAT",
                            fontSize = 11.sp,
                            color = TextSecondary,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
            
            // Arabic Name (Calligraphic)
            Text(
                text = surah.arabicName,
                fontSize = 22.sp,
                color = TealPrimary, // Branding touch
                fontWeight = FontWeight.Normal,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Serif // More traditional feel for Arabic
            )
        }
        
        // Essential iOS element: The Divider
        Divider(
            modifier = Modifier.padding(start = 64.dp), // Indented divider
            color = DividerColor,
            thickness = 0.5.dp
        )
    }
}
