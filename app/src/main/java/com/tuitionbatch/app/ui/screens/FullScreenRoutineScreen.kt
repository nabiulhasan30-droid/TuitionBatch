package com.tuitionbatch.app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuitionbatch.app.data.local.entity.BatchEntity
import com.tuitionbatch.app.notification.AlarmScheduler
import com.tuitionbatch.app.ui.components.EmptyPlaceholder
import com.tuitionbatch.app.ui.components.ThemeBackground
import com.tuitionbatch.app.ui.theme.AppThemeConfig
import com.tuitionbatch.app.ui.viewmodel.MainViewModel
import com.tuitionbatch.app.util.DateHelper
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenRoutineScreen(
    viewModel: MainViewModel,
    themeConfig: AppThemeConfig,
    onOpenDrawer: () -> Unit,
    onOpenCalendar: () -> Unit,
    onOpenThemePicker: () -> Unit,
    onOpenBatchDetail: (Long) -> Unit
) {
    val context = LocalContext.current
    val activeBatches by viewModel.activeBatches.collectAsState()
    val allStudents by viewModel.allStudents.collectAsState()

    // ৪. বার গুলো সুন্দর স্টাইলিশ বাংলায় শর্ট ফর্ম এ (শনি, রবি, সোম, মঙ্গল, বুধ, বৃহ, শুক্র)
    val daysList = listOf(
        Pair(Calendar.SATURDAY, "শনি"),
        Pair(Calendar.SUNDAY, "রবি"),
        Pair(Calendar.MONDAY, "সোম"),
        Pair(Calendar.TUESDAY, "মঙ্গল"),
        Pair(Calendar.WEDNESDAY, "বুধ"),
        Pair(Calendar.THURSDAY, "বৃহ"),
        Pair(Calendar.FRIDAY, "শুক্র")
    )

    val todayCalendarDay = remember { Calendar.getInstance().get(Calendar.DAY_OF_WEEK) }
    var selectedDay by remember { mutableIntStateOf(todayCalendarDay) }

    // Live clock ticker
    var currentTimeMillis by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            currentTimeMillis = System.currentTimeMillis()
            kotlinx.coroutines.delay(1000)
        }
    }

    val todayCal = remember(currentTimeMillis) {
        Calendar.getInstance().apply { timeInMillis = currentTimeMillis }
    }

    // ২. সবার উপরে ইংরেজি তারিখের নিচে বাংলা তারিখ মাস ও আরবি তারিখ মাস (পশ্চিমবঙ্গীয় নিয়ম)
    val englishDateStr = remember(currentTimeMillis) {
        DateHelper.getEnglishDate(todayCal)
    }
    val bengaliDateStr = remember(currentTimeMillis) {
        DateHelper.getWestBengalBengaliDate(todayCal)
    }
    val arabicDateStr = remember(currentTimeMillis) {
        DateHelper.getArabicHijriDate(todayCal)
    }

    val dayBatches = remember(activeBatches, selectedDay) {
        activeBatches
            .filter { it.daysOfWeek.contains(selectedDay) }
            .sortedWith(compareBy({ it.startTimeHour }, { it.startTimeMinute }))
    }

    ThemeBackground(themeConfig = themeConfig) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Sleek Ultra-Compact Header with Dates
            Surface(
                color = Color.Transparent,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Hamburger Menu (Three-line button)
                    IconButton(
                        onClick = onOpenDrawer,
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(themeConfig.cardBackground)
                            .border(1.dp, themeConfig.cardBorder, RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu Drawer",
                            tint = themeConfig.primary
                        )
                    }

                    // ১. তিনটি তারিখ একসাথে একটি স্টাইলিশ অসাধারণ বাক্সে (Header Date Trio Box)
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 6.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { onOpenCalendar() },
                        shape = RoundedCornerShape(14.dp),
                        color = themeConfig.cardBackground.copy(alpha = 0.88f),
                        border = androidx.compose.foundation.BorderStroke(
                            width = 1.2.dp,
                            brush = Brush.horizontalGradient(
                                listOf(
                                    themeConfig.primary.copy(alpha = 0.65f),
                                    themeConfig.cardBorder,
                                    themeConfig.accent.copy(alpha = 0.55f)
                                )
                            )
                        )
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            themeConfig.primary.copy(alpha = 0.08f),
                                            Color.Transparent
                                        )
                                    )
                                )
                                .padding(horizontal = 6.dp, vertical = 4.dp)
                        ) {
                            // ইংরেজি তারিখ
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    Icons.Default.DateRange,
                                    contentDescription = null,
                                    tint = themeConfig.primary,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = englishDateStr,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Black,
                                        letterSpacing = 0.1.sp
                                    ),
                                    color = themeConfig.englishDateColor,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                            Spacer(modifier = Modifier.height(2.5.dp))

                            // বাংলা ও আরবি তারিখ ক্যাপসুল
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                // পশ্চিমবঙ্গীয় বাংলা তারিখ
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(themeConfig.bengaliDateColor.copy(alpha = 0.12f))
                                        .border(
                                            0.5.dp,
                                            themeConfig.bengaliDateColor.copy(alpha = 0.35f),
                                            RoundedCornerShape(5.dp)
                                        )
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "🌾 $bengaliDateStr",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 9.5.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = themeConfig.bengaliDateColor,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                Spacer(modifier = Modifier.width(4.dp))

                                // আরবি হিজরি তারিখ
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(themeConfig.arabicDateColor.copy(alpha = 0.12f))
                                        .border(
                                            0.5.dp,
                                            themeConfig.arabicDateColor.copy(alpha = 0.35f),
                                            RoundedCornerShape(5.dp)
                                        )
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "🌙 $arabicDateStr",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 9.5.sp,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = themeConfig.arabicDateColor,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }

                    // Top Right Action: Stylish Dedicated Calendar Button
                    Surface(
                        onClick = onOpenCalendar,
                        modifier = Modifier
                            .height(38.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        color = themeConfig.cardBackground,
                        border = androidx.compose.foundation.BorderStroke(
                            width = 1.2.dp,
                            brush = Brush.linearGradient(
                                listOf(
                                    themeConfig.accent,
                                    themeConfig.primary.copy(alpha = 0.8f)
                                )
                            )
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                Icons.Default.CalendarMonth,
                                contentDescription = "West Bengal Calendar",
                                tint = themeConfig.accent,
                                modifier = Modifier.size(19.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "পঞ্জিকা",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 11.5.sp,
                                    letterSpacing = 0.2.sp
                                ),
                                color = themeConfig.accent
                            )
                        }
                    }
                }
            }

            // ৪. বার গুলো সুন্দর স্টাইলিশ শর্ট ফর্ম এ (সোম, বুধ, বৃহ, শুক্র)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                daysList.forEach { (calDay, shortLabel) ->
                    val isSelected = selectedDay == calDay
                    val isToday = todayCalendarDay == calDay

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) themeConfig.tabSelectedBg
                                else themeConfig.cardBackground.copy(alpha = 0.65f)
                            )
                            .border(
                                width = if (isSelected) 1.5.dp else if (isToday) 1.2.dp else 0.8.dp,
                                color = if (isSelected) themeConfig.primary
                                else if (isToday) themeConfig.accent
                                else themeConfig.cardBorder.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clickable { selectedDay = calDay },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = shortLabel,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontSize = 13.5.sp,
                                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold
                                ),
                                color = if (isSelected) themeConfig.tabSelectedTextColor
                                else themeConfig.tabUnselectedTextColor
                            )
                            if (isToday) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Box(
                                    modifier = Modifier
                                        .size(3.5.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) themeConfig.tabSelectedTextColor else themeConfig.accent)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Full-Screen Routine Container (Fills the entire screen, no scrolling required)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                if (dayBatches.isEmpty()) {
                    EmptyPlaceholder(
                        message = "${daysList.find { it.first == selectedDay }?.second}-বারে কোনো ব্যাচ নেই। মেন্যু থেকে নতুন ব্যাচ যোগ করতে পারেন।",
                        icon = Icons.Default.EventBusy
                    )
                } else {
                    // Adaptive Column that distributes cards so they fit directly on screen
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        dayBatches.forEach { batch ->
                            val isToday = selectedDay == todayCalendarDay
                            val batchStatus = remember(batch, currentTimeMillis, isToday) {
                                if (!isToday) BatchLiveStatus.UPCOMING
                                else calculateBatchStatus(batch, currentTimeMillis)
                            }

                            FullRoutineCard(
                                batch = batch,
                                studentCount = 0,
                                status = batchStatus,
                                themeConfig = themeConfig,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f, fill = false),
                                onClick = { onOpenBatchDetail(batch.id) },
                                onCall = {
                                    val studentsInBatch = allStudents.filter { it.batchId == batch.id }
                                    val phone = studentsInBatch.firstOrNull { it.studentPhone.isNotBlank() }?.studentPhone
                                        ?: studentsInBatch.firstOrNull { it.guardianPhone.isNotBlank() }?.guardianPhone
                                    if (!phone.isNullOrBlank()) {
                                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                                        context.startActivity(intent)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class BatchLiveStatus {
    LIVE_NOW,       // Currently running
    UPCOMING_SOON,  // Within next 1 hour (15-min alarm triggers soon)
    UPCOMING,       // Later today
    COMPLETED       // Already finished today
}

fun calculateBatchStatus(batch: BatchEntity, nowMillis: Long): BatchLiveStatus {
    val cal = Calendar.getInstance().apply { timeInMillis = nowMillis }
    val currentHour = cal.get(Calendar.HOUR_OF_DAY)
    val currentMinute = cal.get(Calendar.MINUTE)
    val nowMinutesTotal = currentHour * 60 + currentMinute

    val startMinutesTotal = batch.startTimeHour * 60 + batch.startTimeMinute
    val endMinutesTotal = startMinutesTotal + batch.durationMinutes

    return when {
        nowMinutesTotal in startMinutesTotal until endMinutesTotal -> BatchLiveStatus.LIVE_NOW
        nowMinutesTotal < startMinutesTotal && (startMinutesTotal - nowMinutesTotal) <= 60 -> BatchLiveStatus.UPCOMING_SOON
        nowMinutesTotal >= endMinutesTotal -> BatchLiveStatus.COMPLETED
        else -> BatchLiveStatus.UPCOMING
    }
}

@Composable
fun FullRoutineCard(
    batch: BatchEntity,
    studentCount: Int,
    status: BatchLiveStatus,
    themeConfig: AppThemeConfig,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onCall: () -> Unit
) {
    val timeFormatted = AlarmScheduler.formatTime(batch.startTimeHour, batch.startTimeMinute)
    val endHour = (batch.startTimeHour * 60 + batch.startTimeMinute + batch.durationMinutes) / 60
    val endMinute = (batch.startTimeHour * 60 + batch.startTimeMinute + batch.durationMinutes) % 60
    val endTimeFormatted = AlarmScheduler.formatTime(endHour % 24, endMinute)

    // Outer Glassmorphic Container
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(themeConfig.cardGradientBrush)
            .border(1.2.dp, themeConfig.cardBorderBrush, RoundedCornerShape(18.dp))
            .clickable { onClick() }
    ) {
        // Specular top highlight (Apple Glass reflection)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            Color.White.copy(alpha = if (themeConfig.isDark) 0.35f else 0.6f),
                            Color.White.copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                )
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 14.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ১. সময় (Time Capsule)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        if (themeConfig.isDark) Color(0xFF040914).copy(alpha = 0.55f)
                        else Color(0xFFE2E8F0).copy(alpha = 0.65f)
                    )
                    .border(
                        1.dp,
                        themeConfig.cardBorder.copy(alpha = 0.6f),
                        RoundedCornerShape(14.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 7.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = timeFormatted,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 14.5.sp,
                            fontWeight = FontWeight.ExtraBold
                        ),
                        color = themeConfig.timeCapsuleColor
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.ArrowDownward,
                            contentDescription = null,
                            tint = themeConfig.textSecondary,
                            modifier = Modifier.size(10.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = endTimeFormatted,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.5.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = themeConfig.textSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // ২. ব্যাচের নাম & ৩. জায়গা (Center Column)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                // ব্যাচ এর নাম
                Text(
                    text = batch.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.2.sp
                    ),
                    color = themeConfig.batchTitleColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(3.dp))

                // জায়গা
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = themeConfig.locationTextColor,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = batch.location.ifBlank { "নির্দিষ্ট স্থান নেই" },
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = themeConfig.locationTextColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // ৪. ফোন এর বাটন (Phone call button)
            IconButton(
                onClick = onCall,
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(themeConfig.primary.copy(alpha = 0.22f))
                    .border(1.2.dp, themeConfig.primary.copy(alpha = 0.55f), CircleShape)
            ) {
                Icon(
                    Icons.Default.Call,
                    contentDescription = "কল করুন",
                    tint = themeConfig.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
