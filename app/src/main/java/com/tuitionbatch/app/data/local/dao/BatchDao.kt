package com.tuitionbatch.app.data.local.dao

import androidx.room.*
import com.tuitionbatch.app.data.local.entity.BatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BatchDao {
    @Query("SELECT * FROM batches ORDER BY startTimeHour ASC, startTimeMinute ASC")
    fun getAllBatches(): Flow<List<BatchEntity>>

    @Query("SELECT * FROM batches WHERE isActive = 1 ORDER BY startTimeHour ASC, startTimeMinute ASC")
    fun getActiveBatches(): Flow<List<BatchEntity>>

    @Query("SELECT * FROM batches WHERE isActive = 1")
    suspend fun getActiveBatchesSync(): List<BatchEntity>

    @Query("SELECT * FROM batches WHERE id = :batchId LIMIT 1")
    fun getBatchById(batchId: Long): Flow<BatchEntity?>

    @Query("SELECT * FROM batches WHERE id = :batchId LIMIT 1")
    suspend fun getBatchByIdSync(batchId: Long): BatchEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatch(batch: BatchEntity): Long

    @Update
    suspend fun updateBatch(batch: BatchEntity)

    @Delete
    suspend fun deleteBatch(batch: BatchEntity)

    @Query("DELETE FROM batches WHERE id = :batchId")
    suspend fun deleteBatchById(batchId: Long)
}
