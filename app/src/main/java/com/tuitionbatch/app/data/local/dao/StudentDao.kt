package com.tuitionbatch.app.data.local.dao

import androidx.room.*
import com.tuitionbatch.app.data.local.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getAllStudents(): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE batchId = :batchId ORDER BY rollNumber ASC, name ASC")
    fun getStudentsByBatch(batchId: Long): Flow<List<StudentEntity>>

    @Query("SELECT * FROM students WHERE batchId = :batchId ORDER BY rollNumber ASC, name ASC")
    suspend fun getStudentsByBatchSync(batchId: Long): List<StudentEntity>

    @Query("SELECT COUNT(*) FROM students WHERE batchId = :batchId AND isActive = 1")
    fun getStudentCountByBatch(batchId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM students WHERE isActive = 1")
    fun getTotalActiveStudentCount(): Flow<Int>

    @Query("SELECT * FROM students WHERE id = :studentId LIMIT 1")
    suspend fun getStudentById(studentId: Long): StudentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentEntity): Long

    @Update
    suspend fun updateStudent(student: StudentEntity)

    @Delete
    suspend fun deleteStudent(student: StudentEntity)
}
