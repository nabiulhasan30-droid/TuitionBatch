package com.tuitionbatch.app.ui.screens

import android.content.Intent
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContactPhone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tuitionbatch.app.data.local.entity.BatchEntity
import com.tuitionbatch.app.data.local.entity.StudentEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditStudentDialog(
    initialStudent: StudentEntity? = null,
    batches: List<BatchEntity>,
    preselectedBatchId: Long? = null,
    onDismiss: () -> Unit,
    onSave: (StudentEntity) -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(initialStudent?.name ?: "") }
    var roll by remember { mutableStateOf(initialStudent?.rollNumber ?: "") }
    var phone by remember { mutableStateOf(initialStudent?.studentPhone ?: "") }
    var guardianName by remember { mutableStateOf(initialStudent?.guardianName ?: "") }
    var guardianPhone by remember { mutableStateOf(initialStudent?.guardianPhone ?: "") }
    var institution by remember { mutableStateOf(initialStudent?.institution ?: "") }
    var feeOverrideStr by remember { mutableStateOf(initialStudent?.feeOverride?.let { if (it > 0) it.toInt().toString() else "" } ?: "") }

    var selectedBatchId by remember {
        mutableLongStateOf(
            initialStudent?.batchId ?: preselectedBatchId ?: (batches.firstOrNull()?.id ?: 0L)
        )
    }

    var batchDropdownExpanded by remember { mutableStateOf(false) }

    var pickingForStudent by remember { mutableStateOf(true) }

    val contactPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data ?: return@rememberLauncherForActivityResult
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )
        try {
            context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    val rawNumber = if (numberIndex != -1) cursor.getString(numberIndex) else ""
                    val contactName = if (nameIndex != -1) cursor.getString(nameIndex) else ""
                    val cleanNumber = rawNumber.replace(" ", "").replace("-", "")

                    if (pickingForStudent) {
                        phone = cleanNumber
                        if (name.isBlank() && contactName.isNotBlank()) {
                            name = contactName
                        }
                    } else {
                        guardianPhone = cleanNumber
                        if (guardianName.isBlank() && contactName.isNotBlank()) {
                            guardianName = contactName
                        }
                    }
                }
            }
        } catch (e: Exception) {
            // Silently ignore format/permission edge case
        }
    }

    val openDirectoryPicker = { forStudent: Boolean ->
        pickingForStudent = forStudent
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
        contactPickerLauncher.launch(intent)
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
                        text = if (initialStudent == null) "নতুন শিক্ষার্থী যুক্ত করুন" else "শিক্ষার্থীর তথ্য পরিবর্তন",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Select Batch
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

                Spacer(modifier = Modifier.height(10.dp))

                // Student Name
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("শিক্ষার্থীর নাম") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = roll,
                        onValueChange = { roll = it },
                        label = { Text("রোল / আইডি") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("শিক্ষার্থীর ফোন") },
                        placeholder = { Text("নম্বর লিখুন বা বাছুন") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { openDirectoryPicker(true) }) {
                                Icon(
                                    Icons.Default.ContactPhone,
                                    contentDescription = "ফোন ডিরেক্টরি থেকে বাছুন",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        modifier = Modifier.weight(1.8f),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Guardian Details
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = guardianName,
                        onValueChange = { guardianName = it },
                        label = { Text("অভিভাবকের নাম") },
                        singleLine = true,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = guardianPhone,
                        onValueChange = { guardianPhone = it },
                        label = { Text("অভিভাবকের ফোন") },
                        placeholder = { Text("নম্বর লিখুন বা বাছুন") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        trailingIcon = {
                            IconButton(onClick = { openDirectoryPicker(false) }) {
                                Icon(
                                    Icons.Default.ContactPhone,
                                    contentDescription = "ফোন ডিরেক্টরি থেকে বাছুন",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        modifier = Modifier.weight(1.2f),
                        shape = RoundedCornerShape(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = institution,
                    onValueChange = { institution = it },
                    label = { Text("স্কুল / কলেজ / প্রতিষ্ঠান") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = feeOverrideStr,
                    onValueChange = { feeOverrideStr = it },
                    label = { Text("বিশেষ মাসিক ফি (খালি রাখলে ব্যাচ ফি প্রযোজ্য)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Save Button
                Button(
                    onClick = {
                        if (name.isNotBlank() && selectedBatchId > 0) {
                            val feeOverride = feeOverrideStr.toDoubleOrNull()
                            val student = (initialStudent ?: StudentEntity(
                                batchId = selectedBatchId,
                                name = name,
                                rollNumber = roll,
                                studentPhone = phone,
                                guardianName = guardianName,
                                guardianPhone = guardianPhone,
                                institution = institution,
                                feeOverride = feeOverride
                            )).copy(
                                batchId = selectedBatchId,
                                name = name,
                                rollNumber = roll,
                                studentPhone = phone,
                                guardianName = guardianName,
                                guardianPhone = guardianPhone,
                                institution = institution,
                                feeOverride = feeOverride
                            )
                            onSave(student)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    enabled = name.isNotBlank() && selectedBatchId > 0
                ) {
                    Text(
                        text = if (initialStudent == null) "শিক্ষার্থী যোগ করুন" else "আপডেট সংরক্ষণ করুন",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
