package com.tuitionbatch.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tuitionbatch.app.data.local.entity.BatchEntity
import com.tuitionbatch.app.data.local.entity.StudentEntity
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFeeDialog(
    students: List<StudentEntity>,
    batches: List<BatchEntity>,
    onDismiss: () -> Unit,
    onSave: (studentId: Long, batchId: Long, amount: Double, monthYear: String, method: String, note: String) -> Unit
) {
    var selectedStudentId by remember { mutableLongStateOf(students.firstOrNull()?.id ?: 0L) }
    val currentMonthYear = remember {
        SimpleDateFormat("MMMM yyyy", Locale.ENGLISH).format(System.currentTimeMillis())
    }
    var monthYear by remember { mutableStateOf(currentMonthYear) }
    var amountStr by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("Cash") }
    var receiptNote by remember { mutableStateOf("") }

    var studentDropdownExpanded by remember { mutableStateOf(false) }
    val methods = listOf("Cash", "bKash", "Nagad", "Bank")

    // Update default fee amount when student is selected
    LaunchedEffect(selectedStudentId) {
        val student = students.find { it.id == selectedStudentId }
        val batch = batches.find { it.id == student?.batchId }
        val fee = student?.feeOverride ?: (batch?.monthlyFee ?: 0.0)
        if (fee > 0) {
            amountStr = fee.toInt().toString()
        }
    }

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
                        text = "ফি / বেতন গ্রহণ",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Student Selector
                ExposedDropdownMenuBox(
                    expanded = studentDropdownExpanded,
                    onExpandedChange = { studentDropdownExpanded = !studentDropdownExpanded }
                ) {
                    val currentStudent = students.find { it.id == selectedStudentId }
                    val currentStudentName = if (currentStudent != null) {
                        val bName = batches.find { it.id == currentStudent.batchId }?.name ?: ""
                        "${currentStudent.name} (${bName})"
                    } else "শিক্ষার্থী নির্বাচন করুন"

                    OutlinedTextField(
                        value = currentStudentName,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("শিক্ষার্থী") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = studentDropdownExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = studentDropdownExpanded,
                        onDismissRequest = { studentDropdownExpanded = false }
                    ) {
                        students.forEach { s ->
                            val bName = batches.find { it.id == s.batchId }?.name ?: ""
                            DropdownMenuItem(
                                text = { Text("${s.name} (রোল: ${s.rollNumber}) - $bName") },
                                onClick = {
                                    selectedStudentId = s.id
                                    studentDropdownExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = monthYear,
                    onValueChange = { monthYear = it },
                    label = { Text("মাসের নাম (যেমন: November 2026)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = amountStr,
                    onValueChange = { amountStr = it },
                    label = { Text("পরিশোধিত টাকার পরিমাণ (টাকা)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Payment Method Chips
                Text("পেমেন্ট মেথড:", style = MaterialTheme.typography.titleMedium.copy(fontSize = 13.sp))
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    methods.forEach { m ->
                        FilterChip(
                            selected = paymentMethod == m,
                            onClick = { paymentMethod = m },
                            label = { Text(m) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = receiptNote,
                    onValueChange = { receiptNote = it },
                    label = { Text("নোট / ট্রানজাকশন আইডি (ঐচ্ছিক)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val amount = amountStr.toDoubleOrNull() ?: 0.0
                        val student = students.find { it.id == selectedStudentId }
                        if (student != null && amount > 0) {
                            onSave(student.id, student.batchId, amount, monthYear, paymentMethod, receiptNote)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    enabled = selectedStudentId > 0 && amountStr.isNotBlank()
                ) {
                    Text("পেমেন্ট এন্ট্রি সম্পন্ন করুন", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
