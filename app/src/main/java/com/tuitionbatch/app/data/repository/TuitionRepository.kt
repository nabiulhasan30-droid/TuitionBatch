package com.tuitionbatch.app.data.repository

import com.tuitionbatch.app.data.local.AppDatabase
import com.tuitionbatch.app.data.local.entity.*
import kotlinx.coroutines.flow.Flow

class TuitionRepository(private val database: AppDatabase) {

    private val batchDao = database.batchDao()
    private val studentDao = database.studentDao()
    private val attendanceDao = database.attendanceDao()
    private val feeDao = database.feeDao()
    private val noteDao = database.noteDao()

    // Batches
    val allBatches: Flow<List<BatchEntity>> = batchDao.getAllBatches()
    val activeBatches: Flow<List<BatchEntity>> = batchDao.getActiveBatches()

    suspend fun getActiveBatchesSync(): List<BatchEntity> = batchDao.getActiveBatchesSync()

    fun getBatchById(batchId: Long): Flow<BatchEntity?> = batchDao.getBatchById(batchId)
    suspend fun getBatchByIdSync(batchId: Long): BatchEntity? = batchDao.getBatchByIdSync(batchId)

    suspend fun insertBatch(batch: BatchEntity): Long = batchDao.insertBatch(batch)
    suspend fun updateBatch(batch: BatchEntity) = batchDao.updateBatch(batch)
    suspend fun deleteBatch(batch: BatchEntity) = batchDao.deleteBatch(batch)

    // Students
    val allStudents: Flow<List<StudentEntity>> = studentDao.getAllStudents()
    val totalActiveStudents: Flow<Int> = studentDao.getTotalActiveStudentCount()

    fun getStudentsForBatch(batchId: Long): Flow<List<StudentEntity>> = studentDao.getStudentsByBatch(batchId)
    suspend fun getStudentsForBatchSync(batchId: Long): List<StudentEntity> = studentDao.getStudentsByBatchSync(batchId)
    fun getStudentCount(batchId: Long): Flow<Int> = studentDao.getStudentCountByBatch(batchId)
    suspend fun getStudentById(id: Long): StudentEntity? = studentDao.getStudentById(id)

    suspend fun insertStudent(student: StudentEntity): Long = studentDao.insertStudent(student)
    suspend fun updateStudent(student: StudentEntity) = studentDao.updateStudent(student)
    suspend fun deleteStudent(student: StudentEntity) = studentDao.deleteStudent(student)

    // Attendance
    fun getAttendanceForDate(batchId: Long, dateMillis: Long): Flow<List<AttendanceEntity>> =
        attendanceDao.getAttendanceForDate(batchId, dateMillis)

    fun getAttendanceForStudent(studentId: Long): Flow<List<AttendanceEntity>> =
        attendanceDao.getAttendanceForStudent(studentId)

    suspend fun recordAttendance(attendance: AttendanceEntity) = attendanceDao.recordAttendance(attendance)
    suspend fun recordAttendanceBatch(list: List<AttendanceEntity>) = attendanceDao.recordAttendanceBatch(list)

    // Fees
    val allFeeRecords: Flow<List<FeeRecordEntity>> = feeDao.getAllFeeRecords()
    fun getFeeRecordsForBatch(batchId: Long): Flow<List<FeeRecordEntity>> = feeDao.getFeeRecordsByBatch(batchId)
    fun getFeeRecordsForStudent(studentId: Long): Flow<List<FeeRecordEntity>> = feeDao.getFeeRecordsForStudent(studentId)
    fun getTotalCollectedForMonth(monthYear: String): Flow<Double?> = feeDao.getTotalCollectedForMonth(monthYear)

    suspend fun insertFeeRecord(record: FeeRecordEntity): Long = feeDao.insertFeeRecord(record)
    suspend fun updateFeeRecord(record: FeeRecordEntity) = feeDao.updateFeeRecord(record)
    suspend fun deleteFeeRecord(record: FeeRecordEntity) = feeDao.deleteFeeRecord(record)

    // Notes
    fun getNotesForBatch(batchId: Long): Flow<List<NoteEntity>> = noteDao.getNotesForBatch(batchId)
    suspend fun insertNote(note: NoteEntity): Long = noteDao.insertNote(note)
    suspend fun deleteNote(note: NoteEntity) = noteDao.deleteNote(note)
}
