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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.tuitionbatch.app.ui.theme.AppThemeConfig
import com.tuitionbatch.app.ui.theme.ThemeArtType

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
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            when (themeConfig.backgroundType) {
                ThemeArtType.CYBER_STARS -> {
                    // 1. Cyber Midnight: Twinkling cyber stars & subtle bottom grid
                    val starPositions = listOf(
                        Offset(width * 0.12f, height * 0.08f),
                        Offset(width * 0.85f, height * 0.15f),
                        Offset(width * 0.35f, height * 0.22f),
                        Offset(width * 0.72f, height * 0.38f),
                        Offset(width * 0.18f, height * 0.55f),
                        Offset(width * 0.88f, height * 0.65f),
                        Offset(width * 0.45f, height * 0.82f),
                        Offset(width * 0.65f, height * 0.92f)
                    )
                    starPositions.forEachIndexed { i, pos ->
                        val radius = if (i % 2 == 0) 2.5f else 1.5f
                        drawCircle(color = themeConfig.primary.copy(alpha = 0.55f), radius = radius, center = pos)
                        drawCircle(color = Color.White.copy(alpha = 0.85f), radius = radius * 0.6f, center = pos)
                    }

                    // Top-right glowing cyber nebula
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(themeConfig.primary.copy(alpha = 0.22f), Color.Transparent),
                            center = Offset(width * 0.85f, height * 0.12f),
                            radius = width * 0.7f
                        ),
                        radius = width * 0.7f,
                        center = Offset(width * 0.85f, height * 0.12f)
                    )

                    // Bottom ambient glow
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(themeConfig.secondary.copy(alpha = 0.18f), Color.Transparent),
                            center = Offset(width * 0.2f, height * 0.85f),
                            radius = width * 0.8f
                        ),
                        radius = width * 0.8f,
                        center = Offset(width * 0.2f, height * 0.85f)
                    )
                }

                ThemeArtType.AURORA_WAVES -> {
                    // 2. Royal Aurora: Glowing Northern Lights wave ribbons
                    val wavePath1 = Path().apply {
                        moveTo(0f, height * 0.15f)
                        cubicTo(width * 0.35f, height * 0.08f, width * 0.65f, height * 0.25f, width, height * 0.12f)
                        lineTo(width, height * 0.38f)
                        cubicTo(width * 0.65f, height * 0.45f, width * 0.35f, height * 0.28f, 0f, height * 0.35f)
                        close()
                    }
                    drawPath(
                        path = wavePath1,
                        brush = Brush.horizontalGradient(
                            listOf(Color.Transparent, themeConfig.primary.copy(alpha = 0.18f), themeConfig.secondary.copy(alpha = 0.15f), Color.Transparent)
                        )
                    )

                    // Bottom ambient aurora
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(themeConfig.primary.copy(alpha = 0.16f), Color.Transparent),
                            center = Offset(width * 0.5f, height * 0.9f),
                            radius = width * 0.85f
                        ),
                        radius = width * 0.85f,
                        center = Offset(width * 0.5f, height * 0.9f)
                    )
                }

                ThemeArtType.COSMIC_NEBULA -> {
                    // 3. Cosmic Nebula: Deep stellar dust & glowing orbs
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(themeConfig.primary.copy(alpha = 0.25f), themeConfig.secondary.copy(alpha = 0.10f), Color.Transparent),
                            center = Offset(width * 0.75f, height * 0.2f),
                            radius = width * 0.8f
                        ),
                        radius = width * 0.8f,
                        center = Offset(width * 0.75f, height * 0.2f)
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(themeConfig.secondary.copy(alpha = 0.20f), Color.Transparent),
                            center = Offset(width * 0.2f, height * 0.75f),
                            radius = width * 0.75f
                        ),
                        radius = width * 0.75f,
                        center = Offset(width * 0.2f, height * 0.75f)
                    )
                }

                ThemeArtType.SUNSET_HORIZON -> {
                    // 4. Sunset Horizon: Warm radiant twilight glow
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(themeConfig.primary.copy(alpha = 0.25f), themeConfig.secondary.copy(alpha = 0.12f), Color.Transparent),
                            center = Offset(width * 0.5f, height * 0.1f),
                            radius = width * 0.9f
                        ),
                        radius = width * 0.9f,
                        center = Offset(width * 0.5f, height * 0.1f)
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFDE047).copy(alpha = 0.10f), Color.Transparent),
                            center = Offset(width * 0.8f, height * 0.75f),
                            radius = width * 0.6f
                        ),
                        radius = width * 0.6f,
                        center = Offset(width * 0.8f, height * 0.75f)
                    )
                }

                ThemeArtType.OCEAN_ABYSS -> {
                    // 5. Ocean Abyss: Underwater aquatic light & bubbles
                    val bubblePositions = listOf(
                        Offset(width * 0.25f, height * 0.3f),
                        Offset(width * 0.75f, height * 0.45f),
                        Offset(width * 0.4f, height * 0.65f),
                        Offset(width * 0.82f, height * 0.8f)
                    )
                    bubblePositions.forEach { bPos ->
                        drawCircle(color = themeConfig.primary.copy(alpha = 0.12f), radius = 18f, center = bPos)
                        drawCircle(color = Color.White.copy(alpha = 0.20f), radius = 16f, center = bPos, style = Stroke(width = 1.5f))
                    }

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(themeConfig.primary.copy(alpha = 0.20f), Color.Transparent),
                            center = Offset(width * 0.3f, height * 0.15f),
                            radius = width * 0.8f
                        ),
                        radius = width * 0.8f,
                        center = Offset(width * 0.3f, height * 0.15f)
                    )
                }

                ThemeArtType.OBSIDIAN_GOLD -> {
                    // 6. Obsidian Gold: Metallic diagonal gold sweeps
                    val goldLine = Path().apply {
                        moveTo(0f, height * 0.3f)
                        lineTo(width, height * 0.15f)
                        lineTo(width, height * 0.22f)
                        lineTo(0f, height * 0.37f)
                        close()
                    }
                    drawPath(
                        path = goldLine,
                        brush = Brush.horizontalGradient(
                            listOf(Color.Transparent, themeConfig.primary.copy(alpha = 0.14f), themeConfig.secondary.copy(alpha = 0.22f), Color.Transparent)
                        )
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(themeConfig.primary.copy(alpha = 0.15f), Color.Transparent),
                            center = Offset(width * 0.7f, height * 0.8f),
                            radius = width * 0.75f
                        ),
                        radius = width * 0.75f,
                        center = Offset(width * 0.7f, height * 0.8f)
                    )
                }

                ThemeArtType.CYBERPUNK_GRID -> {
                    // 7. Cyberpunk Synthwave: Hot pink & cyan horizon glow
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(themeConfig.primary.copy(alpha = 0.24f), Color.Transparent),
                            center = Offset(width * 0.2f, height * 0.25f),
                            radius = width * 0.8f
                        ),
                        radius = width * 0.8f,
                        center = Offset(width * 0.2f, height * 0.25f)
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(themeConfig.secondary.copy(alpha = 0.20f), Color.Transparent),
                            center = Offset(width * 0.85f, height * 0.75f),
                            radius = width * 0.8f
                        ),
                        radius = width * 0.8f,
                        center = Offset(width * 0.85f, height * 0.75f)
                    )
                }

                ThemeArtType.TITANIUM_MATRIX -> {
                    // 8. Titanium Matrix: Sleek high-tech dot grid
                    for (x in 20..width.toInt() step 60) {
                        for (y in 40..height.toInt() step 60) {
                            drawCircle(color = themeConfig.primary.copy(alpha = 0.08f), radius = 1.5f, center = Offset(x.toFloat(), y.toFloat()))
                        }
                    }

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(themeConfig.accent.copy(alpha = 0.12f), Color.Transparent),
                            center = Offset(width * 0.5f, height * 0.3f),
                            radius = width * 0.7f
                        ),
                        radius = width * 0.7f,
                        center = Offset(width * 0.5f, height * 0.3f)
                    )
                }

                ThemeArtType.EMERALD_VELVET -> {
                    // 9. Mystic Emerald: Glowing organic botanical spores
                    val sporePositions = listOf(
                        Offset(width * 0.2f, height * 0.15f),
                        Offset(width * 0.8f, height * 0.3f),
                        Offset(width * 0.35f, height * 0.5f),
                        Offset(width * 0.7f, height * 0.7f),
                        Offset(width * 0.15f, height * 0.85f)
                    )
                    sporePositions.forEach { sPos ->
                        drawCircle(color = themeConfig.primary.copy(alpha = 0.35f), radius = 4f, center = sPos)
                        drawCircle(color = Color.White.copy(alpha = 0.6f), radius = 2f, center = sPos)
                    }

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(themeConfig.primary.copy(alpha = 0.20f), Color.Transparent),
                            center = Offset(width * 0.5f, height * 0.5f),
                            radius = width * 0.85f
                        ),
                        radius = width * 0.85f,
                        center = Offset(width * 0.5f, height * 0.5f)
                    )
                }

                ThemeArtType.FROST_LIGHT -> {
                    // 10. Crystal Frost Light: Prismatic soft luxury flares
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFBFDBFE).copy(alpha = 0.5f), Color.Transparent),
                            center = Offset(width * 0.8f, height * 0.15f),
                            radius = width * 0.7f
                        ),
                        radius = width * 0.7f,
                        center = Offset(width * 0.8f, height * 0.15f)
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFDDD6FE).copy(alpha = 0.4f), Color.Transparent),
                            center = Offset(width * 0.2f, height * 0.85f),
                            radius = width * 0.8f
                        ),
                        radius = width * 0.8f,
                        center = Offset(width * 0.2f, height * 0.85f)
                    )
                }
            }
        }

        content()
    }
}
