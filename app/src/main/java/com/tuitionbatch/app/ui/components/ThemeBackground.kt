package com.tuitionbatch.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.tuitionbatch.app.ui.theme.AppThemeConfig

@Composable
fun ThemeBackground(
    themeConfig: AppThemeConfig,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(themeConfig.backgroundBrush)
    ) {
        // Ambient soft atmospheric lighting
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // Top-right glowing radial orb
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        themeConfig.primary.copy(alpha = if (themeConfig.isDark) 0.18f else 0.12f),
                        Color.Transparent
                    ),
                    center = Offset(width * 0.85f, height * 0.12f),
                    radius = width * 0.75f
                ),
                radius = width * 0.75f,
                center = Offset(width * 0.85f, height * 0.12f)
            )

            // Bottom-left glowing ambient orb
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        themeConfig.secondary.copy(alpha = if (themeConfig.isDark) 0.14f else 0.08f),
                        Color.Transparent
                    ),
                    center = Offset(width * 0.15f, height * 0.85f),
                    radius = width * 0.80f
                ),
                radius = width * 0.80f,
                center = Offset(width * 0.15f, height * 0.85f)
            )

            // Center subtle atmospheric tint
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        themeConfig.accent.copy(alpha = if (themeConfig.isDark) 0.08f else 0.05f),
                        Color.Transparent
                    ),
                    center = Offset(width * 0.5f, height * 0.5f),
                    radius = width * 0.6f
                ),
                radius = width * 0.6f,
                center = Offset(width * 0.5f, height * 0.5f)
            )
        }

        content()
    }
}
