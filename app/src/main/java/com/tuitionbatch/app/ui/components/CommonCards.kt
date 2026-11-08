package com.tuitionbatch.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuitionbatch.app.notification.AlarmScheduler
import com.tuitionbatch.app.ui.theme.AppThemeConfig
import java.util.Calendar

@Composable
fun StatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
    themeConfig: AppThemeConfig? = null
) {
    if (themeConfig != null) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(18.dp))
                .background(themeConfig.cardBackground)
                .border(1.2.dp, themeConfig.cardBorderBrush, RoundedCornerShape(18.dp))
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(iconColor.copy(alpha = 0.18f))
                        .border(1.dp, iconColor.copy(alpha = 0.35f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = themeConfig.subtextColor
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                        fontWeight = FontWeight.Bold,
                        color = themeConfig.textColor
                    )
                    if (subtitle.isNotEmpty()) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = themeConfig.subtextColor
                        )
                    }
                }
            }
        }
    } else {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(iconColor.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = value,
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (subtitle.isNotEmpty()) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DayChip(
    dayNumber: Int,
    isSelected: Boolean,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    themeConfig: AppThemeConfig? = null
) {
    val dayName = when (dayNumber) {
        Calendar.SATURDAY -> "শনি"
        Calendar.SUNDAY -> "রবি"
        Calendar.MONDAY -> "সোম"
        Calendar.TUESDAY -> "মঙ্গল"
        Calendar.WEDNESDAY -> "বুধ"
        Calendar.THURSDAY -> "বৃহঃ"
        Calendar.FRIDAY -> "শুক্র"
        else -> ""
    }

    if (themeConfig != null) {
        val bgColor = if (isSelected) themeConfig.primary else themeConfig.cardBackground.copy(alpha = 0.4f)
        val textColor = if (isSelected) (if (themeConfig.isDark) Color.Black else Color.White) else themeConfig.subtextColor
        val borderColor = if (isSelected) themeConfig.primary else themeConfig.cardBorder.copy(alpha = 0.4f)

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(bgColor)
                .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dayName,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = textColor
            )
        }
    } else {
        val bgColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
        val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(bgColor)
                .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = dayName,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = textColor
            )
        }
    }
}

@Composable
fun EmptyPlaceholder(
    message: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    themeConfig: AppThemeConfig? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(if (themeConfig != null) themeConfig.cardBackground else MaterialTheme.colorScheme.surfaceVariant)
                .then(if (themeConfig != null) Modifier.border(1.dp, themeConfig.cardBorder, CircleShape) else Modifier),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                tint = if (themeConfig != null) themeConfig.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = if (themeConfig != null) themeConfig.subtextColor else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
