package com.tuitionbatch.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuitionbatch.app.data.local.entity.BatchEntity
import com.tuitionbatch.app.notification.AlarmScheduler
import com.tuitionbatch.app.ui.components.EmptyPlaceholder
import com.tuitionbatch.app.ui.components.StatCard
import com.tuitionbatch.app.ui.theme.*
import com.tuitionbatch.app.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DashboardScreen(
    viewModel: MainViewModel,
    onNavigateToBatches: () -> Unit,
    onNavigateToStudents: () -> Unit,
    onNavigateToFees: () -> Unit,
    onOpenBatchDetail: (Long) -> Unit
) {
    val batches by viewModel.allBatches.collectAsState()
    val activeBatches by viewModel.activeBatches.collectAsState()
    val students by viewModel.allStudents.collectAsState()
    val totalStudents by viewModel.totalActiveStudents.collectAsState()
    val todayClasses by viewModel.todayClasses.collectAsState()
    val (expectedFee, collectedFee) = viewModel.thisMonthFeeSummary.collectAsState().value

    var showAddBatchDialog by remember { mutableStateOf(false) }
    var showAddStudentDialog by remember { mutableStateOf(false) }
    var showAddFeeDialog by remember { mutableStateOf(false) }

    val todayDateFormatted = remember {
        val sdf = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH)
        sdf.format(System.currentTimeMillis())
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Header
        item {
            Column {
                Text(
                    text = "Tuition Batch Manager",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "ড্যাশবোর্ড ও রুটিন",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = todayDateFormatted,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // 15-Minute Reminder Status Banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                listOf(RoyalNavy, OceanBlue)
                            )
                        )
                        .padding(18.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "স্মার্ট ১৫-মিনিট নোটিফিকেশন অ্যালার্ম",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "প্রতিটি ব্যাচের পড়ার ঠিক ১৫ মিনিট আগে আপনি অ্যালার্ট ও নোটিফিকেশন পাবেন।",
                                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }
        }

        // Stats Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "মোট ব্যাচ",
                        value = "${activeBatches.size}টি",
                        subtitle = "সক্রিয় ব্যাচ",
                        icon = Icons.Default.Groups,
                        iconColor = OceanBlue,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigateToBatches() }
                    )
                    StatCard(
                        title = "স্মার্ট অ্যালার্ম",
                        value = "১৫মি পূর্বে",
                        subtitle = "স্বয়ংক্রিয় নোটিফিকেশন",
                        icon = Icons.Default.NotificationsActive,
                        iconColor = EmeraldGreen,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigateToBatches() }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "আজকের ক্লাস",
                        value = "${todayClasses.size}টি",
                        subtitle = "রুটিন অনুযায়ী",
                        icon = Icons.Default.EventNote,
                        iconColor = AmberGold,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "চলতি মাসের ফি",
                        value = "₹${collectedFee.toInt()}",
                        subtitle = "আদায়কৃত (বকেয়া: ₹${(expectedFee - collectedFee).coerceAtLeast(0.0).toInt()})",
                        icon = Icons.Default.AccountBalanceWallet,
                        iconColor = RoyalNavy,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigateToFees() }
                    )
                }
            }
        }

        // Quick Action Buttons
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { showAddBatchDialog = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("+ ব্যাচ", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                    onClick = { showAddStudentDialog = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    enabled = activeBatches.isNotEmpty()
                ) {
                    Icon(Icons.Default.PersonAdd, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("+ শিক্ষার্থী", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                    onClick = { showAddFeeDialog = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    enabled = students.isNotEmpty()
                ) {
                    Icon(Icons.Default.Payment, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("+ ফি আদায়", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Section: Today's Routine
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "আজকের পড়ার রুটিন (${todayClasses.size}টি ব্যাচ)",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (todayClasses.isEmpty()) {
            item {
                EmptyPlaceholder(
                    message = "আজকে কোনো নির্ধারিত ব্যাচ নেই! নিশ্চিন্তে বিশ্রাম নিন বা নতুন রুটিন যোগ করুন।",
                    icon = Icons.Default.CheckCircleOutline
                )
            }
        } else {
            items(todayClasses) { batch ->
                TodayBatchCard(
                    batch = batch,
                    onClick = { onOpenBatchDetail(batch.id) }
                )
            }
        }
    }

    if (showAddBatchDialog) {
        AddEditBatchDialog(
            onDismiss = { showAddBatchDialog = false },
            onSave = { newBatch ->
                viewModel.insertBatch(newBatch)
                showAddBatchDialog = false
            }
        )
    }

    if (showAddStudentDialog) {
        AddEditStudentDialog(
            batches = activeBatches,
            onDismiss = { showAddStudentDialog = false },
            onSave = { newStudent ->
                viewModel.insertStudent(newStudent)
                showAddStudentDialog = false
            }
        )
    }

    if (showAddFeeDialog) {
        AddFeeDialog(
            students = students,
            batches = activeBatches,
            onDismiss = { showAddFeeDialog = false },
            onSave = { studentId, batchId, amount, monthYear, method, note ->
                viewModel.recordFee(studentId, batchId, amount, monthYear, method, note)
                showAddFeeDialog = false
            }
        )
    }
}

@Composable
fun TodayBatchCard(
    batch: BatchEntity,
    onClick: () -> Unit
) {
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
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(60.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(batchColor)
            )
            Spacer(modifier = Modifier.width(14.dp))
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
                        modifier = Modifier.size(15.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = timeStr,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "🔔 ১৫মি পূর্বে অ্যালার্ম",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = "Details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
