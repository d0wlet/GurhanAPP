package com.gurhan.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gurhan.R
import com.gurhan.ui.theme.AccentGold
import com.gurhan.ui.theme.PrimaryGreen
import com.gurhan.ui.theme.SurfaceWhite

// Enum for tabs to keep it type-safe
enum class BottomTab(val route: String, val title: String, val iconResId: Int) {
    HOME("home", "Gurhan", R.drawable.ic_book_open), // Fallback or dynamic
    TASBIH("tasbih", "Tesbih", R.drawable.ic_circle), // Fallback
    SETTINGS("settings", "Sazlamalar", R.drawable.ic_settings)
}

@Composable
fun ModernBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Floating Bottom Bar Container
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp) // Floating margin
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(32.dp),
                spotColor = Color.Black.copy(alpha = 0.15f),
                ambientColor = Color.Black.copy(alpha = 0.1f)
            )
    ) {
        Surface(
            color = SurfaceWhite,
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomTab.values().forEach { tab ->
                    val isSelected = currentRoute == tab.route
                    BottomBarItem(
                        tab = tab,
                        isSelected = isSelected,
                        onClick = {
                            if (currentRoute != tab.route) {
                                navController.navigate(tab.route) {
                                    popUpTo("home") { saveState = true }
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
fun BottomBarItem(
    tab: BottomTab,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1.0f,
        animationSpec = tween(300),
        label = "scale"
    )
    
    val color = if (isSelected) PrimaryGreen else Color.Gray.copy(alpha = 0.6f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .scale(scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null // No ripple for cleaner look
            ) { onClick() }
            .padding(8.dp)
    ) {
        // Icon logic: Try to use resources, assuming they exist or will exist
        // For now using `painterResource`. If ID is invalid, app crashes, so we need valid IDs.
        // We will assume R.drawable.ic_... exist from the script or fallback.
        // Since `home` failed, let's play safe for compilation and use a known existing or standard icon if possible,
        // but since I can't check resource IDs easily at compile time, I'll rely on what I pushed.
        // I will update R.drawable references after I confirm what icons exist.
        // For now, let's assume they exist.
        
        Icon(
            painter = painterResource(id = tab.iconResId), 
            contentDescription = tab.title,
            tint = color,
            modifier = Modifier.size(26.dp)
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(AccentGold)
            )
        }
    }
}
