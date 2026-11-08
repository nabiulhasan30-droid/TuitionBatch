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

    // Animated pulse & radar ripple for Live batches
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.45f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    val radarRadius by infiniteTransition.animateFloat(
        initialValue = 4f,
        targetValue = 14f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radarRadius"
    )
    val radarAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radarAlpha"
    )

    val cardBorderBrush = when {
        isLive -> Brush.linearGradient(
            listOf(themeConfig.liveGlowColor, themeConfig.primary.copy(alpha = pulseAlpha), themeConfig.liveGlowColor)
        )
        isSoon -> Brush.linearGradient(
            listOf(Color(0xFFF59E0B), Color(0xFFFDE68A), Color(0xFFF59E0B).copy(alpha = 0.5f))
        )
        else -> themeConfig.cardBorderBrush
    }

    val cardBorderWidth = if (isLive || isSoon) 1.8.dp else 1.2.dp

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

    val reminderHour = (batch.startTimeHour * 60 + batch.startTimeMinute - 15) / 60
    val reminderMinute = (batch.startTimeHour * 60 + batch.startTimeMinute - 15) % 60
    val reminderTimeStr = AlarmScheduler.formatTime(reminderHour % 24, reminderMinute)

    val batchTagColor = try {
        Color(android.graphics.Color.parseColor(batch.colorHex))
    } catch (e: Exception) {
        themeConfig.primary
    }

    // Outer Glassmorphic Container
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(18.dp))
            .background(themeConfig.cardGradientBrush)
            .border(cardBorderWidth, cardBorderBrush, RoundedCornerShape(18.dp))
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
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Time Pillar (Futuristic Capsule)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        if (isLive) themeConfig.primary.copy(alpha = 0.22f)
                        else if (themeConfig.isDark) Color(0xFF040914).copy(alpha = 0.5f)
                        else Color(0xFFE2E8F0).copy(alpha = 0.6f)
                    )
                    .border(
                        1.dp,
                        if (isLive) themeConfig.liveGlowColor.copy(alpha = 0.5f)
                        else themeConfig.cardBorder.copy(alpha = 0.5f),
                        RoundedCornerShape(14.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = timeFormatted,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 14.5.sp,
                            fontWeight = FontWeight.ExtraBold
                        ),
                        color = if (isLive) themeConfig.primary else themeConfig.textPrimary
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.ArrowDownward,
                            contentDescription = null,
                            tint = themeConfig.textSecondary,
                            modifier = Modifier.size(10.dp)
                        )
                        Text(
                            text = endTimeFormatted,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.SemiBold),
                            color = themeConfig.textSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(themeConfig.primary.copy(alpha = 0.18f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = durationText,
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
                            color = themeConfig.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(11.dp))

            // Center Column: Batch Details & Badges
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Color Tag Accent Dot
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .clip(CircleShape)
                            .background(batchTagColor)
                    )
                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = batch.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.5.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = themeConfig.textPrimary,
                        maxLines = 1,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // Live Radar Pulse or Upcoming Badge
                    if (isLive) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(themeConfig.liveGlowColor.copy(alpha = 0.25f))
                                .border(1.dp, themeConfig.liveGlowColor.copy(alpha = pulseAlpha), RoundedCornerShape(8.dp))
                                .padding(horizontal = 7.dp, vertical = 2.dp)
                        ) {
                            Box(
                                modifier = Modifier.size(14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier.size(14.dp)) {
                                    drawCircle(
                                        color = themeConfig.liveGlowColor.copy(alpha = radarAlpha),
                                        radius = radarRadius
                                    )
                                    drawCircle(
                                        color = Color.Red,
                                        radius = 3.5f
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "লাইভ ক্লাস",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (themeConfig.isDark) Color.White else Color(0xFF0F172A)
                            )
                        }
                    } else if (isSoon) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFF59E0B).copy(alpha = 0.25f))
                                .border(1.dp, Color(0xFFF59E0B), RoundedCornerShape(8.dp))
                                .padding(horizontal = 7.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "⏳ আসন্ন ক্লাস",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (themeConfig.isDark) Color(0xFFFDE68A) else Color(0xFF92400E)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(3.dp))

                // Location & Student count badges
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(themeConfig.cardBorder.copy(alpha = 0.35f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "📍 ${batch.location}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp, fontWeight = FontWeight.Medium),
                            color = themeConfig.textSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(themeConfig.cardBorder.copy(alpha = 0.35f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "👨‍🎓 ${studentCount}জন",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 11.sp, fontWeight = FontWeight.Medium),
                            color = themeConfig.textSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // 15-Minute Alarm Badge (Elevated Pill)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(themeConfig.primary.copy(alpha = 0.14f))
                        .border(0.8.dp, themeConfig.primary.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 7.dp, vertical = 3.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.NotificationsActive,
                            contentDescription = null,
                            modifier = Modifier.size(12.dp),
                            tint = themeConfig.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "অ্যালার্ম: $reminderTimeStr (১৫মি পূর্বে)",
                            fontSize = 10.5.sp,
                            fontWeight = FontWeight.Bold,
                            color = themeConfig.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Right Action Column (Floating Glass Call & Chevron)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onCall,
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(themeConfig.primary.copy(alpha = 0.18f))
                        .border(1.dp, themeConfig.primary.copy(alpha = 0.4f), CircleShape)
                ) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Call",
                        tint = themeConfig.primary,
                        modifier = Modifier.size(19.dp)
                    )
                }

                Spacer(modifier = Modifier.width(2.dp))

                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "Details",
                    tint = themeConfig.textSecondary.copy(alpha = 0.7f),
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
