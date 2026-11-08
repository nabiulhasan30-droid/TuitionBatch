package com.tuitionbatch.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "batches")
data class BatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val subject: String,
    val grade: String,
    val daysOfWeek: List<Int>, // 1=Sunday, 2=Monday, ..., 7=Saturday (java.util.Calendar)
    val startTimeHour: Int,    // 0..23
    val startTimeMinute: Int,  // 0..59
    val durationMinutes: Int = 60,
    val monthlyFee: Double = 0.0,
    val location: String = "Offline/Home",
    val colorHex: String = "#1E40AF",
    val isActive: Boolean = true,
    val notes: String = ""
)
