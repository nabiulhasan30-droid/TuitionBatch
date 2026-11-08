package com.tuitionbatch.app.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.tuitionbatch.app.data.local.AppDatabase
import com.tuitionbatch.app.data.local.entity.BatchEntity
import com.tuitionbatch.app.receiver.TuitionReminderReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

object AlarmScheduler {

    const val FIFTEEN_MINUTES_MILLIS = 15 * 60 * 1000L

    /**
     * Calculates the next occurrence of a batch class and schedules an exact alarm
     * 15 minutes before that class starts.
     */
    fun schedule15MinReminder(context: Context, batch: BatchEntity) {
        if (!batch.isActive || batch.daysOfWeek.isEmpty()) {
            cancelReminder(context, batch.id)
            return
        }

        val nextClassCalendar = calculateNextClassTime(batch.daysOfWeek, batch.startTimeHour, batch.startTimeMinute)
            ?: return

        val reminderTimeMillis = nextClassCalendar.timeInMillis - FIFTEEN_MINUTES_MILLIS

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, TuitionReminderReceiver::class.java).apply {
            putExtra(TuitionReminderReceiver.EXTRA_BATCH_ID, batch.id)
            putExtra(TuitionReminderReceiver.EXTRA_BATCH_NAME, batch.name)
            putExtra(TuitionReminderReceiver.EXTRA_SUBJECT, batch.subject)
            putExtra(TuitionReminderReceiver.EXTRA_TIME_STR, formatTime(batch.startTimeHour, batch.startTimeMinute))
            putExtra(TuitionReminderReceiver.EXTRA_LOCATION, batch.location)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            batch.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    reminderTimeMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    reminderTimeMillis,
                    pendingIntent
                )
            }
        } catch (e: SecurityException) {
            // In Android 12+ if exact alarm permission is restricted, fallback to setWindow or normal
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                reminderTimeMillis,
                pendingIntent
            )
        }
    }

    fun cancelReminder(context: Context, batchId: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, TuitionReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            batchId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    /**
     * Reschedules all active batch reminders (e.g. after device boot or database sync).
     */
    fun rescheduleAll(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getInstance(context)
            val batches = db.batchDao().getActiveBatchesSync()
            batches.forEach { batch ->
                schedule15MinReminder(context, batch)
            }
        }
    }

    /**
     * Finds the next timestamp for a class from now.
     */
    fun calculateNextClassTime(daysOfWeek: List<Int>, hour: Int, minute: Int): Calendar? {
        if (daysOfWeek.isEmpty()) return null

        val now = Calendar.getInstance()
        var candidateCalendar: Calendar? = null

        // Check the next 8 days
        for (dayOffset in 0..7) {
            val checkCal = (now.clone() as Calendar).apply {
                add(Calendar.DAY_OF_YEAR, dayOffset)
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val dayOfWeek = checkCal.get(Calendar.DAY_OF_WEEK)
            if (daysOfWeek.contains(dayOfWeek)) {
                // The reminder is 15 minutes before class
                val reminderTime = checkCal.timeInMillis - FIFTEEN_MINUTES_MILLIS
                if (reminderTime > now.timeInMillis) {
                    candidateCalendar = checkCal
                    break
                }
            }
        }

        return candidateCalendar
    }

    fun formatTime(hour: Int, minute: Int): String {
        val amPm = if (hour < 12) "AM" else "PM"
        val hourIn12Format = when {
            hour == 0 -> 12
            hour > 12 -> hour - 12
            else -> hour
        }
        return String.format("%02d:%02d %s", hourIn12Format, minute, amPm)
    }
}
