package com.gurhan.ui.components

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurhan.data.model.Surah
import com.gurhan.ui.animations.AnimationSpecs

@Composable
fun SurahCard(
    surah: Surah,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    // Smooth scale animation
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = AnimationSpecs.fastSpring(),
        label = "cardScale"
    )
    
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .scale(scale)
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 4.dp,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .clickable(
                    interactionSource = interactionSource,
                    indication = androidx.compose.material.ripple.rememberRipple(
                        color = Color(0xFF0D9488)
                    )
                ) {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    onClick()
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Number badge with gradient
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF0D9488).copy(alpha = 0.1f),
                                    Color(0xFF14B8A6).copy(alpha = 0.15f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = surah.id.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D9488)
                    )
                }
                
                // Surah info
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = surah.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF0F172A)
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = surah.revelationType,
                            fontSize = 13.sp,
                            color = Color(0xFF64748B)
                        )
                        
                        Box(
                            modifier = Modifier
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
