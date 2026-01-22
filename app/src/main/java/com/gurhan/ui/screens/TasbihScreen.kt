package com.gurhan.ui.screens

import android.view.HapticFeedbackConstants
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurhan.ui.theme.*
import kotlin.math.min

@Composable
fun TasbihScreen() {
    var count by remember { mutableStateOf(0) }
    var target by remember { mutableStateOf(33) }
    
    val view = LocalView.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SubhanAllah",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Main Counter Button
        TASBIHButton(
            count = count,
            target = target,
            onClick = {
                count++
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                if (count >= target) {
                     // Vibration for completion could go here
                }
            }
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ControlChip(text = "Tazelemek", onClick = { count = 0 })
            ControlChip(text = "33", isSelected = target == 33, onClick = { target = 33 })
            ControlChip(text = "99", isSelected = target == 99, onClick = { target = 99 })
        }
    }
}

@Composable
fun TASBIHButton(
    count: Int,
    target: Int,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100), label = "buttonScale"
    )
    
    val progress = min(count.toFloat() / target.toFloat(), 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(500), label = "progress"
    )

    Box(
        modifier = Modifier
            .size(280.dp)
            .scale(scale)
            .shadow(20.dp, CircleShape, spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // Progress Arc
        Canvas(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            drawArc(
                color = DividerColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
            )
            
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        PrimaryGreenLight, 
                        MaterialTheme.colorScheme.primary, 
                        PrimaryGreenDark, 
                        PrimaryGreenLight
                    )
                ),
                startAngle = -90f,
                sweepAngle = animatedProgress * 360f,
                useCenter = false,
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        
        // Inner Circle & Text
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "/ $target",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ControlChip(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
    }
}
