package com.tuitionbatch.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
        // 1. Color-matched scenic artistic photo / illustration
        Image(
            painter = painterResource(id = themeConfig.backgroundDrawableRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // 2. Frosted glass gradient overlay to ensure text contrast and legibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            themeConfig.cardBackground.copy(alpha = if (themeConfig.isDark) 0.50f else 0.35f),
                            themeConfig.cardBackground.copy(alpha = if (themeConfig.isDark) 0.70f else 0.55f),
                            themeConfig.cardBackground.copy(alpha = if (themeConfig.isDark) 0.85f else 0.75f)
                        )
                    )
                )
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            when (themeConfig.backgroundType) {
                ThemeArtType.CYBER_STARS -> {
                    // 1. Cyber Midnight: Luminous cyber stars, neon halos, and dual laser nebulae
                    val starPositions = listOf(
                        Offset(width * 0.10f, height * 0.07f),
                        Offset(width * 0.85f, height * 0.12f),
                        Offset(width * 0.30f, height * 0.20f),
                        Offset(width * 0.75f, height * 0.32f),
                        Offset(width * 0.15f, height * 0.48f),
                        Offset(width * 0.90f, height * 0.60f),
                        Offset(width * 0.40f, height * 0.75f),
                        Offset(width * 0.70f, height * 0.88f),
                        Offset(width * 0.22f, height * 0.92f)
                    )
                    starPositions.forEachIndexed { i, pos ->
                        val radius = if (i % 2 == 0) 3.5f else 2.2f
                        val haloColor = if (i % 3 == 0) Color(0xFFFF007A) else Color(0xFF00F0FF)
                        drawCircle(color = haloColor.copy(alpha = 0.45f), radius = radius * 2.5f, center = pos)
                        drawCircle(color = Color.White, radius = radius, center = pos)
                    }

                    // Top-right electric cyan neon nebula
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF00F0FF).copy(alpha = 0.32f), Color(0xFF818CF8).copy(alpha = 0.15f), Color.Transparent),
                            center = Offset(width * 0.85f, height * 0.15f),
                            radius = width * 0.85f
                        ),
                        radius = width * 0.85f,
                        center = Offset(width * 0.85f, height * 0.15f)
                    )

                    // Bottom-left hot magenta nebula
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFF007A).copy(alpha = 0.25f), Color(0xFF3B82F6).copy(alpha = 0.10f), Color.Transparent),
                            center = Offset(width * 0.15f, height * 0.82f),
                            radius = width * 0.8f
                        ),
                        radius = width * 0.8f,
                        center = Offset(width * 0.15f, height * 0.82f)
                    )
                }

                ThemeArtType.AURORA_WAVES -> {
                    // 2. Royal Aurora: Glowing Northern Lights wave ribbons in vibrant emerald, cyan & gold
                    val wavePath1 = Path().apply {
                        moveTo(0f, height * 0.18f)
                        cubicTo(width * 0.35f, height * 0.06f, width * 0.65f, height * 0.26f, width, height * 0.14f)
                        lineTo(width, height * 0.38f)
                        cubicTo(width * 0.65f, height * 0.48f, width * 0.35f, height * 0.28f, 0f, height * 0.38f)
                        close()
                    }
                    drawPath(
                        path = wavePath1,
                        brush = Brush.horizontalGradient(
                            listOf(Color.Transparent, Color(0xFF10B981).copy(alpha = 0.30f), Color(0xFF06B6D4).copy(alpha = 0.28f), Color(0xFFFBBF24).copy(alpha = 0.15f), Color.Transparent)
                        )
                    )

                    val wavePath2 = Path().apply {
                        moveTo(0f, height * 0.55f)
                        cubicTo(width * 0.4f, height * 0.68f, width * 0.7f, height * 0.48f, width, height * 0.62f)
                        lineTo(width, height * 0.78f)
                        cubicTo(width * 0.7f, height * 0.62f, width * 0.4f, height * 0.82f, 0f, height * 0.70f)
                        close()
                    }
                    drawPath(
                        path = wavePath2,
                        brush = Brush.horizontalGradient(
                            listOf(Color.Transparent, Color(0xFF06B6D4).copy(alpha = 0.22f), Color(0xFF10B981).copy(alpha = 0.25f), Color.Transparent)
                        )
                    )

                    // Aurora ambient background orbs
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF10B981).copy(alpha = 0.28f), Color.Transparent),
                            center = Offset(width * 0.5f, height * 0.2f),
                            radius = width * 0.8f
                        ),
                        radius = width * 0.8f,
                        center = Offset(width * 0.5f, height * 0.2f)
                    )
                }

                ThemeArtType.COSMIC_NEBULA -> {
                    // 3. Cosmic Nebula: Deep stellar dust & multi-color galactic clouds
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFC084FC).copy(alpha = 0.38f), Color(0xFFF472B6).copy(alpha = 0.20f), Color.Transparent),
                            center = Offset(width * 0.8f, height * 0.2f),
                            radius = width * 0.85f
                        ),
                        radius = width * 0.85f,
                        center = Offset(width * 0.8f, height * 0.2f)
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF38BDF8).copy(alpha = 0.25f), Color(0xFF818CF8).copy(alpha = 0.18f), Color.Transparent),
                            center = Offset(width * 0.2f, height * 0.75f),
                            radius = width * 0.8f
                        ),
                        radius = width * 0.8f,
                        center = Offset(width * 0.2f, height * 0.75f)
                    )

                    // Galaxy dust speckles
                    val dustPositions = listOf(
                        Offset(width * 0.25f, height * 0.15f),
                        Offset(width * 0.65f, height * 0.28f),
                        Offset(width * 0.82f, height * 0.42f),
                        Offset(width * 0.38f, height * 0.62f),
                        Offset(width * 0.78f, height * 0.82f)
                    )
                    dustPositions.forEach { pos ->
                        drawCircle(color = Color(0xFFF472B6).copy(alpha = 0.7f), radius = 2.5f, center = pos)
                        drawCircle(color = Color.White, radius = 1.2f, center = pos)
                    }
                }

                ThemeArtType.SUNSET_HORIZON -> {
                    // 4. Sunset Horizon: Rich fiery orange, crimson, and golden sun aura
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFB923C).copy(alpha = 0.38f), Color(0xFFF43F5E).copy(alpha = 0.24f), Color(0xFFFDE047).copy(alpha = 0.12f), Color.Transparent),
                            center = Offset(width * 0.5f, height * 0.12f),
                            radius = width * 0.95f
                        ),
                        radius = width * 0.95f,
                        center = Offset(width * 0.5f, height * 0.12f)
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFF43F5E).copy(alpha = 0.28f), Color(0xFF881337).copy(alpha = 0.15f), Color.Transparent),
                            center = Offset(width * 0.8f, height * 0.80f),
                            radius = width * 0.75f
                        ),
                        radius = width * 0.75f,
                        center = Offset(width * 0.8f, height * 0.80f)
                    )
                }

                ThemeArtType.OCEAN_ABYSS -> {
                    // 5. Ocean Abyss: Bioluminescent glowing bubbles and aquatic cyan/marine shafts
                    val bubblePositions = listOf(
                        Offset(width * 0.20f, height * 0.25f),
                        Offset(width * 0.78f, height * 0.35f),
                        Offset(width * 0.35f, height * 0.55f),
                        Offset(width * 0.85f, height * 0.72f),
                        Offset(width * 0.15f, height * 0.85f)
                    )
                    bubblePositions.forEachIndexed { i, bPos ->
                        val bRadius = if (i % 2 == 0) 22f else 16f
                        drawCircle(color = Color(0xFF38BDF8).copy(alpha = 0.22f), radius = bRadius, center = bPos)
                        drawCircle(color = Color(0xFF34D399).copy(alpha = 0.40f), radius = bRadius, center = bPos, style = Stroke(width = 2f))
                        drawCircle(color = Color.White.copy(alpha = 0.6f), radius = 3f, center = Offset(bPos.x - 4f, bPos.y - 4f))
                    }

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF38BDF8).copy(alpha = 0.32f), Color(0xFF0284C7).copy(alpha = 0.15f), Color.Transparent),
                            center = Offset(width * 0.3f, height * 0.18f),
                            radius = width * 0.9f
                        ),
                        radius = width * 0.9f,
                        center = Offset(width * 0.3f, height * 0.18f)
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF34D399).copy(alpha = 0.25f), Color.Transparent),
                            center = Offset(width * 0.75f, height * 0.85f),
                            radius = width * 0.75f
                        ),
                        radius = width * 0.75f,
                        center = Offset(width * 0.75f, height * 0.85f)
                    )
                }

                ThemeArtType.OBSIDIAN_GOLD -> {
                    // 6. Obsidian Gold: Rich gold shimmer, golden ribbons & amber radiant flares
                    val goldSweep = Path().apply {
                        moveTo(0f, height * 0.22f)
                        lineTo(width, height * 0.10f)
                        lineTo(width, height * 0.18f)
                        lineTo(0f, height * 0.30f)
                        close()
                    }
                    drawPath(
                        path = goldSweep,
                        brush = Brush.horizontalGradient(
                            listOf(Color.Transparent, Color(0xFFF59E0B).copy(alpha = 0.35f), Color(0xFFFDE68A).copy(alpha = 0.50f), Color(0xFFF59E0B).copy(alpha = 0.25f), Color.Transparent)
                        )
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFF59E0B).copy(alpha = 0.32f), Color(0xFF78350F).copy(alpha = 0.15f), Color.Transparent),
                            center = Offset(width * 0.75f, height * 0.78f),
                            radius = width * 0.85f
                        ),
                        radius = width * 0.85f,
                        center = Offset(width * 0.75f, height * 0.78f)
                    )

                    // Gold dust particles
                    val goldParticles = listOf(
                        Offset(width * 0.18f, height * 0.12f),
                        Offset(width * 0.82f, height * 0.28f),
                        Offset(width * 0.45f, height * 0.48f),
                        Offset(width * 0.65f, height * 0.65f),
                        Offset(width * 0.25f, height * 0.85f)
                    )
                    goldParticles.forEach { gPos ->
                        drawCircle(color = Color(0xFFF59E0B).copy(alpha = 0.6f), radius = 3.5f, center = gPos)
                        drawCircle(color = Color(0xFFFFFBEB), radius = 1.8f, center = gPos)
                    }
                }

                ThemeArtType.CYBERPUNK_GRID -> {
                    // 7. Cyberpunk Synthwave: Hot neon pink sun, electric cyan grid & synthwave atmosphere
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFFF0055).copy(alpha = 0.45f), Color(0xFFB026FF).copy(alpha = 0.25f), Color.Transparent),
                            center = Offset(width * 0.5f, height * 0.22f),
                            radius = width * 0.85f
                        ),
                        radius = width * 0.85f,
                        center = Offset(width * 0.5f, height * 0.22f)
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF00F0FF).copy(alpha = 0.32f), Color.Transparent),
                            center = Offset(width * 0.85f, height * 0.80f),
                            radius = width * 0.8f
                        ),
                        radius = width * 0.8f,
                        center = Offset(width * 0.85f, height * 0.80f)
                    )

                    // Cyber horizontal laser lines
                    val laserLines = listOf(height * 0.15f, height * 0.28f, height * 0.75f, height * 0.88f)
                    laserLines.forEach { yPos ->
                        drawLine(
                            brush = Brush.horizontalGradient(
                                listOf(Color.Transparent, Color(0xFF00F0FF).copy(alpha = 0.25f), Color(0xFFFF0055).copy(alpha = 0.35f), Color.Transparent)
                            ),
                            start = Offset(0f, yPos),
                            end = Offset(width, yPos),
                            strokeWidth = 1.5f
                        )
                    }
                }

                ThemeArtType.TITANIUM_MATRIX -> {
                    // 8. Titanium Matrix: Rainbow prism spectrum flares & high-tech matrix grid
                    for (x in 20..width.toInt() step 55) {
                        for (y in 40..height.toInt() step 55) {
                            drawCircle(color = Color(0xFF38BDF8).copy(alpha = 0.14f), radius = 1.8f, center = Offset(x.toFloat(), y.toFloat()))
                        }
                    }

                    // Iridescent prism halo
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF38BDF8).copy(alpha = 0.25f), Color(0xFF818CF8).copy(alpha = 0.20f), Color(0xFF34D399).copy(alpha = 0.12f), Color.Transparent),
                            center = Offset(width * 0.5f, height * 0.25f),
                            radius = width * 0.85f
                        ),
                        radius = width * 0.85f,
                        center = Offset(width * 0.5f, height * 0.25f)
                    )
                }

                ThemeArtType.EMERALD_VELVET -> {
                    // 9. Mystic Emerald: Glowing fireflies & emerald auroral blooms
                    val sporePositions = listOf(
                        Offset(width * 0.18f, height * 0.12f),
                        Offset(width * 0.82f, height * 0.25f),
                        Offset(width * 0.32f, height * 0.45f),
                        Offset(width * 0.75f, height * 0.65f),
                        Offset(width * 0.15f, height * 0.82f),
                        Offset(width * 0.60f, height * 0.90f)
                    )
                    sporePositions.forEachIndexed { i, sPos ->
                        val sRadius = if (i % 2 == 0) 4f else 2.5f
                        drawCircle(color = Color(0xFF34D399).copy(alpha = 0.50f), radius = sRadius * 2f, center = sPos)
                        drawCircle(color = Color(0xFFF59E0B).copy(alpha = 0.70f), radius = sRadius, center = sPos)
                        drawCircle(color = Color.White, radius = sRadius * 0.5f, center = sPos)
                    }

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF34D399).copy(alpha = 0.32f), Color(0xFF047857).copy(alpha = 0.18f), Color.Transparent),
                            center = Offset(width * 0.5f, height * 0.45f),
                            radius = width * 0.9f
                        ),
                        radius = width * 0.9f,
                        center = Offset(width * 0.5f, height * 0.45f)
                    )
                }

                ThemeArtType.FROST_LIGHT -> {
                    // 10. Crystal Frost Light: Luminous prismatic crystal refractions
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFF93C5FD).copy(alpha = 0.65f), Color(0xFFC7D2FE).copy(alpha = 0.45f), Color.Transparent),
                            center = Offset(width * 0.8f, height * 0.15f),
                            radius = width * 0.8f
                        ),
                        radius = width * 0.8f,
                        center = Offset(width * 0.8f, height * 0.15f)
                    )

                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFDDD6FE).copy(alpha = 0.55f), Color(0xFFFBCFE8).copy(alpha = 0.35f), Color.Transparent),
                            center = Offset(width * 0.2f, height * 0.82f),
                            radius = width * 0.85f
                        ),
                        radius = width * 0.85f,
                        center = Offset(width * 0.2f, height * 0.82f)
                    )
                }
            }
        }

        content()
    }
}
