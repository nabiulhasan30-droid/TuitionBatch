package com.tuitionbatch.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuitionbatch.app.notification.AlarmScheduler
import com.tuitionbatch.app.ui.components.EmptyPlaceholder
import com.tuitionbatch.app.ui.viewmodel.MainViewModel
import java.util.Calendar

@Composable
fun RoutineScreen(
    viewModel: MainViewModel,
    onOpenBatchDetail: (Long) -> Unit
) {
    val activeBatches by viewModel.activeBatches.collectAsState()

    val daysOfWeekList = listOf(
        Pair(Calendar.SATURDAY, "শনিবার"),
        Pair(Calendar.SUNDAY, "রবিবার"),
        Pair(Calendar.MONDAY, "সোমবার"),
        Pair(Calendar.TUESDAY, "মঙ্গলবার"),
        Pair(Calendar.WEDNESDAY, "বুধবার"),
        Pair(Calendar.THURSDAY, "বৃহস্পতিবার"),
        Pair(Calendar.FRIDAY, "শুক্রবার")
    )

    val currentCalendarDay = remember { Calendar.getInstance().get(Calendar.DAY_OF_WEEK) }
    var selectedDay by remember { mutableIntStateOf(currentCalendarDay) }

    val dayBatches = remember(activeBatches, selectedDay) {
        activeBatches
            .filter { it.daysOfWeek.contains(selectedDay) }
            .sortedWith(compareBy({ it.startTimeHour }, { it.startTimeMinute }))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "সাপ্তাহিক টিউশন রুটিন",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "দিনের ভিত্তিতে সম্পূর্ণ পড়ার সময়সূচি",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Scrollable Day selector
        ScrollableTabRow(
            selectedTabIndex = daysOfWeekList.indexOfFirst { it.first == selectedDay }.coerceAtLeast(0),
            edgePadding = 0.dp,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            daysOfWeekList.forEach { (calDay, label) ->
                val isSelected = selectedDay == calDay
                Tab(
                    selected = isSelected,
                    onClick = { selectedDay = calDay },
                    text = {
                        Text(
                            text = label,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (dayBatches.isEmpty()) {
            EmptyPlaceholder(
                message = "${daysOfWeekList.find { it.first == selectedDay }?.second}-এ কোনো ব্যাচ নেই।",
                icon = Icons.Default.CalendarMonth
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 90.dp)
            ) {
                items(dayBatches) { batch ->
                    val batchColor = try {
                        Color(android.graphics.Color.parseColor(batch.colorHex))
                    } catch (e: Exception) {
                        MaterialTheme.colorScheme.primary
                    }
                    val timeStr = AlarmScheduler.formatTime(batch.startTimeHour, batch.startTimeMinute)

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenBatchDetail(batch.id) }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(14.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(4.dp)
                                    .height(50.dp)
                                    .clip(RoundedCornerShape(2.dp))
                                    .background(batchColor)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = batch.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${batch.subject} • ${batch.grade} • ${batch.location}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        Icons.Default.AccessTime,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp),
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = timeStr,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "🔔 -১৫মি অ্যালার্ম",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = "Open",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}
