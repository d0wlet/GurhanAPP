package com.gurhan.ui.animations

import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition

/**
 * Telegram-quality animation specifications
 * Using spring physics for natural, fluid motion
 */
object AnimationSpecs {
    
    // Spring configurations (Telegram-style)
    private const val SPRING_STIFFNESS_LOW = 200f
    private const val SPRING_STIFFNESS_MEDIUM = 400f
    private const val SPRING_STIFFNESS_HIGH = 800f
    private const val SPRING_DAMPING_RATIO = 0.75f
    
    /**
     * Smooth spring animation for UI elements
     */
    fun <T> smoothSpring(
        stiffness: Float = SPRING_STIFFNESS_MEDIUM
    ): SpringSpec<T> = spring(
        dampingRatio = SPRING_DAMPING_RATIO,
        stiffness = stiffness
    )
    
    /**
     * Fast spring for quick interactions
     */
    fun <T> fastSpring(): SpringSpec<T> = spring(
        dampingRatio = SPRING_DAMPING_RATIO,
        stiffness = SPRING_STIFFNESS_HIGH
    )
    
    /**
     * Gentle spring for large movements
     */
    fun <T> gentleSpring(): SpringSpec<T> = spring(
        dampingRatio = SPRING_DAMPING_RATIO,
        stiffness = SPRING_STIFFNESS_LOW
    )
    
    /**
     * Tween animation for precise timing
     */
    fun <T> smoothTween(
        durationMillis: Int = 300,
        easing: Easing = FastOutSlowInEasing
    ): TweenSpec<T> = tween(
        durationMillis = durationMillis,
        easing = easing
    )
    
    /**
     * Page transition - slide from right (Telegram-style)
     */
    val pageEnterTransition: EnterTransition = slideInHorizontally(
        initialOffsetX = { it },
        animationSpec = smoothSpring()
    ) + fadeIn(animationSpec = tween(200))
    
    /**
     * Page transition - slide to left
     */
    val pageExitTransition: ExitTransition = slideOutHorizontally(
        targetOffsetX = { -it / 3 },
        animationSpec = smoothSpring()
    ) + fadeOut(animationSpec = tween(200))
    
    /**
     * Page pop - slide from left
     */
    val pagePopEnterTransition: EnterTransition = slideInHorizontally(
        initialOffsetX = { -it / 3 },
        animationSpec = smoothSpring()
    ) + fadeIn(animationSpec = tween(200))
    
    /**
     * Page pop - slide to right
     */
    val pagePopExitTransition: ExitTransition = slideOutHorizontally(
        targetOffsetX = { it },
        animationSpec = smoothSpring()
    ) + fadeOut(animationSpec = tween(200))
    
    /**
     * Scale animation for buttons and cards
     */
    val scaleInAnimation = scaleIn(
        initialScale = 0.92f,
        animationSpec = smoothSpring()
    ) + fadeIn(animationSpec = tween(150))
    
    val scaleOutAnimation = scaleOut(
        targetScale = 0.92f,
        animationSpec = smoothSpring()
    ) + fadeOut(animationSpec = tween(150))
    
    /**
     * Ripple effect duration
     */
    const val RIPPLE_DURATION = 300
    
    /**
     * Icon animation duration
     */
    const val ICON_ANIMATION_DURATION = 250
    
    /**
     * Navigation bar animation
     */
    const val NAV_BAR_ANIMATION_DURATION = 400
}
