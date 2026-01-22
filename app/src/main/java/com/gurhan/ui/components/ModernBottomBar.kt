package com.gurhan.ui.components

import android.view.HapticFeedbackConstants
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gurhan.ui.animations.AnimationSpecs
import com.gurhan.ui.icons.CustomIcons

/**
 * Modern glassmorphic navigation bar with Telegram-quality animations
 */
@Composable
fun ModernBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val items = remember {
        listOf(
            BottomNavItem("home", "Gurhan", CustomIcons.Quran),
            BottomNavItem("tasbih", "Tesbih", CustomIcons.Tasbih),
            BottomNavItem("bookmarks", "Bellikler", CustomIcons.BookmarkOutline),
            BottomNavItem("settings", "Sazlamalar", CustomIcons.Settings)
        )
    }
    
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    
    // Glassmorphic background
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        color = Color.White.copy(alpha = 0.95f),
        shadowElevation = 12.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.98f),
                            Color.White.copy(alpha = 0.95f)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val isSelected = currentRoute == item.route
                    
                    NavBarItem(
                        item = item,
                        isSelected = isSelected,
                        onClick = {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NavBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val view = LocalView.current
    
    // Animated properties
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.0f else 0.92f,
        animationSpec = AnimationSpecs.smoothSpring(),
        label = "scale"
    )
    
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF0D9488) else Color(0xFF94A3B8),
        animationSpec = AnimationSpecs.smoothTween(300),
        label = "iconColor"
    )
    
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF0D9488) else Color(0xFF64748B),
        animationSpec = AnimationSpecs.smoothTween(300),
        label = "textColor"
    )
    
    val indicatorWidth by animateDpAsState(
        targetValue = if (isSelected) 48.dp else 0.dp,
        animationSpec = AnimationSpecs.smoothSpring(),
        label = "indicatorWidth"
    )
    
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) Color(0xFF0D9488).copy(alpha = 0.1f) else Color.Transparent,
        animationSpec = AnimationSpecs.smoothTween(300),
        label = "backgroundColor"
    )
    
    Box(
        modifier = Modifier
            .scale(scale)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                // Haptic feedback
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                onClick()
            }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Text(
                text = item.label,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = textColor
            )
            
            // Active indicator
            if (indicatorWidth > 0.dp) {
                Box(
                    modifier = Modifier
                        .width(indicatorWidth)
                        .height(3.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0D9488))
                )
            }
        }
    }
}

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
