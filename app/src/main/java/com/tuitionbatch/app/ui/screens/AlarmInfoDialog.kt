package com.tuitionbatch.app.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tuitionbatch.app.notification.NotificationHelper

@Composable
fun AlarmInfoDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.NotificationsActive,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "স্মার্ট ১৫-মিনিট অ্যালার্ম",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "🔔 কীভাবে কাজ করে:",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "• আপনার রুটিনে নির্ধারিত প্রতিটি পড়ার সময়ের ঠিক ১৫ মিনিট আগে উচ্চ-অগ্রাধিকারের সাউন্ড ও ভাইব্রেশনসহ অ্যালার্ম আসবে।\n" +
                            "• ফোন বন্ধ করে চালু (Reboot) করলেও স্বয়ংক্রিয়ভাবে অ্যালার্ম পুনরায় সেট হয়ে যাবে।\n" +
                            "• ইন্টারনেট সংযোগ না থাকলেও অফলাইনে নিখুঁতভাবে অ্যালার্ম কাজ করবে।",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Test Button
                Button(
                    onClick = {
                        NotificationHelper.showTuitionReminderNotification(
                            context = context,
                            batchId = 9999L,
                            batchName = "Pagda Batch (টেস্ট)",
                            subject = "পদার্থবিজ্ঞান",
                            timeFormatted = "১২:৪০ PM",
                            location = "Pagda"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.NotificationsActive, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("এখনই টেস্ট নোটিফিকেশন পাঠান", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
