package com.tuitionbatch.app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuitionbatch.app.data.local.entity.BatchEntity
import com.tuitionbatch.app.notification.AlarmScheduler
import com.tuitionbatch.app.ui.components.EmptyPlaceholder
import com.tuitionbatch.app.ui.components.ThemeBackground
import com.tuitionbatch.app.ui.theme.AppThemeConfig
import com.tuitionbatch.app.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenRoutineScreen(
    viewModel: MainViewModel,
    themeConfig: AppThemeConfig,
    onOpenDrawer: () -> Unit,
    onOpenThemePicker: () -> Unit,
    onOpenBatchDetail: (Long) -> Unit
) {
    val context = LocalContext.current
    val activeBatches by viewModel.activeBatches.collectAsState()
    val allStudents by viewModel.allStudents.collectAsState()

    val daysList = listOf(
        Pair(Calendar.SATURDAY, "শনিবার"),
        Pair(Calendar.SUNDAY, "রবিবার"),
        Pair(Calendar.MONDAY, "সোমবার"),
        Pair(Calendar.TUESDAY, "মঙ্গলবার"),
        Pair(Calendar.WEDNESDAY, "বুধবার"),
        Pair(Calendar.THURSDAY, "বৃহস্পতিবার"),
        Pair(Calendar.FRIDAY, "শুক্রবার")
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

    val dayBatches = remember(activeBatches, selectedDay) {
        activeBatches
            .filter { it.daysOfWeek.contains(selectedDay) }
            .sortedWith(compareBy({ it.startTimeHour }, { it.startTimeMinute }))
    }

    val liveFormattedTime = remember(currentTimeMillis) {
        SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH).format(currentTimeMillis)
    }

    val liveFormattedDate = remember(currentTimeMillis) {
        SimpleDateFormat("EEEE, dd MMMM", Locale.ENGLISH).format(currentTimeMillis)
    }

    ThemeBackground(themeConfig = themeConfig) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // Sleek Ultra-Compact Header
            Surface(
                color = Color.Transparent,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Hamburger Menu (Three-line button)
                    IconButton(
                        onClick = onOpenDrawer,
                        modifier = Modifier
                            .size(40.dp)
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

                    // Center live title and digital time
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(themeConfig.primary)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "TUITION BATCH ROUTINE",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    letterSpacing = 1.2.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                color = themeConfig.primary
                            )
                        }
                        Text(
                            text = "$liveFormattedTime • $liveFormattedDate",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp, fontWeight = FontWeight.SemiBold),
                            color = themeConfig.textSecondary
                        )
                    }

                    // Theme selector button
                    IconButton(
                        onClick = onOpenThemePicker,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(themeConfig.cardBackground)
                            .border(1.dp, themeConfig.cardBorder, RoundedCornerShape(12.dp))
                    ) {
                        Icon(
                            Icons.Default.Palette,
                            contentDescription = "Themes",
                            tint = themeConfig.primary
                        )
                    }
                }
            }

            // Compact 7-Day Tab Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                daysList.forEach { (calDay, label) ->
                    val isSelected = selectedDay == calDay
                    val isToday = todayCalendarDay == calDay

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (isSelected) themeConfig.primary
                                else themeConfig.cardBackground
                            )
                            .border(
                                width = if (isToday && !isSelected) 1.5.dp else 1.dp,
                                color = if (isToday && !isSelected) themeConfig.primary else themeConfig.cardBorder,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedDay = calDay },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = label.take(4),
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                color = if (isSelected) {
                                    if (themeConfig.isDark) Color(0xFF0F172A) else Color.White
                                } else themeConfig.textPrimary
                            )
                            if (isToday) {
                                Box(
                                    modifier = Modifier
                                        .size(4.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) Color.White else themeConfig.primary)
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
                        message = "${daysList.find { it.first == selectedDay }?.second}-এ কোনো ব্যাচ নেই। মেন্যু থেকে নতুন ব্যাচ যোগ করতে পারেন।",
                        icon = Icons.Default.EventBusy
                    )
                } else {
                    // Adaptive Column that distributes height so all 4-6 cards fit directly on screen
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        dayBatches.forEach { batch ->
                            val studentCount = allStudents.count { it.batchId == batch.id && it.isActive }
                            val isToday = selectedDay == todayCalendarDay
                            val batchStatus = remember(batch, currentTimeMillis, isToday) {
                                if (!isToday) BatchLiveStatus.UPCOMING
                                else calculateBatchStatus(batch, currentTimeMillis)
                            }

                            FullRoutineCard(
                                batch = batch,
                                studentCount = studentCount,
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
    val isLive = status == BatchLiveStatus.LIVE_NOW
    val isSoon = status == BatchLiveStatus.UPCOMING_SOON

    // Animated pulse for Live batches
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    val cardBorderColor = when {
        isLive -> themeConfig.liveGlowColor.copy(alpha = pulseAlpha)
        isSoon -> Color(0xFFF59E0B)
        else -> themeConfig.cardBorder
    }

    val cardBorderWidth = if (isLive || isSoon) 2.dp else 1.dp

    val timeFormatted = AlarmScheduler.formatTime(batch.startTimeHour, batch.startTimeMinute)
    val endHour = (batch.startTimeHour * 60 + batch.startTimeMinute + batch.durationMinutes) / 60
    val endMinute = (batch.startTimeHour * 60 + batch.startTimeMinute + batch.durationMinutes) % 60
    val endTimeFormatted = AlarmScheduler.formatTime(endHour % 24, endMinute)

    val durationHours = batch.durationMinutes / 60
    val durationMins = batch.durationMinutes % 60
    val durationText = buildString {
        if (durationHours > 0) append("${durationHours}ঘণ্টা ")
        if (durationMins > 0) append("${durationMins}মি")
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isLive) themeConfig.cardBackground.copy(alpha = 0.95f)
            else themeConfig.cardBackground
        ),
        modifier = modifier
            .border(cardBorderWidth, cardBorderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Time Badge (Left Column)
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isLive) themeConfig.primary.copy(alpha = 0.25f)
                        else themeConfig.cardBorder.copy(alpha = 0.4f)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = timeFormatted,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = if (isLive) themeConfig.primary else themeConfig.textPrimary
                )
                Text(
                    text = "হতে $endTimeFormatted",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = themeConfig.textSecondary
                )
                Text(
                    text = "($durationText)",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
                    color = themeConfig.primary
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Main Info Column
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = batch.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = themeConfig.textPrimary,
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // Live or Soon badge
                    if (isLive) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(themeConfig.liveGlowColor.copy(alpha = pulseAlpha))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "🔴 চলমান",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        }
                    } else if (isSoon) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFF59E0B))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "⏳ আসন্ন",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "📍 ${batch.location}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        color = themeConfig.textSecondary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "• 👨‍🎓 ${studentCount}জন",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                        color = themeConfig.textSecondary
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // 15-Minute Alarm Badge
                val reminderHour = (batch.startTimeHour * 60 + batch.startTimeMinute - 15) / 60
                val reminderMinute = (batch.startTimeHour * 60 + batch.startTimeMinute - 15) % 60
                val reminderTimeStr = AlarmScheduler.formatTime(reminderHour % 24, reminderMinute)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.NotificationsActive,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = themeConfig.primary
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "অ্যালার্ম: $reminderTimeStr (১৫মি পূর্বে)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = themeConfig.primary
                    )
                }
            }

            // Quick Actions Right Column
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onCall,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Call",
                        tint = themeConfig.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "Open",
                    tint = themeConfig.textSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
