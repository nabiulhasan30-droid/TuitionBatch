package com.tuitionbatch.app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuitionbatch.app.data.local.entity.StudentEntity
import com.tuitionbatch.app.ui.components.EmptyPlaceholder
import com.tuitionbatch.app.ui.components.ThemeBackground
import com.tuitionbatch.app.ui.theme.AppThemeConfig
import com.tuitionbatch.app.ui.theme.EmeraldGreen
import com.tuitionbatch.app.ui.theme.ThemeManager
import com.tuitionbatch.app.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentsScreen(
    viewModel: MainViewModel,
    themeConfig: AppThemeConfig = ThemeManager.getThemeConfig(0),
    onBack: () -> Unit = {},
    onOpenBatchDetail: (Long) -> Unit
) {
    val context = LocalContext.current
    val students by viewModel.allStudents.collectAsState()
    val batches by viewModel.allBatches.collectAsState()
    val batchMap = remember(batches) { batches.associateBy { it.id } }

    var searchQuery by remember { mutableStateOf("") }
    var showAddStudentDialog by remember { mutableStateOf(false) }
    var studentToEdit by remember { mutableStateOf<StudentEntity?>(null) }
    var studentToDelete by remember { mutableStateOf<StudentEntity?>(null) }

    val filteredStudents = remember(students, searchQuery) {
        if (searchQuery.isBlank()) students
        else students.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
                    it.rollNumber.contains(searchQuery, ignoreCase = true) ||
                    it.studentPhone.contains(searchQuery) ||
                    it.guardianPhone.contains(searchQuery) ||
                    (batchMap[it.batchId]?.name?.contains(searchQuery, ignoreCase = true) == true)
        }
    }

    ThemeBackground(themeConfig = themeConfig) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("শিক্ষার্থী তালিকা ও ডিরেক্ট কল", fontWeight = FontWeight.Bold, color = themeConfig.textColor) },
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
                    onClick = { showAddStudentDialog = true },
                    icon = { Icon(Icons.Default.PersonAdd, contentDescription = null) },
                    text = { Text("নতুন শিক্ষার্থী") },
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
                    text = "সকল শিক্ষার্থী",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = themeConfig.textColor
                )
                Text(
                    text = "অভিভাবক ও শিক্ষার্থীদের ফোন নম্বর এবং বিবরণ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = themeConfig.subtextColor
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("নাম, রোল বা মোবাইল নম্বর দিয়ে খুঁজুন...", color = themeConfig.subtextColor.copy(alpha = 0.7f)) },
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

                if (filteredStudents.isEmpty()) {
                    EmptyPlaceholder(
                        message = if (searchQuery.isBlank()) "এখনও কোনো শিক্ষার্থী যোগ করা হয়নি। নিচে '+ নতুন শিক্ষার্থী' বাটনে ক্লিক করুন!"
                        else "কোনো শিক্ষার্থী খুঁজে পাওয়া যায়নি।",
                        icon = Icons.Default.School,
                        themeConfig = themeConfig
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 90.dp)
                    ) {
                        items(filteredStudents) { student ->
                            val batch = batchMap[student.batchId]
                            val batchName = batch?.name ?: "অজানা ব্যাচ"
                            val batchColor = try {
                                Color(android.graphics.Color.parseColor(batch?.colorHex ?: "#1E40AF"))
                            } catch (e: Exception) {
                                themeConfig.primary
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(themeConfig.cardBackground)
                                    .border(1.2.dp, themeConfig.cardBorderBrush, RoundedCornerShape(18.dp))
                            ) {
                                // Top specular highlight
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(1.5.dp)
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(Color.Transparent, themeConfig.primary.copy(alpha = 0.5f), Color.Transparent)
                                            )
                                        )
                                )

                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(44.dp)
                                                .clip(CircleShape)
                                                .background(batchColor.copy(alpha = 0.2f))
                                                .border(1.2.dp, batchColor.copy(alpha = 0.7f), CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = student.name.take(1).uppercase(),
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = batchColor
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = student.name,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = themeConfig.textColor
                                            )
                                            Text(
                                                text = "রোল: ${student.rollNumber.ifBlank { "N/A" }} • $batchName",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = themeConfig.subtextColor
                                            )
                                        }

                                        IconButton(onClick = { studentToEdit = student }, modifier = Modifier.size(32.dp)) {
                                            Icon(Icons.Default.Edit, contentDescription = "Edit", tint = themeConfig.subtextColor, modifier = Modifier.size(18.dp))
                                        }
                                        IconButton(onClick = { studentToDelete = student }, modifier = Modifier.size(32.dp)) {
                                            Icon(
                                                Icons.Default.DeleteOutline,
                                                contentDescription = "Delete",
                                                tint = MaterialTheme.colorScheme.error,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    // Phone numbers and 1-click actions
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        if (student.studentPhone.isNotBlank()) {
                                            AssistChip(
                                                onClick = {
                                                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${student.studentPhone}"))
                                                    context.startActivity(intent)
                                                },
                                                label = { Text("ছাত্র: ${student.studentPhone}", fontSize = 11.sp, color = themeConfig.textColor) },
                                                leadingIcon = {
                                                    Icon(Icons.Default.Phone, contentDescription = null, tint = EmeraldGreen, modifier = Modifier.size(14.dp))
                                                },
                                                colors = AssistChipDefaults.assistChipColors(containerColor = themeConfig.cardBackground.copy(alpha = 0.6f)),
                                                border = BorderStroke(1.dp, themeConfig.cardBorder)
                                            )
                                        }

                                        if (student.guardianPhone.isNotBlank()) {
                                            AssistChip(
                                                onClick = {
                                                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${student.guardianPhone}"))
                                                    context.startActivity(intent)
                                                },
                                                label = { Text("অভিভাবক: ${student.guardianPhone}", fontSize = 11.sp, color = themeConfig.textColor) },
                                                leadingIcon = {
                                                    Icon(Icons.Default.ContactPhone, contentDescription = null, tint = themeConfig.primary, modifier = Modifier.size(14.dp))
                                                },
                                                colors = AssistChipDefaults.assistChipColors(containerColor = themeConfig.cardBackground.copy(alpha = 0.6f)),
                                                border = BorderStroke(1.dp, themeConfig.cardBorder)
                                            )
                                        }
                                    }
                                }
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
            onDismiss = { showAddStudentDialog = false },
            onSave = { newStudent ->
                viewModel.insertStudent(newStudent)
                showAddStudentDialog = false
            }
        )
    }

    if (studentToEdit != null) {
        AddEditStudentDialog(
            initialStudent = studentToEdit,
            batches = batches,
            onDismiss = { studentToEdit = null },
            onSave = { updated ->
                viewModel.updateStudent(updated)
                studentToEdit = null
            }
        )
    }

    if (studentToDelete != null) {
        AlertDialog(
            onDismissRequest = { studentToDelete = null },
            title = { Text("শিক্ষার্থী অপসারণ") },
            text = { Text("আপনি কি নিশ্চিতভাবে '${studentToDelete?.name}' শিক্ষার্থীকে তালিকা থেকে বাদ দিতে চান?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        studentToDelete?.let { viewModel.deleteStudent(it) }
                        studentToDelete = null
                    }
                ) {
                    Text("মুছে ফেলুন", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { studentToDelete = null }) {
                    Text("বাতিল")
                }
            }
        )
    }
}
