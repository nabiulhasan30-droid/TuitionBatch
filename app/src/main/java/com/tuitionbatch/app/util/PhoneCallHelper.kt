package com.tuitionbatch.app.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat

object PhoneCallHelper {

    /**
     * ডাইরেক্ট ফোন কল করার সুবিধা (Direct Phone Calling)
     * যদি CALL_PHONE পারমিশন থাকে, সরাসরি কল লাগিয়ে দেবে (ACTION_CALL)।
     * পারমিশন না থাকলে নিরাপদ ফলব্যাক হিসেবে ফোনের ডায়ালপ্যাড ওপেন করবে (ACTION_DIAL)।
     */
    fun directCall(context: Context, rawPhoneNumber: String) {
        val cleanNumber = rawPhoneNumber.trim().replace(" ", "").replace("-", "")
        if (cleanNumber.isBlank()) {
            Toast.makeText(context, "ফোন নম্বর পাওয়া যায়নি!", Toast.LENGTH_SHORT).show()
            return
        }

        val hasCallPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED

        val intent = if (hasCallPermission) {
            Intent(Intent.ACTION_CALL, Uri.parse("tel:$cleanNumber")).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        } else {
            Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanNumber")).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }

        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            // Security or activity exception fallback
            try {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanNumber")).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(dialIntent)
            } catch (ex: Exception) {
                Toast.makeText(context, "কল করা সম্ভব হয়নি: ${ex.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
