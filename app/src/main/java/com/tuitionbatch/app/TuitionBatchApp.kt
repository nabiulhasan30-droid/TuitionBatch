package com.tuitionbatch.app

import android.app.Application
import com.tuitionbatch.app.data.local.AppDatabase
import com.tuitionbatch.app.data.repository.TuitionRepository
import com.tuitionbatch.app.notification.AlarmScheduler
import com.tuitionbatch.app.notification.NotificationHelper

class TuitionBatchApp : Application() {

    lateinit var repository: TuitionRepository
        private set

    override fun onCreate() {
        super.onCreate()

        // 1. Initialize Notification Channel for 15-minute reminders
        NotificationHelper.createNotificationChannel(this)

        // 2. Initialize Room Database & Repository
        val database = AppDatabase.getInstance(this)
        repository = TuitionRepository(database)

        // 3. Ensure all active alarms are registered and data is seeded
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            com.tuitionbatch.app.data.local.TuitionDataSeeder.seedInitialDataIfEmpty(
                database.batchDao(),
                database.studentDao()
            )
            AlarmScheduler.rescheduleAll(this@TuitionBatchApp)
        }
    }
}
