package com.tuitionbatch.app.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuitionbatch.app.data.local.entity.AttendanceEntity
import com.tuitionbatch.app.data.local.entity.BatchEntity
import com.tuitionbatch.app.data.local.entity.NoteEntity
import com.tuitionbatch.app.data.local.entity.StudentEntity
import com.tuitionbatch.app.notification.AlarmScheduler
import com.tuitionbatch.app.ui.components.DayChip
import com.tuitionbatch.app.ui.components.EmptyPlaceholder
import com.tuitionbatch.app.ui.components.ThemeBackground
import com.tuitionbatch.app.ui.theme.AppThemeConfig
import com.tuitionbatch.app.ui.theme.EmeraldGreen
import com.tuitionbatch.app.ui.theme.RoyalNavy
import com.tuitionbatch.app.ui.theme.ThemeManager
import com.tuitionbatch.app.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BatchDetailScreen(
    batchId: Long,
    viewModel: MainViewModel,
    themeConfig: AppThemeConfig = ThemeManager.getThemeConfig(0),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val batches by viewModel.allBatches.collectAsState()
    val batch = batches.find { it.id == batchId }

    if (batch == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val students by viewModel.getStudentsForBatch(batchId).collectAsState(initial = emptyList())
    val notes by viewModel.getNotesForBatch(batchId).collectAsState(initial = emptyList())

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("শিক্ষার্থী (${students.size})", "দৈনিক হাজিরা", "পড়ার নোট ও কাজ")

    var showAddStudentDialog by remember { mutableStateOf(false) }
    var showAddNoteDialog by remember { mutableStateOf(false) }

    // Today midnight timestamp for attendance
    val todayMidnight = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    val attendanceRecords by viewModel.getAttendanceForDate(batchId, todayMidnight).collectAsState(initial = emptyList())
    val attendanceMap = remember(attendanceRecords) {
        attendanceRecords.associateBy { it.studentId }
    }

    val batchColor = try {
        Color(android.graphics.Color.parseColor(batch.colorHex))
    } catch (e: Exception) {
        RoyalNavy
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(batch.name, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        floatingActionButton = {
            when (selectedTabIndex) {
                0 -> {
                    FloatingActionButton(
                        onClick = { showAddStudentDialog = true },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = "Add Student")
                    }
                }
                2 -> {
                    FloatingActionButton(
                        onClick = { showAddNoteDialog = true },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ) {
                        Icon(Icons.Default.PostAdd, contentDescription = "Add Note")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            // Batch Info Header Card
            Card(
                shape = RoundedCornerShape(0.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "বিষয়: ${batch.subject} • ${batch.grade}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "স্থান: ${batch.location} • ফি: ৳${batch.monthlyFee.toInt()}/মাস",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // 15 Min Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "🔔 ১৫মি পূর্বে অ্যালার্ম",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Days & Time
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf(
                            Calendar.SATURDAY, Calendar.SUNDAY, Calendar.MONDAY,
                            Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY
                        ).forEach { dayNum ->
                            DayChip(
                                dayNumber = dayNum,
                                isSelected = batch.daysOfWeek.contains(dayNum)
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = AlarmScheduler.formatTime(batch.startTimeHour, batch.startTimeMinute),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Tab Selector
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            // Tab Content
            when (selectedTabIndex) {
                0 -> {
                    // Students List Tab
                    if (students.isEmpty()) {
                        EmptyPlaceholder(
                            message = "এই ব্যাচে কোনো শিক্ষার্থী নেই। নিচে '+' বাটনে ক্লিক করে শিক্ষার্থী যুক্ত করুন!",
                            icon = Icons.Default.School
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(students) { student ->
                                StudentCard(
                                    student = student,
                                    onCall = { phone ->
                                        if (phone.isNotBlank()) {
                                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                                            context.startActivity(intent)
                                        }
                                    },
                                    onDelete = {
                                        viewModel.deleteStudent(student)
                                    }
                                )
                            }
                        }
                    }
                }
                1 -> {
                    // Attendance Tab
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val todayStr = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(todayMidnight)
                            Text(
                                text = "আজকের হাজিরা ($todayStr)",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            TextButton(
                                onClick = {
                                    viewModel.markAllPresent(batchId, students, todayMidnight)
                                }
                            ) {
                                Text("সবাইকে উপস্থিত করুন", fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        if (students.isEmpty()) {
                            EmptyPlaceholder(
                                message = "হাজিরা নেওয়ার জন্য প্রথমে শিক্ষার্থী যুক্ত করুন।",
                                icon = Icons.Default.PersonOutline
                            )
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(bottom = 80.dp)
                            ) {
                                items(students) { student ->
                                    val currentStatus = attendanceMap[student.id]?.status ?: "UNMARKED"
                                    AttendanceRow(
                                        student = student,
                                        status = currentStatus,
                                        onStatusChange = { newStatus ->
                                            viewModel.recordAttendance(batchId, student.id, todayMidnight, newStatus)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                2 -> {
                    // Notes & Homework Tab
                    if (notes.isEmpty()) {
                        EmptyPlaceholder(
                            message = "এখনও কোনো পড়া বা বাড়ির কাজের নোট নেই। নিচে '+' বাটনে ক্লিক করে আজকের ক্লাসের পড়া যোগ করুন!",
                            icon = Icons.Default.MenuBook
                        )
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(notes) { note ->
                                NoteCard(
                                    note = note,
                                    onDelete = { viewModel.deleteNote(note) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddStudentDialog) {
        AddEditStudentDialog(
            batches = batches,
            preselectedBatchId = batchId,
            onDismiss = { showAddStudentDialog = false },
            onSave = { newStudent ->
                viewModel.insertStudent(newStudent)
                showAddStudentDialog = false
            }
        )
    }

    if (showAddNoteDialog) {
        AddNoteDialog(
            onDismiss = { showAddNoteDialog = false },
            onSave = { topic, hw, exam ->
                viewModel.addNote(batchId, topic, hw, exam)
                showAddNoteDialog = false
            }
        )
    }
}

@Composable
fun StudentCard(
    student: StudentEntity,
    onCall: (String) -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
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
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = student.name.take(1).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "রোল: ${student.rollNumber.ifBlank { "N/A" }} • ${student.institution.ifBlank { "প্রতিষ্ঠান উল্লেখ নেই" }}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (student.guardianPhone.isNotBlank()) {
                    Text(
                        text = "অভিভাবক: ${student.guardianName.ifBlank { "অভিভাবক" }} (${student.guardianPhone})",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Call buttons
            if (student.studentPhone.isNotBlank()) {
                IconButton(onClick = { onCall(student.studentPhone) }) {
                    Icon(Icons.Default.Phone, contentDescription = "Call Student", tint = EmeraldGreen)
                }
            }
            if (student.guardianPhone.isNotBlank()) {
                IconButton(onClick = { onCall(student.guardianPhone) }) {
                    Icon(Icons.Default.ContactPhone, contentDescription = "Call Guardian", tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun AttendanceRow(
    student: StudentEntity,
    status: String,
    onStatusChange: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 15.sp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "রোল: ${student.rollNumber.ifBlank { "N/A" }}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                // Present Button
                FilterChip(
                    selected = status == "PRESENT",
                    onClick = { onStatusChange("PRESENT") },
                    label = { Text("উপস্থিত", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = EmeraldGreen,
                        selectedLabelColor = Color.White
                    )
                )
                // Absent Button
                FilterChip(
                    selected = status == "ABSENT",
                    onClick = { onStatusChange("ABSENT") },
                    label = { Text("অনুপস্থিত", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.error,
                        selectedLabelColor = Color.White
                    )
                )
                // Late Button
                FilterChip(
                    selected = status == "LATE",
                    onClick = { onStatusChange("LATE") },
                    label = { Text("দেরি", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFD97706),
                        selectedLabelColor = Color.White
                    )
                )
            }
        }
    }
}

@Composable
fun NoteCard(
    note: NoteEntity,
    onDelete: () -> Unit
) {
    val dateStr = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).format(note.dateMillis)

    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "তারিখ: $dateStr",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
                    Icon(
                        Icons.Default.DeleteOutline,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "📖 ক্লাসে যা পড়ানো হয়েছে:",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = note.topicCovered,
                style = MaterialTheme.typography.bodyMedium
            )

            if (note.homeworkAssigned.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "📝 বাড়ির কাজ (HW):",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                )
                Text(
                    text = note.homeworkAssigned,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (note.examInfo.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "🎯 আসন্ন পরীক্ষা / ক্লাস টেস্ট:",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
                )
                Text(
                    text = note.examInfo,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onSave: (topic: String, homework: String, exam: String) -> Unit
) {
    var topic by remember { mutableStateOf("") }
    var homework by remember { mutableStateOf("") }
    var exam by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("আজকের ক্লাসের পড়া ও বাড়ির কাজ") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = topic,
                    onValueChange = { topic = it },
                    label = { Text("আজকে যা পড়ানো হলো (টপিক / অধ্যায়)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = homework,
                    onValueChange = { homework = it },
                    label = { Text("বাড়ির কাজ (HW)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = exam,
                    onValueChange = { exam = it },
                    label = { Text("আসন্ন পরীক্ষা / টেস্ট (ঐচ্ছিক)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (topic.isNotBlank()) {
                        onSave(topic, homework, exam)
                    }
                },
                enabled = topic.isNotBlank()
            ) {
                Text("সংরক্ষণ করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}
