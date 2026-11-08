package com.tuitionbatch.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tuitionbatch.app.data.local.entity.BatchEntity
import com.tuitionbatch.app.notification.AlarmScheduler
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditBatchDialog(
    initialBatch: BatchEntity? = null,
    onDismiss: () -> Unit,
    onSave: (BatchEntity) -> Unit
) {
    var name by remember { mutableStateOf(initialBatch?.name ?: "") }
    var subject by remember { mutableStateOf(initialBatch?.subject ?: "") }
    var grade by remember { mutableStateOf(initialBatch?.grade ?: "") }
    var monthlyFeeStr by remember { mutableStateOf(initialBatch?.monthlyFee?.let { if (it > 0) it.toInt().toString() else "" } ?: "") }
    var location by remember { mutableStateOf(initialBatch?.location ?: "অফলাইন / বাসা") }

    var selectedDays by remember {
        mutableStateOf(initialBatch?.daysOfWeek ?: listOf(Calendar.SATURDAY, Calendar.MONDAY, Calendar.WEDNESDAY))
    }

    var hour by remember { mutableIntStateOf(initialBatch?.startTimeHour ?: 8) }
    var minute by remember { mutableIntStateOf(initialBatch?.startTimeMinute ?: 0) }
    var selectedColor by remember { mutableStateOf(initialBatch?.colorHex ?: "#1E40AF") }

    val presetColors = listOf("#1E40AF", "#0284C7", "#059669", "#D97706", "#DC2626", "#7C3AED", "#DB2777")
    val daysList = listOf(
        Pair(Calendar.SATURDAY, "শনি"),
        Pair(Calendar.SUNDAY, "রবি"),
        Pair(Calendar.MONDAY, "সোম"),
        Pair(Calendar.TUESDAY, "মঙ্গল"),
        Pair(Calendar.WEDNESDAY, "বুধ"),
        Pair(Calendar.THURSDAY, "বৃহঃ"),
        Pair(Calendar.FRIDAY, "শুক্র")
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (initialBatch == null) "নতুন ব্যাচ তৈরি করুন" else "ব্যাচ তথ্য পরিবর্তন",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Batch Name
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("ব্যাচের নাম (যেমন: Class 10 Physics Morning)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = { Text("বিষয় (যেমন: পদার্থবিজ্ঞান)") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = grade,
                        onValueChange = { grade = it },
                        label = { Text("ক্লাস (যেমন: ১০ম)") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Days Selection
                Text(
                    text = "পড়ার দিনসমূহ:",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    daysList.forEach { (calDay, label) ->
                        val isSelected = selectedDays.contains(calDay)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .clickable {
                                    selectedDays = if (isSelected) {
                                        selectedDays - calDay
                                    } else {
                                        selectedDays + calDay
                                    }
                                }
                                .padding(horizontal = 9.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Class Time with 15-Minute reminder indicator
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Alarm,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "পড়ার সময়: ${AlarmScheduler.formatTime(hour, minute)}",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "🔔 পড়ার ১৫ মিনিট আগে স্বয়ংক্রিয়ভাবে নোটিফিকেশন আসবে।",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Quick Hour & Minute Selector
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("ঘণ্টা:", style = MaterialTheme.typography.labelSmall)
                            Slider(
                                value = hour.toFloat(),
                                onValueChange = { hour = it.toInt() },
                                valueRange = 6f..22f,
                                steps = 15,
                                modifier = Modifier.weight(1f)
                            )
                            Text("${hour}:00", fontWeight = FontWeight.Bold)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("মিনিট:", style = MaterialTheme.typography.labelSmall)
                            listOf(0, 15, 30, 45).forEach { minVal ->
                                FilterChip(
                                    selected = minute == minVal,
                                    onClick = { minute = minVal },
                                    label = { Text("${minVal}মি") }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = monthlyFeeStr,
                        onValueChange = { monthlyFeeStr = it },
                        label = { Text("মাসিক ফি (₹)") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("স্থান / লিংক") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Color Tag Picker
                Text(
                    text = "ব্যাচ কালার কোড:",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 13.sp),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    presetColors.forEach { hex ->
                        val isPicked = selectedColor == hex
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(Color(android.graphics.Color.parseColor(hex)))
                                .clickable { selectedColor = hex }
                                .then(
                                    if (isPicked) Modifier.border(3.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
                                    else Modifier
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Save Button
                Button(
                    onClick = {
                        if (name.isNotBlank()) {
                            val fee = monthlyFeeStr.toDoubleOrNull() ?: 0.0
                            val batch = (initialBatch ?: BatchEntity(
                                name = name,
                                subject = subject,
                                grade = grade,
                                daysOfWeek = selectedDays,
                                startTimeHour = hour,
                                startTimeMinute = minute,
                                monthlyFee = fee,
                                location = location,
                                colorHex = selectedColor
                            )).copy(
                                name = name,
                                subject = subject,
                                grade = grade,
                                daysOfWeek = selectedDays,
                                startTimeHour = hour,
                                startTimeMinute = minute,
                                monthlyFee = fee,
                                location = location,
                                colorHex = selectedColor
                            )
                            onSave(batch)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    enabled = name.isNotBlank() && selectedDays.isNotEmpty()
                ) {
                    Text(
                        text = if (initialBatch == null) "ব্যাচ সংরক্ষণ করুন" else "আপডেট সংরক্ষণ করুন",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
