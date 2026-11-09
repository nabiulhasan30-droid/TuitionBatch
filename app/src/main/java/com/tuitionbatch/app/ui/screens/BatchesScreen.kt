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
import com.tuitionbatch.app.ui.components.DayChip
import com.tuitionbatch.app.ui.components.EmptyPlaceholder
import com.tuitionbatch.app.ui.components.ThemeBackground
import com.tuitionbatch.app.ui.theme.AppThemeConfig
import com.tuitionbatch.app.ui.theme.ThemeManager
import com.tuitionbatch.app.ui.viewmodel.MainViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatchesScreen(
    viewModel: MainViewModel,
    themeConfig: com.tuitionbatch.app.ui.theme.AppThemeConfig = com.tuitionbatch.app.ui.theme.ThemeManager.getThemeConfig(0),
    onBack: () -> Unit = {},
    onOpenBatchDetail: (Long) -> Unit
) {
    val batches by viewModel.allBatches.collectAsState()
    val allStudents by viewModel.allStudents.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var showAddBatchDialog by remember { mutableStateOf(false) }
    var batchToEdit by remember { mutableStateOf<BatchEntity?>(null) }
    var batchToDelete by remember { mutableStateOf<BatchEntity?>(null) }

    val filteredBatches = remember(batches, searchQuery) {
        if (searchQuery.isBlank()) batches
        else batches.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
                    it.subject.contains(searchQuery, ignoreCase = true) ||
                    it.grade.contains(searchQuery, ignoreCase = true)
        }
    }

    ThemeBackground(themeConfig = themeConfig) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("সকল ব্যাচ ও শিডিউল", fontWeight = FontWeight.Bold, color = themeConfig.textColor) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = themeConfig.textColor)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { showAddBatchDialog = true },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    text = { Text("নতুন ব্যাচ") },
                    shape = RoundedCornerShape(16.dp),
                    containerColor = themeConfig.primary,
                    contentColor = if (themeConfig.isDark) Color.Black else Color.White
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "সকল টিউশন ব্যাচ (${batches.size}টি)",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = themeConfig.textColor
                )
                Text(
                    text = "আপনার সকল প্রাইভেট ব্যাচ ও রুটিন শিডিউল",
                    style = MaterialTheme.typography.bodyMedium,
                    color = themeConfig.subtextColor
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("ব্যাচের নাম বা বিষয় দিয়ে খুঁজুন...", color = themeConfig.subtextColor.copy(alpha = 0.7f)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = themeConfig.primary) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = themeConfig.subtextColor)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = themeConfig.textColor,
                        unfocusedTextColor = themeConfig.textColor,
                        focusedBorderColor = themeConfig.primary,
                        unfocusedBorderColor = themeConfig.cardBorder,
                        focusedContainerColor = themeConfig.cardBackground.copy(alpha = 0.5f),
                        unfocusedContainerColor = themeConfig.cardBackground.copy(alpha = 0.3f)
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                if (filteredBatches.isEmpty()) {
                    EmptyPlaceholder(
                        message = if (searchQuery.isBlank()) "এখনও কোনো ব্যাচ তৈরি করা হয়নি। নিচে '+ নতুন ব্যাচ' বাটনে চাপ দিন!"
                        else "কোনো ব্যাচ খুঁজে পাওয়া যায়নি।",
                        icon = Icons.Default.Groups,
                        themeConfig = themeConfig
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 90.dp)
                    ) {
                        items(filteredBatches) { batch ->
                            val studentCount = allStudents.count { it.batchId == batch.id && it.isActive }
                            BatchListItemCard(
                                batch = batch,
                                studentCount = studentCount,
                                themeConfig = themeConfig,
                                onClick = { onOpenBatchDetail(batch.id) },
                                onEdit = { batchToEdit = batch },
                                onDelete = { batchToDelete = batch },
                                onToggleActive = {
                                    viewModel.updateBatch(batch.copy(isActive = !batch.isActive))
                                }
                            )
                        }
                    }
                }
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

    if (batchToEdit != null) {
        AddEditBatchDialog(
            initialBatch = batchToEdit,
            onDismiss = { batchToEdit = null },
            onSave = { updated ->
                viewModel.updateBatch(updated)
                batchToEdit = null
            }
        )
    }

    if (batchToDelete != null) {
        AlertDialog(
            onDismissRequest = { batchToDelete = null },
            title = { Text("ব্যাচ মুছে ফেলা") },
            text = { Text("আপনি কি নিশ্চিতভাবে '${batchToDelete?.name}' ব্যাচটি মুছে ফেলতে চান? এতে এই ব্যাচের সকল শিক্ষার্থী ও হাজিরা মুছে যাবে।") },
            confirmButton = {
                TextButton(
                    onClick = {
                        batchToDelete?.let { viewModel.deleteBatch(it) }
                        batchToDelete = null
                    }
                ) {
                    Text("মুছে ফেলুন", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { batchToDelete = null }) {
                    Text("বাতিল")
                }
            }
        )
    }
}

@Composable
fun BatchListItemCard(
    batch: BatchEntity,
    studentCount: Int,
    themeConfig: AppThemeConfig = ThemeManager.getThemeConfig(0),
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleActive: () -> Unit
) {
    val batchColor = try {
        Color(android.graphics.Color.parseColor(batch.colorHex))
    } catch (e: Exception) {
        themeConfig.primary
    }

    val timeStr = AlarmScheduler.formatTime(batch.startTimeHour, batch.startTimeMinute)
    val allWeekDays = listOf(
        Calendar.SATURDAY, Calendar.SUNDAY, Calendar.MONDAY,
        Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(themeConfig.cardBackground)
            .border(1.2.dp, themeConfig.cardBorderBrush, RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        // Specular top highlight
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(Color.Transparent, themeConfig.primary.copy(alpha = 0.6f), Color.Transparent)
                    )
                )
        )

        Column(modifier = Modifier.padding(16.dp)) {
            // Top row: Color tag, Title, Subject, Menu/Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(batchColor)
                        .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = batch.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = themeConfig.textColor
                    )
                    Text(
                        text = "${batch.subject} • ${batch.grade}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = themeConfig.subtextColor
                    )
                }

                // Edit & Delete icons
                IconButton(onClick = onEdit, modifier = Modifier.size(32.dp)) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = themeConfig.subtextColor, modifier = Modifier.size(18.dp))
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                    Icon(
                        Icons.Default.DeleteOutline,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Days chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                allWeekDays.forEach { dayNum ->
                    DayChip(
                        dayNumber = dayNum,
                        isSelected = batch.daysOfWeek.contains(dayNum),
                        themeConfig = themeConfig
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Time, Fee, Students Info Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = themeConfig.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$timeStr (🔔 -১৫মি)",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = themeConfig.primary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = themeConfig.subtextColor
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = batch.location.ifBlank { "ব্যাচ রুম" },
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        color = themeConfig.subtextColor
                    )
                }

                Text(
                    text = "ফি: ₹${batch.monthlyFee.toInt()}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = themeConfig.textColor
                )
            }
        }
    }
}
