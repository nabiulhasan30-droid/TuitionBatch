package com.tuitionbatch.app.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tuitionbatch.app.ui.components.EmptyPlaceholder
import com.tuitionbatch.app.ui.components.StatCard
import com.tuitionbatch.app.ui.theme.EmeraldGreen
import com.tuitionbatch.app.ui.theme.OceanBlue
import com.tuitionbatch.app.ui.theme.RoyalNavy
import com.tuitionbatch.app.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun FeesScreen(
    viewModel: MainViewModel
) {
    val feeRecords by viewModel.allFeeRecords.collectAsState()
    val students by viewModel.allStudents.collectAsState()
    val batches by viewModel.allBatches.collectAsState()

    val studentMap = remember(students) { students.associateBy { it.id } }
    val batchMap = remember(batches) { batches.associateBy { it.id } }

    val (expectedFee, collectedFee) = viewModel.thisMonthFeeSummary.collectAsState().value
    val dueFee = (expectedFee - collectedFee).coerceAtLeast(0.0)

    var showAddFeeDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddFeeDialog = true },
                icon = { Icon(Icons.Default.Payment, contentDescription = null) },
                text = { Text("ফি আদায় করুন") },
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "বেতন ও ফি হিসাব",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "মাসিক আয়, বকেয়া এবং পেমেন্ট ট্র্যাকিং",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Financial Summary
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                StatCard(
                    title = "চলতি মাসে আদায়",
                    value = "৳${collectedFee.toInt()}",
                    subtitle = "মোট সংগৃহীত",
                    icon = Icons.Default.CheckCircle,
                    iconColor = EmeraldGreen,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "বকেয়া ফি",
                    value = "৳${dueFee.toInt()}",
                    subtitle = "প্রত্যাশিত: ৳${expectedFee.toInt()}",
                    icon = Icons.Default.HourglassEmpty,
                    iconColor = Color(0xFFD97706),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "সাম্প্রতিক পেমেন্ট হিস্ট্রি (${feeRecords.size}টি রেকর্ড)",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (feeRecords.isEmpty()) {
                EmptyPlaceholder(
                    message = "এখনও কোনো পেমেন্ট রেকর্ড নেই। নিচে 'ফি আদায় করুন' বাটনে ক্লিক করে বেতন সংগ্রহ এন্ট্রি করুন!",
                    icon = Icons.Default.ReceiptLong
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
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = student?.name ?: "অজানা শিক্ষার্থী",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${batch?.name ?: "ব্যাচ"} • মাস: ${record.monthYear}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "তারিখ: $dateFormatted",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    if (record.receiptNote.isNotBlank()) {
                                        Text(
                                            text = "নোট: ${record.receiptNote}",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "৳${record.amountPaid.toInt()}",
                                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 18.sp),
                                        fontWeight = FontWeight.ExtraBold,
                                        color = EmeraldGreen
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    AssistChip(
                                        onClick = {},
                                        label = { Text(record.paymentMethod, fontSize = 10.sp, fontWeight = FontWeight.Bold) }
                                    )
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
