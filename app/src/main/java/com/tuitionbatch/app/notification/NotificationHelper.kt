package com.tuitionbatch.app.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.tuitionbatch.app.MainActivity
import com.tuitionbatch.app.R

object NotificationHelper {

    const val CHANNEL_ID = "tuition_routine_reminders"
    private const val CHANNEL_NAME = "Tuition Routine 15-Min Reminders"
    private const val CHANNEL_DESC = "Alerts 15 minutes before scheduled tuition batch classes"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT)
                .build()

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESC
                enableLights(true)
                lightColor = 0xFF1E3A8A.toInt()
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 400, 200, 400)
                setSound(soundUri, audioAttributes)
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showTuitionReminderNotification(
        context: Context,
        batchId: Long,
        batchName: String,
        subject: String,
        timeFormatted: String,
        location: String
    ) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("EXTRA_BATCH_ID", batchId)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            batchId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("🔔 সামনে ব্যাচ আসছে: $batchName")
            .setContentText("১৫ মিনিট বাকি • সময়: $timeFormatted • স্থান: $location")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "⚠️ সামনে ব্যাচ আসছে!\n" +
                            "১৫ মিনিট পরেই $batchName ব্যাচের পড়া শুরু হবে।\n\n" +
                            "🕒 সময়: $timeFormatted\n" +
                            "📍 স্থান: $location\n" +
                            "📖 বিষয়: $subject"
                )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSound(soundUri)
            .setVibrate(longArrayOf(0, 400, 200, 400))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_notification, "অ্যাপ খুলুন", pendingIntent)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(batchId.toInt(), notification)
    }
}
