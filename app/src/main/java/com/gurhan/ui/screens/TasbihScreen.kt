package com.gurhan.ui.screens

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurhan.ui.animations.AnimationSpecs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Tasbih (Prayer Counter) Screen
 * Beautiful, interactive counter with haptic feedback and animations
 */
@Composable
fun TasbihScreen() {
    var count by remember { mutableStateOf(0) }
    var targetCount by remember { mutableStateOf(33) }
    var isPressed by remember { mutableStateOf(false) }
    
    val haptic = LocalHapticFeedback.current
    val view = LocalView.current
    val scope = rememberCoroutineScope()
    
    // Animation for press effect
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = AnimationSpecs.fastSpring(),
        label = "scale"
    )
    
    // Ripple animation
    var rippleAlpha by remember { mutableStateOf(0f) }
    val rippleAnimatable = remember { Animatable(0f) }
    
    // Progress animation
    val progress by animateFloatAsState(
        targetValue = (count.toFloat() / targetCount.toFloat()).coerceIn(0f, 1f),
        animationSpec = AnimationSpecs.smoothSpring(),
        label = "progress"
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF0FDFA),
                        Color(0xFFCCFBF1)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Text(
                    text = "Tesbih",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D9488)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Subhanallah • Alhamdulillah • Allahu Akbar",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center
                )
            }
            
            // Counter Circle
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 48.dp)
            ) {
                // Progress ring
                Canvas(
                    modifier = Modifier.size(280.dp)
                ) {
                    val strokeWidth = 12.dp.toPx()
                    val radius = (size.minDimension - strokeWidth) / 2
                    val center = Offset(size.width / 2, size.height / 2)
                    
                    // Background ring
                    drawCircle(
                        color = Color(0xFFE0F2FE),
                        radius = radius,
                        center = center,
                        style = Stroke(width = strokeWidth)
                    )
                    
                    // Progress ring
                    drawArc(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                Color(0xFF0D9488),
                                Color(0xFF14B8A6),
                                Color(0xFF2DD4BF)
                            )
                        ),
                        startAngle = -90f,
                        sweepAngle = 360f * progress,
                        useCenter = false,
                        style = Stroke(width = strokeWidth),
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                        size = androidx.compose.ui.geometry.Size(
                            size.width - strokeWidth,
                            size.height - strokeWidth
                        )
                    )
                    
                    // Ripple effect
                    if (rippleAlpha > 0f) {
                        drawCircle(
                            color = Color(0xFF0D9488).copy(alpha = rippleAlpha),
                            radius = radius * rippleAnimatable.value,
                            center = center,
                            style = Stroke(width = strokeWidth * (1 - rippleAnimatable.value))
                        )
                    }
                }
                
                // Counter button
                Surface(
                    modifier = Modifier
                        .size(240.dp)
                        .scale(scale)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isPressed = true
                                    
                                    // Haptic feedback
                                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                                    
                                    // Ripple animation
                                    scope.launch {
                                        rippleAlpha = 0.5f
                                        rippleAnimatable.snapTo(0f)
                                        launch {
                                            rippleAnimatable.animateTo(
                                                1.5f,
                                                animationSpec = tween(600, easing = FastOutSlowInEasing)
                                            )
                                        }
                                        launch {
                                            for (i in 0..20) {
                                                delay(30)
                                                rippleAlpha *= 0.9f
                                            }
                                            rippleAlpha = 0f
                                        }
                                    }
                                    
                                    tryAwaitRelease()
                                    isPressed = false
                                },
                                onTap = {
                                    if (count < targetCount) {
                                        count++
                                        
                                        // Special haptic for milestones
                                        if (count == targetCount) {
                                            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                                        }
                                    } else {
                                        // Reached target, vibrate
                                        view.performHapticFeedback(HapticFeedbackConstants.REJECT)
                                    }
                                }
                            )
                        },
                    shape = CircleShape,
                    color = Color.White,
                    shadowElevation = 16.dp
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = count.toString(),
                                fontSize = 72.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0D9488)
                            )
                            
                            Text(
                                text = "/ $targetCount",
                                fontSize = 20.sp,
                                color = Color(0xFF94A3B8)
                            )
                            
                            if (count >= targetCount) {
                                Text(
                                    text = "✓ Tamamlandı",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF10B981)
                                )
                            }
                        }
                    }
                }
            }
            
            // Controls
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                // Target selector
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf(33, 99, 100).forEach { target ->
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = if (targetCount == target) Color(0xFF0D9488) else Color.White,
                            shadowElevation = if (targetCount == target) 8.dp else 2.dp,
                            onClick = {
                                targetCount = target
                                if (count > target) count = 0
                                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            }
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = target.toString(),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (targetCount == target) Color.White else Color(0xFF64748B)
                                )
                            }
                        }
                    }
                }
                
                // Reset button
                Button(
                    onClick = {
                        count = 0
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF0D9488)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Täzeden Başla",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
