package com.tuitionbatch.app.ui.theme

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

enum class ThemeArtType {
    CYBER_STARS,
    AURORA_WAVES,
    COSMIC_NEBULA,
    SUNSET_HORIZON,
    OCEAN_ABYSS,
    OBSIDIAN_GOLD,
    CYBERPUNK_GRID,
    TITANIUM_MATRIX,
    EMERALD_VELVET,
    FROST_LIGHT
}

data class AppThemeConfig(
    val id: Int,
    val name: String,
    val subtitle: String,
    val isDark: Boolean,
    val primary: Color,
    val secondary: Color,
    val accent: Color,
    val backgroundBrush: Brush,
    val cardBackground: Color,
    val cardGradientBrush: Brush,
    val cardBorderBrush: Brush,
    val textPrimary: Color,
    val textSecondary: Color,
    val liveGlowColor: Color,
    val previewColors: List<Color>,
    val backgroundType: ThemeArtType,
    // Custom distinct typography & text colors
    val headerTitleColor: Color = primary,
    val englishDateColor: Color = textPrimary,
    val bengaliDateColor: Color = accent,
    val arabicDateColor: Color = secondary,
    val batchTitleColor: Color = textPrimary,
    val timeCapsuleColor: Color = primary,
    val locationTextColor: Color = textSecondary,
    val tabSelectedBg: Color = primary,
    val tabSelectedTextColor: Color = if (isDark) Color(0xFF0F172A) else Color.White,
    val tabUnselectedTextColor: Color = textSecondary
) {
    val cardBorder: Color get() = primary.copy(alpha = 0.35f)
    val textColor: Color get() = textPrimary
    val subtextColor: Color get() = textSecondary
}

object ThemeManager {
    private const val PREFS_NAME = "tuition_theme_prefs"
    private const val KEY_THEME_ID = "selected_theme_id"

    val themes = listOf(
        // 1. Deep Cyber Midnight
        AppThemeConfig(
            id = 0,
            name = "সাইবার মিডনাইট (Cyber Midnight)",
            subtitle = "গভীর মহাকাশ ও ইলেকট্রিক সায়ান নিয়ন",
            isDark = true,
            primary = Color(0xFF00F0FF),      // Cyber Cyan
            secondary = Color(0xFF818CF8),    // Indigo
            accent = Color(0xFFFF007A),       // Neon Magenta
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF040714), Color(0xFF0B132B), Color(0xFF02040A))
            ),
            cardBackground = Color(0xFF0F1A36).copy(alpha = 0.88f),
            cardGradientBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF14244B).copy(alpha = 0.92f), Color(0xFF0D1730).copy(alpha = 0.85f)),
                start = Offset(0f, 0f), end = Offset(600f, 600f)
            ),
            cardBorderBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF00F0FF).copy(alpha = 0.8f), Color(0xFF818CF8).copy(alpha = 0.35f), Color(0xFF040714).copy(alpha = 0.1f))
            ),
            textPrimary = Color(0xFFF8FAFC),
            textSecondary = Color(0xFF94A3B8),
            liveGlowColor = Color(0xFF00F0FF),
            previewColors = listOf(Color(0xFF040714), Color(0xFF00F0FF), Color(0xFF818CF8)),
            backgroundType = ThemeArtType.CYBER_STARS,
            headerTitleColor = Color(0xFF00F0FF),
            englishDateColor = Color(0xFFF8FAFC),
            bengaliDateColor = Color(0xFFFF007A),
            arabicDateColor = Color(0xFF818CF8),
            batchTitleColor = Color(0xFFF8FAFC),
            timeCapsuleColor = Color(0xFF00F0FF),
            locationTextColor = Color(0xFF38BDF8),
            tabSelectedBg = Color(0xFF00F0FF),
            tabSelectedTextColor = Color(0xFF02040A),
            tabUnselectedTextColor = Color(0xFF94A3B8)
        ),

        // 2. Royal Aurora (Northern Lights)
        AppThemeConfig(
            id = 1,
            name = "রয়্যাল অরোরা (Royal Aurora)",
            subtitle = "নর্দার্ন লাইটসের সবুজ তরঙ্গ ও গোল্ড",
            isDark = true,
            primary = Color(0xFF10B981),      // Emerald Glow
            secondary = Color(0xFF06B6D4),    // Teal
            accent = Color(0xFFFBBF24),       // Amber
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF021614), Color(0xFF042B25), Color(0xFF010E0C))
            ),
            cardBackground = Color(0xFF09332C).copy(alpha = 0.88f),
            cardGradientBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF0E453C).copy(alpha = 0.92f), Color(0xFF072722).copy(alpha = 0.85f)),
                start = Offset(0f, 0f), end = Offset(600f, 600f)
            ),
            cardBorderBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF10B981).copy(alpha = 0.85f), Color(0xFF06B6D4).copy(alpha = 0.4f), Color(0xFF021614).copy(alpha = 0.1f))
            ),
            textPrimary = Color(0xFFECFDF5),
            textSecondary = Color(0xFF6EE7B7),
            liveGlowColor = Color(0xFF10B981),
            previewColors = listOf(Color(0xFF021614), Color(0xFF10B981), Color(0xFF06B6D4)),
            backgroundType = ThemeArtType.AURORA_WAVES,
            headerTitleColor = Color(0xFF10B981),
            englishDateColor = Color(0xFFECFDF5),
            bengaliDateColor = Color(0xFFFBBF24),
            arabicDateColor = Color(0xFF06B6D4),
            batchTitleColor = Color(0xFFECFDF5),
            timeCapsuleColor = Color(0xFF10B981),
            locationTextColor = Color(0xFF6EE7B7),
            tabSelectedBg = Color(0xFF10B981),
            tabSelectedTextColor = Color(0xFF010E0C),
            tabUnselectedTextColor = Color(0xFF6EE7B7)
        ),

        // 3. Cosmic Nebula (Galaxy Violet)
        AppThemeConfig(
            id = 2,
            name = "কসমিক নেবুলা (Cosmic Nebula)",
            subtitle = "গ্যালাক্সি ভায়োলেট ও নিয়ন ম্যাজেন্টা ডাস্ট",
            isDark = true,
            primary = Color(0xFFC084FC),      // Lavender Neon
            secondary = Color(0xFFF472B6),    // Pink Neon
            accent = Color(0xFF38BDF8),       // Sky
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF130422), Color(0xFF28074B), Color(0xFF0B0115))
            ),
            cardBackground = Color(0xFF2B0E4C).copy(alpha = 0.88f),
            cardGradientBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF3B1566).copy(alpha = 0.92f), Color(0xFF200938).copy(alpha = 0.85f)),
                start = Offset(0f, 0f), end = Offset(600f, 600f)
            ),
            cardBorderBrush = Brush.linearGradient(
                colors = listOf(Color(0xFFE879F9).copy(alpha = 0.85f), Color(0xFFC084FC).copy(alpha = 0.4f), Color(0xFF130422).copy(alpha = 0.1f))
            ),
            textPrimary = Color(0xFFFAF5FF),
            textSecondary = Color(0xFFD8B4FE),
            liveGlowColor = Color(0xFFE879F9),
            previewColors = listOf(Color(0xFF130422), Color(0xFFC084FC), Color(0xFFF472B6)),
            backgroundType = ThemeArtType.COSMIC_NEBULA,
            headerTitleColor = Color(0xFFC084FC),
            englishDateColor = Color(0xFFFAF5FF),
            bengaliDateColor = Color(0xFFF472B6),
            arabicDateColor = Color(0xFF38BDF8),
            batchTitleColor = Color(0xFFFAF5FF),
            timeCapsuleColor = Color(0xFFC084FC),
            locationTextColor = Color(0xFFD8B4FE),
            tabSelectedBg = Color(0xFFC084FC),
            tabSelectedTextColor = Color(0xFF130422),
            tabUnselectedTextColor = Color(0xFFD8B4FE)
        ),

        // 4. Sunset Crimson Horizon
        AppThemeConfig(
            id = 3,
            name = "সানসেট হরাইজন (Sunset Horizon)",
            subtitle = "গোধূলির উষ্ণ কোরাল, লাল ও সোনালি আভা",
            isDark = true,
            primary = Color(0xFFFB923C),      // Warm Orange
            secondary = Color(0xFFF43F5E),    // Crimson Rose
            accent = Color(0xFFFDE047),       // Sunlight
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF22050B), Color(0xFF3E0816), Color(0xFF140206))
            ),
            cardBackground = Color(0xFF3B0C18).copy(alpha = 0.88f),
            cardGradientBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF501222).copy(alpha = 0.92f), Color(0xFF2A0610).copy(alpha = 0.85f)),
                start = Offset(0f, 0f), end = Offset(600f, 600f)
            ),
            cardBorderBrush = Brush.linearGradient(
                colors = listOf(Color(0xFFFB923C).copy(alpha = 0.85f), Color(0xFFF43F5E).copy(alpha = 0.4f), Color(0xFF22050B).copy(alpha = 0.1f))
            ),
            textPrimary = Color(0xFFFFF1F2),
            textSecondary = Color(0xFFFDA4AF),
            liveGlowColor = Color(0xFFFB7185),
            previewColors = listOf(Color(0xFF22050B), Color(0xFFFB923C), Color(0xFFF43F5E)),
            backgroundType = ThemeArtType.SUNSET_HORIZON,
            headerTitleColor = Color(0xFFFB923C),
            englishDateColor = Color(0xFFFFF1F2),
            bengaliDateColor = Color(0xFFFDE047),
            arabicDateColor = Color(0xFFF43F5E),
            batchTitleColor = Color(0xFFFFF1F2),
            timeCapsuleColor = Color(0xFFFB923C),
            locationTextColor = Color(0xFFFDA4AF),
            tabSelectedBg = Color(0xFFFB923C),
            tabSelectedTextColor = Color(0xFF22050B),
            tabUnselectedTextColor = Color(0xFFFDA4AF)
        ),

        // 5. Deep Sapphire Abyss
        AppThemeConfig(
            id = 4,
            name = "স্যাফায়ার ওশান (Sapphire Ocean)",
            subtitle = "গভীর সমুদ্রের নীল ও মায়াবী একোয়া আলো",
            isDark = true,
            primary = Color(0xFF38BDF8),      // Ocean Blue
            secondary = Color(0xFF818CF8),    // Deep Marine
            accent = Color(0xFF34D399),       // Mint Coral
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF021220), Color(0xFF08223B), Color(0xFF010A12))
            ),
            cardBackground = Color(0xFF0B2B4A).copy(alpha = 0.88f),
            cardGradientBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF103A63).copy(alpha = 0.92f), Color(0xFF071F36).copy(alpha = 0.85f)),
                start = Offset(0f, 0f), end = Offset(600f, 600f)
            ),
            cardBorderBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF38BDF8).copy(alpha = 0.85f), Color(0xFF60A5FA).copy(alpha = 0.4f), Color(0xFF021220).copy(alpha = 0.1f))
            ),
            textPrimary = Color(0xFFF0F9FF),
            textSecondary = Color(0xFF7DD3FC),
            liveGlowColor = Color(0xFF38BDF8),
            previewColors = listOf(Color(0xFF021220), Color(0xFF38BDF8), Color(0xFF60A5FA)),
            backgroundType = ThemeArtType.OCEAN_ABYSS,
            headerTitleColor = Color(0xFF38BDF8),
            englishDateColor = Color(0xFFF0F9FF),
            bengaliDateColor = Color(0xFF34D399),
            arabicDateColor = Color(0xFF818CF8),
            batchTitleColor = Color(0xFFF0F9FF),
            timeCapsuleColor = Color(0xFF38BDF8),
            locationTextColor = Color(0xFF7DD3FC),
            tabSelectedBg = Color(0xFF38BDF8),
            tabSelectedTextColor = Color(0xFF021220),
            tabUnselectedTextColor = Color(0xFF7DD3FC)
        ),

        // 6. Imperial Obsidian & Royal Gold
        AppThemeConfig(
            id = 5,
            name = "অবসিডিয়ান গোল্ড (Obsidian Gold)",
            subtitle = "লাক্সারি কার্বন ব্ল্যাক ও ব্রাশড খাঁটি গোল্ড",
            isDark = true,
            primary = Color(0xFFF59E0B),      // Pure Gold
            secondary = Color(0xFFFDE68A),    // Champagne
            accent = Color(0xFFDC2626),       // Royal Ruby
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF0C0A07), Color(0xFF19150E), Color(0xFF060503))
            ),
            cardBackground = Color(0xFF211D15).copy(alpha = 0.88f),
            cardGradientBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF2E271B).copy(alpha = 0.92f), Color(0xFF17130D).copy(alpha = 0.85f)),
                start = Offset(0f, 0f), end = Offset(600f, 600f)
            ),
            cardBorderBrush = Brush.linearGradient(
                colors = listOf(Color(0xFFF59E0B).copy(alpha = 0.85f), Color(0xFFFDE68A).copy(alpha = 0.4f), Color(0xFF0C0A07).copy(alpha = 0.1f))
            ),
            textPrimary = Color(0xFFFFFBEB),
            textSecondary = Color(0xFFD6D3D1),
            liveGlowColor = Color(0xFFFBBF24),
            previewColors = listOf(Color(0xFF0C0A07), Color(0xFFF59E0B), Color(0xFFFDE68A)),
            backgroundType = ThemeArtType.OBSIDIAN_GOLD,
            headerTitleColor = Color(0xFFF59E0B),
            englishDateColor = Color(0xFFFFFBEB),
            bengaliDateColor = Color(0xFFFDE68A),
            arabicDateColor = Color(0xFFF87171),
            batchTitleColor = Color(0xFFFFFBEB),
            timeCapsuleColor = Color(0xFFF59E0B),
            locationTextColor = Color(0xFFD6D3D1),
            tabSelectedBg = Color(0xFFF59E0B),
            tabSelectedTextColor = Color(0xFF0C0A07),
            tabUnselectedTextColor = Color(0xFFD6D3D1)
        ),

        // 7. Tokyo Cyberpunk Synthwave
        AppThemeConfig(
            id = 6,
            name = "সাইবারপাংক নিয়ন (Cyberpunk Synthwave)",
            subtitle = "টোকিও নিয়ন গ্রিড, ইলেকট্রিক হট পিংক",
            isDark = true,
            primary = Color(0xFFFF0055),      // Hot Pink Neon
            secondary = Color(0xFF00F0FF),    // Cyan Neon
            accent = Color(0xFFB026FF),       // Purple Neon
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF140108), Color(0xFF260212), Color(0xFF0A0004))
            ),
            cardBackground = Color(0xFF300619).copy(alpha = 0.88f),
            cardGradientBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF450A26).copy(alpha = 0.92f), Color(0xFF200310).copy(alpha = 0.85f)),
                start = Offset(0f, 0f), end = Offset(600f, 600f)
            ),
            cardBorderBrush = Brush.linearGradient(
                colors = listOf(Color(0xFFFF0055).copy(alpha = 0.85f), Color(0xFF00F0FF).copy(alpha = 0.45f), Color(0xFF140108).copy(alpha = 0.1f))
            ),
            textPrimary = Color(0xFFFFFFFF),
            textSecondary = Color(0xFFF472B6),
            liveGlowColor = Color(0xFFFF0055),
            previewColors = listOf(Color(0xFF140108), Color(0xFFFF0055), Color(0xFF00F0FF)),
            backgroundType = ThemeArtType.CYBERPUNK_GRID,
            headerTitleColor = Color(0xFFFF0055),
            englishDateColor = Color(0xFFFFFFFF),
            bengaliDateColor = Color(0xFF00F0FF),
            arabicDateColor = Color(0xFFB026FF),
            batchTitleColor = Color(0xFFFFFFFF),
            timeCapsuleColor = Color(0xFFFF0055),
            locationTextColor = Color(0xFFF472B6),
            tabSelectedBg = Color(0xFFFF0055),
            tabSelectedTextColor = Color(0xFFFFFFFF),
            tabUnselectedTextColor = Color(0xFFF472B6)
        ),

        // 8. Titanium Rainbow Prism
        AppThemeConfig(
            id = 7,
            name = "টাইটানিয়াম প্রিজম (Titanium Prism)",
            subtitle = "বহুবর্ণ স্পেকট্রাম, সায়ান ও নিয়ন ভায়োলেট আলো",
            isDark = true,
            primary = Color(0xFF38BDF8),      // Electric Cyan
            secondary = Color(0xFFA855F7),    // Neon Violet
            accent = Color(0xFFFBBF24),       // Prism Amber
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF070B18), Color(0xFF111936), Color(0xFF04060E))
            ),
            cardBackground = Color(0xFF141F42).copy(alpha = 0.88f),
            cardGradientBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF1C2C5E).copy(alpha = 0.92f), Color(0xFF0F1733).copy(alpha = 0.85f)),
                start = Offset(0f, 0f), end = Offset(600f, 600f)
            ),
            cardBorderBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF38BDF8).copy(alpha = 0.85f), Color(0xFFA855F7).copy(alpha = 0.6f), Color(0xFFFBBF24).copy(alpha = 0.4f))
            ),
            textPrimary = Color(0xFFF8FAFC),
            textSecondary = Color(0xFFBAE6FD),
            liveGlowColor = Color(0xFF38BDF8),
            previewColors = listOf(Color(0xFF070B18), Color(0xFF38BDF8), Color(0xFFA855F7)),
            backgroundType = ThemeArtType.TITANIUM_MATRIX,
            headerTitleColor = Color(0xFF38BDF8),
            englishDateColor = Color(0xFFF8FAFC),
            bengaliDateColor = Color(0xFFFBBF24),
            arabicDateColor = Color(0xFFA855F7),
            batchTitleColor = Color(0xFFF8FAFC),
            timeCapsuleColor = Color(0xFF38BDF8),
            locationTextColor = Color(0xFFBAE6FD),
            tabSelectedBg = Color(0xFF38BDF8),
            tabSelectedTextColor = Color(0xFF070B18),
            tabUnselectedTextColor = Color(0xFFBAE6FD)
        ),

        // 9. Mystic Emerald Forest
        AppThemeConfig(
            id = 8,
            name = "এমারেল্ড ভেলভেট (Mystic Emerald)",
            subtitle = "রহস্যময় অরণ্যের ডার্ক গ্রিন ও জোনাকির আলো",
            isDark = true,
            primary = Color(0xFF34D399),      // Mint Glow
            secondary = Color(0xFFA7F3D0),    // Pastel Mint
            accent = Color(0xFFF59E0B),       // Golden Amber
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFF04140E), Color(0xFF0B2E20), Color(0xFF020B08))
            ),
            cardBackground = Color(0xFF0F3628).copy(alpha = 0.88f),
            cardGradientBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF164B38).copy(alpha = 0.92f), Color(0xFF0A261C).copy(alpha = 0.85f)),
                start = Offset(0f, 0f), end = Offset(600f, 600f)
            ),
            cardBorderBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF34D399).copy(alpha = 0.85f), Color(0xFFA7F3D0).copy(alpha = 0.4f), Color(0xFF04140E).copy(alpha = 0.1f))
            ),
            textPrimary = Color(0xFFF0FDF4),
            textSecondary = Color(0xFF86EFAC),
            liveGlowColor = Color(0xFF34D399),
            previewColors = listOf(Color(0xFF04140E), Color(0xFF34D399), Color(0xFFA7F3D0)),
            backgroundType = ThemeArtType.EMERALD_VELVET,
            headerTitleColor = Color(0xFF34D399),
            englishDateColor = Color(0xFFF0FDF4),
            bengaliDateColor = Color(0xFFF59E0B),
            arabicDateColor = Color(0xFFA7F3D0),
            batchTitleColor = Color(0xFFF0FDF4),
            timeCapsuleColor = Color(0xFF34D399),
            locationTextColor = Color(0xFF86EFAC),
            tabSelectedBg = Color(0xFF34D399),
            tabSelectedTextColor = Color(0xFF04140E),
            tabUnselectedTextColor = Color(0xFF86EFAC)
        ),

        // 10. Crystal Frost Glass (Light Luxury)
        AppThemeConfig(
            id = 9,
            name = "ক্রিস্টাল ফ্রস্ট গ্লাস (Crystal Frost Light)",
            subtitle = "হিমশীতল কাচের আলো ও রাজকীয় ব্লু অ্যাকসেন্ট",
            isDark = false,
            primary = Color(0xFF1D4ED8),      // Royal Sapphire
            secondary = Color(0xFF0284C7),    // Sky Cyan
            accent = Color(0xFFD97706),       // Amber
            backgroundBrush = Brush.verticalGradient(
                listOf(Color(0xFFEFF6FF), Color(0xFFDBEAFE), Color(0xFFF8FAFC))
            ),
            cardBackground = Color(0xFFFFFFFF).copy(alpha = 0.92f),
            cardGradientBrush = Brush.linearGradient(
                colors = listOf(Color(0xFFFFFFFF), Color(0xFFF1F5F9).copy(alpha = 0.95f)),
                start = Offset(0f, 0f), end = Offset(600f, 600f)
            ),
            cardBorderBrush = Brush.linearGradient(
                colors = listOf(Color(0xFF93C5FD).copy(alpha = 0.9f), Color(0xFF3B82F6).copy(alpha = 0.5f), Color(0xFFDBEAFE).copy(alpha = 0.2f))
            ),
            textPrimary = Color(0xFF0F172A),
            textSecondary = Color(0xFF475569),
            liveGlowColor = Color(0xFF2563EB),
            previewColors = listOf(Color(0xFFEFF6FF), Color(0xFF1D4ED8), Color(0xFF0284C7)),
            backgroundType = ThemeArtType.FROST_LIGHT,
            headerTitleColor = Color(0xFF1D4ED8),
            englishDateColor = Color(0xFF0F172A),
            bengaliDateColor = Color(0xFFD97706),
            arabicDateColor = Color(0xFF0284C7),
            batchTitleColor = Color(0xFF0F172A),
            timeCapsuleColor = Color(0xFF1D4ED8),
            locationTextColor = Color(0xFF475569),
            tabSelectedBg = Color(0xFF1D4ED8),
            tabSelectedTextColor = Color(0xFFFFFFFF),
            tabUnselectedTextColor = Color(0xFF475569)
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
