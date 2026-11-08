package com.tuitionbatch.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "students",
    foreignKeys = [
        ForeignKey(
            entity = BatchEntity::class,
            parentColumns = ["id"],
            childColumns = ["batchId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["batchId"])]
)
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val batchId: Long,
    val name: String,
    val rollNumber: String = "",
    val studentPhone: String = "",
    val guardianName: String = "",
    val guardianPhone: String = "",
    val institution: String = "",
    val joiningDate: Long = System.currentTimeMillis(),
    val feeOverride: Double? = null,
    val isActive: Boolean = true
)
