package com.tuitionbatch.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tuitionbatch.app.data.local.AppDatabase
import com.tuitionbatch.app.notification.AlarmScheduler
import com.tuitionbatch.app.notification.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TuitionReminderReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_BATCH_ID = "EXTRA_BATCH_ID"
        const val EXTRA_BATCH_NAME = "EXTRA_BATCH_NAME"
        const val EXTRA_SUBJECT = "EXTRA_SUBJECT"
        const val EXTRA_TIME_STR = "EXTRA_TIME_STR"
        const val EXTRA_LOCATION = "EXTRA_LOCATION"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val batchId = intent.getLongExtra(EXTRA_BATCH_ID, -1L)
        if (batchId == -1L) return

        val batchName = intent.getStringExtra(EXTRA_BATCH_NAME) ?: "Tuition Batch"
        val subject = intent.getStringExtra(EXTRA_SUBJECT) ?: "Class"
        val timeStr = intent.getStringExtra(EXTRA_TIME_STR) ?: ""
        val location = intent.getStringExtra(EXTRA_LOCATION) ?: "Offline"

        // 1. Show the 15-minute heads-up notification with sound & vibration
        NotificationHelper.showTuitionReminderNotification(
            context = context,
            batchId = batchId,
            batchName = batchName,
            subject = subject,
            timeFormatted = timeStr,
            location = location
        )

        // 2. Automatically reschedule the next weekly occurrence for this batch
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val db = AppDatabase.getInstance(context)
                val batch = db.batchDao().getBatchByIdSync(batchId)
                if (batch != null && batch.isActive) {
                    AlarmScheduler.schedule15MinReminder(context, batch)
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}
