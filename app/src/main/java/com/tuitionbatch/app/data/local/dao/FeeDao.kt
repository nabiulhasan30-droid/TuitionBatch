package com.tuitionbatch.app.data.local.dao

import androidx.room.*
import com.tuitionbatch.app.data.local.entity.FeeRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeeDao {
    @Query("SELECT * FROM fee_records ORDER BY paymentDateMillis DESC")
    fun getAllFeeRecords(): Flow<List<FeeRecordEntity>>

    @Query("SELECT * FROM fee_records WHERE batchId = :batchId ORDER BY paymentDateMillis DESC")
    fun getFeeRecordsByBatch(batchId: Long): Flow<List<FeeRecordEntity>>

    @Query("SELECT * FROM fee_records WHERE studentId = :studentId ORDER BY paymentDateMillis DESC")
    fun getFeeRecordsForStudent(studentId: Long): Flow<List<FeeRecordEntity>>

    @Query("SELECT SUM(amountPaid) FROM fee_records WHERE monthYear = :monthYear")
    fun getTotalCollectedForMonth(monthYear: String): Flow<Double?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeeRecord(feeRecord: FeeRecordEntity): Long

    @Update
    suspend fun updateFeeRecord(feeRecord: FeeRecordEntity)

    @Delete
    suspend fun deleteFeeRecord(feeRecord: FeeRecordEntity)
}
