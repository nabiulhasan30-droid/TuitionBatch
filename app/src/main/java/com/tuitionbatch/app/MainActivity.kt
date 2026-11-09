package com.tuitionbatch.app

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.tuitionbatch.app.notification.NotificationHelper
import com.tuitionbatch.app.ui.screens.MainScreen
import com.tuitionbatch.app.ui.theme.TuitionBatchTheme
import com.tuitionbatch.app.ui.viewmodel.MainViewModel
import com.tuitionbatch.app.ui.viewmodel.ViewModelFactory
import com.tuitionbatch.app.util.WestBengalCalendarHelper
import java.time.LocalDate

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(application)
    }

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.POST_NOTIFICATIONS] == true) {
            checkAndNotifyTodayHoliday()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requiredPerms = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requiredPerms.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requiredPerms.add(Manifest.permission.CALL_PHONE)
        }

        if (requiredPerms.isNotEmpty()) {
            requestPermissionsLauncher.launch(requiredPerms.toTypedArray())
        } else {
            checkAndNotifyTodayHoliday()
        }

        val launchBatchId = intent.getLongExtra("EXTRA_BATCH_ID", -1L).takeIf { it != -1L }
        val openCalendar = intent.getBooleanExtra("EXTRA_OPEN_CALENDAR", false)

        setContent {
            TuitionBatchTheme {
                MainScreen(
                    viewModel = viewModel,
                    initialBatchId = launchBatchId,
                    initialOpenCalendar = openCalendar
                )
            }
        }
    }

    private fun checkAndNotifyTodayHoliday() {
        try {
            val today = LocalDate.now()
            val holiday = WestBengalCalendarHelper.getHolidayForDate(today) ?: return

            val prefs = getSharedPreferences("holiday_alert_prefs", Context.MODE_PRIVATE)
            val lastNotifiedDate = prefs.getString("last_notified_date", "")

            if (lastNotifiedDate != today.toString()) {
                NotificationHelper.showHolidayNotification(this, holiday)
                prefs.edit().putString("last_notified_date", today.toString()).apply()
            }
        } catch (e: Exception) {
            // Silently handle if permission or notification service not ready
        }
    }
}
