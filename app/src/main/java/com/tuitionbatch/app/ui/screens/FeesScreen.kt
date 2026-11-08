package com.tuitionbatch.app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.tuitionbatch.app.ui.components.EmptyPlaceholder
import com.tuitionbatch.app.ui.components.StatCard
import com.tuitionbatch.app.ui.components.ThemeBackground
import com.tuitionbatch.app.ui.theme.AppThemeConfig
import com.tuitionbatch.app.ui.theme.EmeraldGreen
import com.tuitionbatch.app.ui.theme.ThemeManager
import com.tuitionbatch.app.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeesScreen(
    viewModel: MainViewModel,
    themeConfig: AppThemeConfig = ThemeManager.getThemeConfig(0),
    onBack: () -> Unit = {}
) {
    val feeRecords by viewModel.allFeeRecords.collectAsState()
    val students by viewModel.allStudents.collectAsState()
    val batches by viewModel.allBatches.collectAsState()

    val studentMap = remember(students) { students.associateBy { it.id } }
    val batchMap = remember(batches) { batches.associateBy { it.id } }

    val (expectedFee, collectedFee) = viewModel.thisMonthFeeSummary.collectAsState().value
    val dueFee = (expectedFee - collectedFee).coerceAtLeast(0.0)

    var showAddFeeDialog by remember { mutableStateOf(false) }

    ThemeBackground(themeConfig = themeConfig) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("মাসিক বেতন ও ফি হিসাব", fontWeight = FontWeight.Bold, color = themeConfig.textColor) },
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
                    onClick = { showAddFeeDialog = true },
                    icon = { Icon(Icons.Default.Payment, contentDescription = null) },
                    text = { Text("ফি আদায় করুন") },
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
                    text = "বেতন ও ফি হিসাব",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = themeConfig.textColor
                )
                Text(
                    text = "মাসিক আয়, বকেয়া এবং পেমেন্ট ট্র্যাকিং",
                    style = MaterialTheme.typography.bodyMedium,
                    color = themeConfig.subtextColor
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Financial Summary
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatCard(
                        title = "চলতি মাসে আদায়",
                        value = "₹${collectedFee.toInt()}",
                        subtitle = "মোট সংগৃহীত",
                        icon = Icons.Default.CheckCircle,
                        iconColor = EmeraldGreen,
                        themeConfig = themeConfig,
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        title = "বকেয়া ফি",
                        value = "₹${dueFee.toInt()}",
                        subtitle = "প্রত্যাশিত: ₹${expectedFee.toInt()}",
                        icon = Icons.Default.HourglassEmpty,
                        iconColor = Color(0xFFD97706),
                        themeConfig = themeConfig,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "সাম্প্রতিক পেমেন্ট হিস্ট্রি (${feeRecords.size}টি রেকর্ড)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = themeConfig.textColor
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (feeRecords.isEmpty()) {
                    EmptyPlaceholder(
                        message = "এখনও কোনো পেমেন্ট রেকর্ড নেই। নিচে 'ফি আদায় করুন' বাটনে ক্লিক করে বেতন সংগ্রহ এন্ট্রি করুন!",
                        icon = Icons.Default.ReceiptLong,
                        themeConfig = themeConfig
                    )
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 90.dp)
                    ) {
                        items(feeRecords) { record ->
                            val student = studentMap[record.studentId]
                            val batch = batchMap[record.batchId]
                            val dateFormatted = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.ENGLISH).format(record.paymentDateMillis)

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

                                Row(
                                    modifier = Modifier
                                        .padding(14.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = student?.name ?: "অজানা শিক্ষার্থী",
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = themeConfig.textColor
                                        )
                                        Text(
                                            text = "${batch?.name ?: "ব্যাচ"} • মাস: ${record.monthYear}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = themeConfig.subtextColor
                                        )
                                        Text(
                                            text = "তারিখ: $dateFormatted",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = themeConfig.subtextColor.copy(alpha = 0.8f)
                                        )
                                        if (record.receiptNote.isNotBlank()) {
                                            Text(
                                                text = "নোট: ${record.receiptNote}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = themeConfig.primary
                                            )
                                        }
                                    }

                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            text = "₹${record.amountPaid.toInt()}",
                                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                                            fontWeight = FontWeight.ExtraBold,
                                            color = EmeraldGreen
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        AssistChip(
                                            onClick = {},
                                            label = { Text(record.paymentMethod, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = themeConfig.textColor) },
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

    if (showAddFeeDialog) {
        AddFeeDialog(
            students = students,
            batches = batches,
            onDismiss = { showAddFeeDialog = false },
            onSave = { studentId, batchId, amount, monthYear, method, note ->
                viewModel.recordFee(studentId, batchId, amount, monthYear, method, note)
                showAddFeeDialog = false
            }
        )
    }
}
