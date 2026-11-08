package com.tuitionbatch.app.ui.theme

import android.content.Context
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

data class AppThemeConfig(
    val id: Int,
    val name: String,
    val isDark: Boolean,
    val primary: Color,
    val secondary: Color,
    val accent: Color,
    val backgroundBrush: Brush,
    val cardBackground: Color,
    val cardBorder: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val liveGlowColor: Color,
    val previewColors: List<Color>
)

object ThemeManager {
    private const val PREFS_NAME = "tuition_theme_prefs"
    private const val KEY_THEME_ID = "selected_theme_id"

    val themes = listOf(
        // 1. Deep Cyber Midnight
        AppThemeConfig(
            id = 0,
            name = "সাইবার মিডনাইট (Cyber Midnight)",
            isDark = true,
            primary = Color(0xFF38BDF8), // Electric Cyan
            secondary = Color(0xFF818CF8),
            accent = Color(0xFFF43F5E),
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF070B19), Color(0xFF0F172A), Color(0xFF050814))
            ),
            cardBackground = Color(0xFF131D36).copy(alpha = 0.85f),
            cardBorder = Color(0xFF253358),
            textPrimary = Color(0xFFF8FAFC),
            textSecondary = Color(0xFF94A3B8),
            liveGlowColor = Color(0xFF06B6D4),
            previewColors = listOf(Color(0xFF070B19), Color(0xFF38BDF8), Color(0xFF818CF8))
        ),

        // 2. Royal Aurora (Northern Lights)
        AppThemeConfig(
            id = 1,
            name = "রয়্যাল অরোরা (Royal Aurora)",
            isDark = true,
            primary = Color(0xFF10B981), // Emerald
            secondary = Color(0xFF06B6D4), // Teal
            accent = Color(0xFFFBBF24),
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF021B1A), Color(0xFF06332E), Color(0xFF021312))
            ),
            cardBackground = Color(0xFF0A3C36).copy(alpha = 0.80f),
            cardBorder = Color(0xFF136458),
            textPrimary = Color(0xFFECFDF5),
            textSecondary = Color(0xFF6EE7B7),
            liveGlowColor = Color(0xFF10B981),
            previewColors = listOf(Color(0xFF021B1A), Color(0xFF10B981), Color(0xFF06B6D4))
        ),

        // 3. Cosmic Nebula (Galaxy Violet)
        AppThemeConfig(
            id = 2,
            name = "কসমিক নেবুলা (Cosmic Nebula)",
            isDark = true,
            primary = Color(0xFFC084FC), // Lavender Neon
            secondary = Color(0xFFF472B6), // Pink Neon
            accent = Color(0xFF38BDF8),
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF150524), Color(0xFF2E0854), Color(0xFF11021D))
            ),
            cardBackground = Color(0xFF2D114D).copy(alpha = 0.82f),
            cardBorder = Color(0xFF581C87),
            textPrimary = Color(0xFFFAF5FF),
            textSecondary = Color(0xFFD8B4FE),
            liveGlowColor = Color(0xFFE879F9),
            previewColors = listOf(Color(0xFF150524), Color(0xFFC084FC), Color(0xFFF472B6))
        ),

        // 4. Sunset Crimson Horizon
        AppThemeConfig(
            id = 3,
            name = "সানসেট হরাইজন (Sunset Horizon)",
            isDark = true,
            primary = Color(0xFFFB923C), // Orange
            secondary = Color(0xFFF43F5E), // Coral
            accent = Color(0xFFFBBF24),
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF26080E), Color(0xFF450A19), Color(0xFF1A0409))
            ),
            cardBackground = Color(0xFF3B101C).copy(alpha = 0.85f),
            cardBorder = Color(0xFF701A2D),
            textPrimary = Color(0xFFFFF1F2),
            textSecondary = Color(0xFFFDA4AF),
            liveGlowColor = Color(0xFFFB7185),
            previewColors = listOf(Color(0xFF26080E), Color(0xFFFB923C), Color(0xFFF43F5E))
        ),

        // 5. Deep Sapphire Abyss
        AppThemeConfig(
            id = 4,
            name = "স্যাফায়ার ওশান (Sapphire Ocean)",
            isDark = true,
            primary = Color(0xFF60A5FA), // Sky Blue
            secondary = Color(0xFF38BDF8),
            accent = Color(0xFF34D399),
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF031525), Color(0xFF0A2540), Color(0xFF020F1A))
            ),
            cardBackground = Color(0xFF0F3254).copy(alpha = 0.85f),
            cardBorder = Color(0xFF1E4E7A),
            textPrimary = Color(0xFFF0F9FF),
            textSecondary = Color(0xFF7DD3FC),
            liveGlowColor = Color(0xFF38BDF8),
            previewColors = listOf(Color(0xFF031525), Color(0xFF60A5FA), Color(0xFF38BDF8))
        ),

        // 6. Obsidian & Brushed Gold (Luxury)
        AppThemeConfig(
            id = 5,
            name = "অবসিডিয়ান গোল্ড (Obsidian Gold)",
            isDark = true,
            primary = Color(0xFFF59E0B), // Metallic Gold
            secondary = Color(0xFFFDE68A),
            accent = Color(0xFFE11D48),
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF0F0E0B), Color(0xFF1C1917), Color(0xFF0A0908))
            ),
            cardBackground = Color(0xFF292524).copy(alpha = 0.85f),
            cardBorder = Color(0xFF57534E),
            textPrimary = Color(0xFFFFFBEB),
            textSecondary = Color(0xFFD6D3D1),
            liveGlowColor = Color(0xFFFBBF24),
            previewColors = listOf(Color(0xFF0F0E0B), Color(0xFFF59E0B), Color(0xFFFDE68A))
        ),

        // 7. Cyberpunk Neon Red & Blue
        AppThemeConfig(
            id = 6,
            name = "সাইবারপাংক নিয়ন (Cyberpunk Neon)",
            isDark = true,
            primary = Color(0xFFF43F5E), // Neon Pink/Red
            secondary = Color(0xFF06B6D4), // Cyan
            accent = Color(0xFFA855F7),
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF18020B), Color(0xFF2B0418), Color(0xFF0D0106))
            ),
            cardBackground = Color(0xFF330920).copy(alpha = 0.85f),
            cardBorder = Color(0xFF660D3D),
            textPrimary = Color(0xFFFFFFFF),
            textSecondary = Color(0xFFF472B6),
            liveGlowColor = Color(0xFFF43F5E),
            previewColors = listOf(Color(0xFF18020B), Color(0xFFF43F5E), Color(0xFF06B6D4))
        ),

        // 8. Titanium Slate Minimal
        AppThemeConfig(
            id = 7,
            name = "টাইটানিয়াম স্লেট (Titanium Minimal)",
            isDark = true,
            primary = Color(0xFF94A3B8), // Slate
            secondary = Color(0xFFCBD5E1),
            accent = Color(0xFF38BDF8),
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF0F172A), Color(0xFF1E293B), Color(0xFF0B1120))
            ),
            cardBackground = Color(0xFF1E293B).copy(alpha = 0.85f),
            cardBorder = Color(0xFF334155),
            textPrimary = Color(0xFFF8FAFC),
            textSecondary = Color(0xFF94A3B8),
            liveGlowColor = Color(0xFF64748B),
            previewColors = listOf(Color(0xFF0F172A), Color(0xFF94A3B8), Color(0xFFCBD5E1))
        ),

        // 9. Lush Emerald Velvet
        AppThemeConfig(
            id = 8,
            name = "এমারেল্ড ভেলভেট (Emerald Velvet)",
            isDark = true,
            primary = Color(0xFF34D399), // Mint Green
            secondary = Color(0xFFA7F3D0),
            accent = Color(0xFFF59E0B),
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF061A14), Color(0xFF0F3628), Color(0xFF04120D))
            ),
            cardBackground = Color(0xFF123B2D).copy(alpha = 0.85f),
            cardBorder = Color(0xFF1C5C47),
            textPrimary = Color(0xFFF0FDF4),
            textSecondary = Color(0xFF86EFAC),
            liveGlowColor = Color(0xFF34D399),
            previewColors = listOf(Color(0xFF061A14), Color(0xFF34D399), Color(0xFFA7F3D0))
        ),

        // 10. Crisp Frost Glass (Light Luxury)
        AppThemeConfig(
            id = 9,
            name = "ক্লিন ফ্রস্ট গ্লাস (Frost Glass Light)",
            isDark = false,
            primary = Color(0xFF1D4ED8), // Royal Blue
            secondary = Color(0xFF0284C7),
            accent = Color(0xFFD97706),
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFFF0F4F8), Color(0xFFE2E8F0), Color(0xFFF8FAFC))
            ),
            cardBackground = Color(0xFFFFFFFF).copy(alpha = 0.90f),
            cardBorder = Color(0xFFCBD5E1),
            textPrimary = Color(0xFF0F172A),
            textSecondary = Color(0xFF475569),
            liveGlowColor = Color(0xFF2563EB),
            previewColors = listOf(Color(0xFFF0F4F8), Color(0xFF1D4ED8), Color(0xFF0284C7))
        )
    )

    fun getSavedThemeId(context: Context): Int {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_THEME_ID, 0)
    }

    fun saveThemeId(context: Context, themeId: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(KEY_THEME_ID, themeId).apply()
    }

    fun getThemeConfig(id: Int): AppThemeConfig {
        return themes.find { it.id == id } ?: themes.first()
    }
}
