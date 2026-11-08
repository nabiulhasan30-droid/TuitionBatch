package com.tuitionbatch.app.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "fee_records",
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["id"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BatchEntity::class,
            parentColumns = ["id"],
            childColumns = ["batchId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["studentId"]),
        Index(value = ["batchId"])
    ]
)
data class FeeRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val studentId: Long,
    val batchId: Long,
    val monthYear: String, // e.g. "Nov 2026"
    val amountPaid: Double,
    val totalDue: Double,
    val paymentDateMillis: Long = System.currentTimeMillis(),
    val paymentMethod: String = "Cash", // Cash, bKash, Nagad, Bank
    val isPaid: Boolean = true,
    val receiptNote: String = ""
)
