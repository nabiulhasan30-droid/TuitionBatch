package com.tuitionbatch.app.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tuitionbatch.app.notification.AlarmScheduler

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON"
        ) {
            AlarmScheduler.rescheduleAll(context)
        }
    }
}
