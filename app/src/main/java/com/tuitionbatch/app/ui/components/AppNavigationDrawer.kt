package com.tuitionbatch.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.tuitionbatch.app.ui.theme.AppThemeConfig

enum class DrawerDestination {
    ROUTINE,
    BATCHES,
    STUDENTS,
    FEES,
    ATTENDANCE,
    CALENDAR,
    ADD_BATCH,
    ADD_STUDENT,
    THEME_PICKER,
    ALARM_INFO
}

@Composable
fun AppDrawerContent(
    themeConfig: AppThemeConfig,
    activeBatchCount: Int,
    totalStudentCount: Int,
    currentDestination: DrawerDestination,
    onNavigate: (DrawerDestination) -> Unit
) {
    ModalDrawerSheet(
        drawerContainerColor = if (themeConfig.isDark) Color(0xFF0F172A) else Color(0xFFF8FAFC),
        drawerContentColor = themeConfig.textPrimary,
        modifier = Modifier.width(310.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Header Profile Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(themeConfig.primary.copy(alpha = 0.15f))
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(themeConfig.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.School,
                                contentDescription = null,
                                tint = if (themeConfig.isDark) Color(0xFF0F172A) else Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "Tuition Batch",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = themeConfig.textPrimary
                            )
                            Text(
                                text = "স্মার্ট টিউটর ম্যানেজার",
                                style = MaterialTheme.typography.labelSmall,
                                color = themeConfig.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(themeConfig.primary.copy(alpha = 0.2f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "মোট ব্যাচ: ${activeBatchCount}টি",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = themeConfig.primary
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(themeConfig.secondary.copy(alpha = 0.2f))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "🔔 ১৫মি পূর্বে নোটিফিকেশন",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = themeConfig.secondary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Navigation Items
            DrawerSectionHeader("প্রধান ফিচারসমূহ")

            DrawerItem(
                title = "পড়ানোর ফুল রুটিন",
                icon = Icons.Default.ViewAgenda,
                isSelected = currentDestination == DrawerDestination.ROUTINE,
                themeConfig = themeConfig,
                onClick = { onNavigate(DrawerDestination.ROUTINE) }
            )

            DrawerItem(
                title = "পশ্চিমবঙ্গ চিরস্থায়ী পঞ্জিকা",
                icon = Icons.Default.CalendarMonth,
                isSelected = currentDestination == DrawerDestination.CALENDAR,
                themeConfig = themeConfig,
                onClick = { onNavigate(DrawerDestination.CALENDAR) }
            )

            DrawerItem(
                title = "সকল ব্যাচ ও শিডিউল",
                icon = Icons.Default.Groups,
                isSelected = currentDestination == DrawerDestination.BATCHES,
                themeConfig = themeConfig,
                onClick = { onNavigate(DrawerDestination.BATCHES) }
            )

            DrawerItem(
                title = "শিক্ষার্থী তালিকা ও ডিরেক্ট কল",
                icon = Icons.Default.Person,
                isSelected = currentDestination == DrawerDestination.STUDENTS,
                themeConfig = themeConfig,
                onClick = { onNavigate(DrawerDestination.STUDENTS) }
            )

            DrawerItem(
                title = "দৈনিক ডিজিটাল হাজিরা",
                icon = Icons.Default.FactCheck,
                isSelected = currentDestination == DrawerDestination.ATTENDANCE,
                themeConfig = themeConfig,
                onClick = { onNavigate(DrawerDestination.ATTENDANCE) }
            )

            DrawerItem(
                title = "মাসিক বেতন ও ফি হিসাব",
                icon = Icons.Default.AccountBalanceWallet,
                isSelected = currentDestination == DrawerDestination.FEES,
                themeConfig = themeConfig,
                onClick = { onNavigate(DrawerDestination.FEES) }
            )

            Divider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                color = themeConfig.cardBorder.copy(alpha = 0.5f)
            )

            DrawerSectionHeader("কুইক অ্যাকশন ও সেটিংস")

            DrawerItem(
                title = "🎨 ১০টি প্রিমিয়াম থিম পরিবর্তন",
                icon = Icons.Default.Palette,
                isSelected = false,
                themeConfig = themeConfig,
                onClick = { onNavigate(DrawerDestination.THEME_PICKER) }
            )

            DrawerItem(
                title = "➕ নতুন ব্যাচ তৈরি করুন",
                icon = Icons.Default.AddCircleOutline,
                isSelected = false,
                themeConfig = themeConfig,
                onClick = { onNavigate(DrawerDestination.ADD_BATCH) }
            )

            DrawerItem(
                title = "➕ নতুন শিক্ষার্থী যোগ করুন",
                icon = Icons.Default.PersonAddAlt1,
                isSelected = false,
                themeConfig = themeConfig,
                onClick = { onNavigate(DrawerDestination.ADD_STUDENT) }
            )

            DrawerItem(
                title = "🔔 ১৫-মিনিট অ্যালার্ম ও নোটিফিকেশন",
                icon = Icons.Default.NotificationsActive,
                isSelected = false,
                themeConfig = themeConfig,
                onClick = { onNavigate(DrawerDestination.ALARM_INFO) }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun DrawerSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.sp, fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
    )
}

@Composable
fun DrawerItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    themeConfig: AppThemeConfig,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) themeConfig.primary.copy(alpha = 0.15f) else Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 3.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) themeConfig.primary else themeConfig.textSecondary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) themeConfig.primary else themeConfig.textPrimary
            )
        }
    }
}
