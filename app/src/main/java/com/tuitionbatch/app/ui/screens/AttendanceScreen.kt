package com.tuitionbatch.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuitionbatch.app.ui.components.EmptyPlaceholder
import com.tuitionbatch.app.ui.theme.AppThemeConfig
import com.tuitionbatch.app.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(
    viewModel: MainViewModel,
    themeConfig: AppThemeConfig,
    onBack: () -> Unit
) {
    val batches by viewModel.activeBatches.collectAsState()
    var selectedBatchId by remember { mutableLongStateOf(batches.firstOrNull()?.id ?: 0L) }
    var batchDropdownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(batches) {
        if (selectedBatchId == 0L && batches.isNotEmpty()) {
            selectedBatchId = batches.first().id
        }
    }

    val students by viewModel.getStudentsForBatch(selectedBatchId).collectAsState(initial = emptyList())

    val todayMidnight = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    val attendanceRecords by viewModel.getAttendanceForDate(selectedBatchId, todayMidnight).collectAsState(initial = emptyList())
    val attendanceMap = remember(attendanceRecords) {
        attendanceRecords.associateBy { it.studentId }
    }

    com.tuitionbatch.app.ui.components.ThemeBackground(themeConfig = themeConfig) {
        Scaffold(
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("দৈনিক ডিজিটাল হাজিরা", fontWeight = FontWeight.Bold, color = themeConfig.textColor) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = themeConfig.textColor)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = androidx.compose.ui.graphics.Color.Transparent
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
            // Select Batch Dropdown
            ExposedDropdownMenuBox(
                expanded = batchDropdownExpanded,
                onExpandedChange = { batchDropdownExpanded = !batchDropdownExpanded }
            ) {
                val currentBatchName = batches.find { it.id == selectedBatchId }?.name ?: "ব্যাচ নির্বাচন করুন"
                OutlinedTextField(
                    value = currentBatchName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("ব্যাচ নির্বাচন করুন") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = batchDropdownExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = batchDropdownExpanded,
                    onDismissRequest = { batchDropdownExpanded = false }
                ) {
                    batches.forEach { batch ->
                        DropdownMenuItem(
                            text = { Text("${batch.name} (${batch.grade})") },
                            onClick = {
                                selectedBatchId = batch.id
                                batchDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Quick Mark All Present & Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val todayStr = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH).format(todayMidnight)
                Text(
                    text = todayStr,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                if (students.isNotEmpty()) {
                    Button(
                        onClick = {
                            viewModel.markAllPresent(selectedBatchId, students, todayMidnight)
                        },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("সবাইকে উপস্থিত করুন", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (students.isEmpty()) {
                EmptyPlaceholder(
                    message = "এই ব্যাচে কোনো শিক্ষার্থী নেই। প্রথমে শিক্ষার্থী যোগ করুন।",
                    icon = Icons.Default.FactCheck
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(students) { student ->
                        val currentStatus = attendanceMap[student.id]?.status ?: "UNMARKED"
                        AttendanceRow(
                            student = student,
                            status = currentStatus,
                            onStatusChange = { newStatus ->
                                viewModel.recordAttendance(selectedBatchId, student.id, todayMidnight, newStatus)
                            }
                        )
                    }
                }
            }
        }
    }
}
}
