package com.gurhan.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Custom premium icon set for Gurhan app
 * Designed to be modern, clean, and professional
 */
object CustomIcons {
    
    /**
     * Quran icon - for home/reading section
     */
    val Quran: ImageVector
        get() = ImageVector.Builder(
            name = "Quran",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                // Book shape
                moveTo(6f, 2f)
                curveTo(4.9f, 2f, 4f, 2.9f, 4f, 4f)
                verticalLineTo(20f)
                curveTo(4f, 21.1f, 4.9f, 22f, 6f, 22f)
                horizontalLineTo(18f)
                curveTo(19.1f, 22f, 20f, 21.1f, 20f, 20f)
                verticalLineTo(4f)
                curveTo(20f, 2.9f, 19.1f, 2f, 18f, 2f)
                horizontalLineTo(6f)
                close()
                
                // Bookmark ribbon
                moveTo(12f, 4f)
                lineTo(14f, 7f)
                lineTo(16f, 4f)
                verticalLineTo(12f)
                lineTo(14f, 10f)
                lineTo(12f, 12f)
                verticalLineTo(4f)
                close()
                
                // Pages detail
                moveTo(7f, 14f)
                horizontalLineTo(17f)
                moveTo(7f, 17f)
                horizontalLineTo(15f)
            }
        }.build()
    
    /**
     * Tasbih icon - prayer counter
     */
    val Tasbih: ImageVector
        get() = ImageVector.Builder(
            name = "Tasbih",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null
            ) {
                // Main bead circle
                moveTo(12f, 14f)
                curveTo(13.66f, 14f, 15f, 12.66f, 15f, 11f)
                curveTo(15f, 9.34f, 13.66f, 8f, 12f, 8f)
                curveTo(10.34f, 8f, 9f, 9.34f, 9f, 11f)
                curveTo(9f, 12.66f, 10.34f, 14f, 12f, 14f)
                close()
                
                // Top beads
                moveTo(8f, 5f)
                curveTo(8.55f, 5f, 9f, 4.55f, 9f, 4f)
                curveTo(9f, 3.45f, 8.55f, 3f, 8f, 3f)
                curveTo(7.45f, 3f, 7f, 3.45f, 7f, 4f)
                curveTo(7f, 4.55f, 7.45f, 5f, 8f, 5f)
                close()
                
                moveTo(12f, 5f)
                curveTo(12.55f, 5f, 13f, 4.55f, 13f, 4f)
                curveTo(13f, 3.45f, 12.55f, 3f, 12f, 3f)
                curveTo(11.45f, 3f, 11f, 3.45f, 11f, 4f)
                curveTo(11f, 4.55f, 11.45f, 5f, 12f, 5f)
                close()
                
                moveTo(16f, 5f)
                curveTo(16.55f, 5f, 17f, 4.55f, 17f, 4f)
                curveTo(17f, 3.45f, 16.55f, 3f, 16f, 3f)
                curveTo(15.45f, 3f, 15f, 3.45f, 15f, 4f)
                curveTo(15f, 4.55f, 15.45f, 5f, 16f, 5f)
                close()
                
                // Bottom beads
                moveTo(12f, 16f)
                lineTo(12f, 21f)
                moveTo(10f, 18f)
                curveTo(10.55f, 18f, 11f, 17.55f, 11f, 17f)
                curveTo(11f, 16.45f, 10.55f, 16f, 10f, 16f)
                curveTo(9.45f, 16f, 9f, 16.45f, 9f, 17f)
                curveTo(9f, 17.55f, 9.45f, 18f, 10f, 18f)
                close()
                
                moveTo(14f, 18f)
                curveTo(14.55f, 18f, 15f, 17.55f, 15f, 17f)
                curveTo(15f, 16.45f, 14.55f, 16f, 14f, 16f)
                curveTo(13.45f, 16f, 13f, 16.45f, 13f, 17f)
                curveTo(13f, 17.55f, 13.45f, 18f, 14f, 18f)
                close()
            }
        }.build()
    
    /**
     * Bookmark icon - filled version
     */
    val BookmarkFilled: ImageVector
        get() = ImageVector.Builder(
            name = "BookmarkFilled",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(17f, 3f)
                horizontalLineTo(7f)
                curveTo(5.9f, 3f, 5f, 3.9f, 5f, 5f)
                verticalLineTo(21f)
                lineTo(12f, 18f)
                lineTo(19f, 21f)
                verticalLineTo(5f)
                curveTo(19f, 3.9f, 18.1f, 3f, 17f, 3f)
                close()
            }
        }.build()
    
    /**
     * Bookmark icon - outline version
     */
    val BookmarkOutline: ImageVector
        get() = ImageVector.Builder(
            name = "BookmarkOutline",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(17f, 3f)
                horizontalLineTo(7f)
                curveTo(5.9f, 3f, 5f, 3.9f, 5f, 5f)
                verticalLineTo(21f)
                lineTo(12f, 18f)
                lineTo(19f, 21f)
                verticalLineTo(5f)
                curveTo(19f, 3.9f, 18.1f, 3f, 17f, 3f)
                close()
            }
        }.build()
    
    /**
     * Settings icon - modern gear
     */
    val Settings: ImageVector
        get() = ImageVector.Builder(
            name = "Settings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(19.14f, 12.94f)
                curveTo(19.18f, 12.64f, 19.2f, 12.33f, 19.2f, 12f)
                curveTo(19.2f, 11.68f, 19.18f, 11.36f, 19.13f, 11.06f)
                lineTo(21.16f, 9.48f)
                curveTo(21.34f, 9.34f, 21.39f, 9.07f, 21.28f, 8.87f)
                lineTo(19.36f, 5.55f)
                curveTo(19.24f, 5.33f, 18.99f, 5.26f, 18.77f, 5.33f)
                lineTo(16.38f, 6.29f)
                curveTo(15.88f, 5.91f, 15.35f, 5.59f, 14.76f, 5.35f)
                lineTo(14.4f, 2.81f)
                curveTo(14.36f, 2.57f, 14.16f, 2.4f, 13.92f, 2.4f)
                horizontalLineTo(10.08f)
                curveTo(9.84f, 2.4f, 9.65f, 2.57f, 9.61f, 2.81f)
                lineTo(9.25f, 5.35f)
                curveTo(8.66f, 5.59f, 8.12f, 5.92f, 7.63f, 6.29f)
                lineTo(5.24f, 5.33f)
                curveTo(5.02f, 5.25f, 4.77f, 5.33f, 4.65f, 5.55f)
                lineTo(2.74f, 8.87f)
                curveTo(2.62f, 9.08f, 2.66f, 9.34f, 2.86f, 9.48f)
                lineTo(4.89f, 11.06f)
                curveTo(4.84f, 11.36f, 4.8f, 11.69f, 4.8f, 12f)
                curveTo(4.8f, 12.31f, 4.82f, 12.64f, 4.87f, 12.94f)
                lineTo(2.84f, 14.52f)
                curveTo(2.66f, 14.66f, 2.61f, 14.93f, 2.72f, 15.13f)
                lineTo(4.64f, 18.45f)
                curveTo(4.76f, 18.67f, 5.01f, 18.74f, 5.23f, 18.67f)
                lineTo(7.62f, 17.71f)
                curveTo(8.12f, 18.09f, 8.65f, 18.41f, 9.24f, 18.65f)
                lineTo(9.6f, 21.19f)
                curveTo(9.64f, 21.43f, 9.84f, 21.6f, 10.08f, 21.6f)
                horizontalLineTo(13.92f)
                curveTo(14.16f, 21.6f, 14.35f, 21.43f, 14.39f, 21.19f)
                lineTo(14.75f, 18.65f)
                curveTo(15.34f, 18.41f, 15.88f, 18.09f, 16.37f, 17.71f)
                lineTo(18.76f, 18.67f)
                curveTo(18.98f, 18.75f, 19.23f, 18.67f, 19.35f, 18.45f)
                lineTo(21.26f, 15.13f)
                curveTo(21.38f, 14.91f, 21.34f, 14.66f, 21.14f, 14.52f)
                lineTo(19.14f, 12.94f)
                close()
                
                moveTo(12f, 15.6f)
                curveTo(10.02f, 15.6f, 8.4f, 13.98f, 8.4f, 12f)
                curveTo(8.4f, 10.02f, 10.02f, 8.4f, 12f, 8.4f)
                curveTo(13.98f, 8.4f, 15.6f, 10.02f, 15.6f, 12f)
                curveTo(15.6f, 13.98f, 13.98f, 15.6f, 12f, 15.6f)
                close()
            }
        }.build()
    
    /**
     * Compass icon - for Qibla direction
     */
    val Compass: ImageVector
        get() = ImageVector.Builder(
            name = "Compass",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = SolidColor(Color.Black)
            ) {
                moveTo(12f, 2f)
                curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
                curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
                curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
                curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
                close()
                
                moveTo(12f, 20f)
                curveTo(7.59f, 20f, 4f, 16.41f, 4f, 12f)
                curveTo(4f, 7.59f, 7.59f, 4f, 12f, 4f)
                curveTo(16.41f, 4f, 20f, 7.59f, 20f, 12f)
                curveTo(20f, 16.41f, 16.41f, 20f, 12f, 20f)
                close()
                
                // Compass needle
                moveTo(12f, 6f)
                lineTo(9f, 12f)
                lineTo(12f, 10.5f)
                lineTo(15f, 12f)
                close()
                
                moveTo(12f, 18f)
                lineTo(15f, 12f)
                lineTo(12f, 13.5f)
                lineTo(9f, 12f)
                close()
            }
        }.build()
}
