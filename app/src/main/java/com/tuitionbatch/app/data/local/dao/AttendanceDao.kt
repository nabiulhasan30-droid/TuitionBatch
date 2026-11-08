package com.tuitionbatch.app.data.local.dao

import androidx.room.*
import com.tuitionbatch.app.data.local.entity.AttendanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Query("SELECT * FROM attendance WHERE batchId = :batchId AND dateMillis = :dateMillis")
    fun getAttendanceForDate(batchId: Long, dateMillis: Long): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE studentId = :studentId ORDER BY dateMillis DESC")
    fun getAttendanceForStudent(studentId: Long): Flow<List<AttendanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun recordAttendance(attendance: AttendanceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun recordAttendanceBatch(attendanceList: List<AttendanceEntity>)

    @Query("DELETE FROM attendance WHERE batchId = :batchId AND dateMillis = :dateMillis")
    suspend fun deleteAttendanceForDate(batchId: Long, dateMillis: Long)
}
