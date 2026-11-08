package com.tuitionbatch.app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.tuitionbatch.app.ui.screens.MainScreen
import com.tuitionbatch.app.ui.theme.TuitionBatchTheme
import com.tuitionbatch.app.ui.viewmodel.MainViewModel
import com.tuitionbatch.app.ui.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(application)
    }

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Permission result handled
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request POST_NOTIFICATIONS on Android 13+ (API 33+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        val launchBatchId = intent.getLongExtra("EXTRA_BATCH_ID", -1L).takeIf { it != -1L }

        setContent {
            TuitionBatchTheme {
                MainScreen(
                    viewModel = viewModel,
                    initialBatchId = launchBatchId
                )
            }
        }
    }
}
