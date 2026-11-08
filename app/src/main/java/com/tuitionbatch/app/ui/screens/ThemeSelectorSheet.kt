package com.tuitionbatch.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tuitionbatch.app.ui.theme.AppThemeConfig
import com.tuitionbatch.app.ui.theme.ThemeManager

@Composable
fun ThemeSelectorDialog(
    currentThemeId: Int,
    onThemeSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(26.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxWidth()
            ) {
                // Top Title & Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Palette,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "১০টি প্রিমিয়াম থিম নির্বাচন",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Text(
                    text = "যেকোনো থিমে ট্যাপ করলেই পুরো অ্যাপের ব্যাকগ্রাউন্ড, কার্ড ও নিয়ন গ্লো সাথে সাথে বদলে যাবে।",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                LazyColumn(
                    modifier = Modifier.heightIn(max = 500.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(ThemeManager.themes) { themeConfig ->
                        val isSelected = themeConfig.id == currentThemeId

                        LuxuryThemeItemCard(
                            themeConfig = themeConfig,
                            isSelected = isSelected,
                            onSelect = { onThemeSelected(themeConfig.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LuxuryThemeItemCard(
    themeConfig: AppThemeConfig,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(themeConfig.backgroundBrush)
            .border(
                width = if (isSelected) 2.5.dp else 1.dp,
                color = if (isSelected) themeConfig.primary else themeConfig.cardBorder,
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onSelect() }
            .padding(10.dp)
    ) {
        Column {
            // Theme Title & Selection Checkmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Swatches
                    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                        themeConfig.previewColors.forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(0.5.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = themeConfig.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = themeConfig.textPrimary
                    )
                }

                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(themeConfig.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = if (themeConfig.isDark) Color(0xFF0F172A) else Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = themeConfig.subtitle,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = themeConfig.textSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mini Live Card Mockup inside the Theme Item
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(themeConfig.cardGradientBrush)
                    .border(1.dp, themeConfig.cardBorderBrush, RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Mini Time Pill
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(themeConfig.primary.copy(alpha = 0.22f))
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "12:40 PM",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = themeConfig.primary
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Column {
                            Text(
                                text = "Pagda Batch",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = themeConfig.textPrimary
                            )
                            Text(
                                text = "📍 Pagda • 🔔 অ্যালার্ম সক্রিয়",
                                fontSize = 9.sp,
                                color = themeConfig.textSecondary
                            )
                        }
                    }

                    // Mini Live Tag
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(themeConfig.liveGlowColor.copy(alpha = 0.3f))
                            .border(0.8.dp, themeConfig.liveGlowColor, RoundedCornerShape(6.dp))
                            .padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "🔴 LIVE",
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = themeConfig.textPrimary
                        )
                    }
                }
            }
        }
    }
}
