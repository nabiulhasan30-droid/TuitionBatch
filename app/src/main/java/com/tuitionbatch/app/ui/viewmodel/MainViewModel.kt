package com.tuitionbatch.app.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tuitionbatch.app.data.local.entity.*
import com.tuitionbatch.app.data.repository.TuitionRepository
import com.tuitionbatch.app.notification.AlarmScheduler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainViewModel(
    application: Application,
    private val repository: TuitionRepository
) : AndroidViewModel(application) {

    val allBatches: StateFlow<List<BatchEntity>> = repository.allBatches
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activeBatches: StateFlow<List<BatchEntity>> = repository.activeBatches
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allStudents: StateFlow<List<StudentEntity>> = repository.allStudents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val totalActiveStudents: StateFlow<Int> = repository.totalActiveStudents
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val allFeeRecords: StateFlow<List<FeeRecordEntity>> = repository.allFeeRecords
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Today's classes derived from active batches
    val todayClasses: StateFlow<List<BatchEntity>> = activeBatches.map { list ->
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        list.filter { it.daysOfWeek.contains(currentDay) }
            .sortedWith(compareBy({ it.startTimeHour }, { it.startTimeMinute }))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Monthly stats: Total expected income vs collected
    val thisMonthFeeSummary: StateFlow<Pair<Double, Double>> = combine(
        activeBatches,
        allStudents,
        allFeeRecords
    ) { batches, students, feeRecords ->
        val currentMonthStr = SimpleDateFormat("MMM yyyy", Locale.ENGLISH).format(System.currentTimeMillis())

        // Calculate expected fee = sum over active students of (overrideFee ?: batch.monthlyFee)
        val batchMap = batches.associateBy { it.id }
        var expectedTotal = 0.0
        students.filter { it.isActive }.forEach { student ->
            val b = batchMap[student.batchId]
            val fee = student.feeOverride ?: (b?.monthlyFee ?: 0.0)
            expectedTotal += fee
        }

        val collectedThisMonth = feeRecords
            .filter { it.monthYear.contains(currentMonthStr, ignoreCase = true) && it.isPaid }
            .sumOf { it.amountPaid }

        Pair(expectedTotal, collectedThisMonth)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Pair(0.0, 0.0))

    // ----------------- Batch Actions -----------------

    fun insertBatch(batch: BatchEntity) {
        viewModelScope.launch {
            val newId = repository.insertBatch(batch)
            val insertedBatch = batch.copy(id = newId)
            AlarmScheduler.schedule15MinReminder(getApplication(), insertedBatch)
        }
    }

    fun updateBatch(batch: BatchEntity) {
        viewModelScope.launch {
            repository.updateBatch(batch)
            AlarmScheduler.schedule15MinReminder(getApplication(), batch)
        }
    }

    fun deleteBatch(batch: BatchEntity) {
        viewModelScope.launch {
            repository.deleteBatch(batch)
            AlarmScheduler.cancelReminder(getApplication(), batch.id)
        }
    }

    // ----------------- Student Actions -----------------

    fun insertStudent(student: StudentEntity) {
        viewModelScope.launch {
            repository.insertStudent(student)
        }
    }

    fun updateStudent(student: StudentEntity) {
        viewModelScope.launch {
            repository.updateStudent(student)
        }
    }

    fun deleteStudent(student: StudentEntity) {
        viewModelScope.launch {
            repository.deleteStudent(student)
        }
    }

    fun getStudentsForBatch(batchId: Long): Flow<List<StudentEntity>> {
        return repository.getStudentsForBatch(batchId)
    }

    // ----------------- Attendance Actions -----------------

    fun getAttendanceForDate(batchId: Long, dateMillis: Long): Flow<List<AttendanceEntity>> {
        return repository.getAttendanceForDate(batchId, dateMillis)
    }

    fun recordAttendance(batchId: Long, studentId: Long, dateMillis: Long, status: String) {
        viewModelScope.launch {
            repository.recordAttendance(
                AttendanceEntity(
                    batchId = batchId,
                    studentId = studentId,
                    dateMillis = dateMillis,
                    status = status
                )
            )
        }
    }

    fun markAllPresent(batchId: Long, students: List<StudentEntity>, dateMillis: Long) {
        viewModelScope.launch {
            val list = students.map {
                AttendanceEntity(
                    batchId = batchId,
                    studentId = it.id,
                    dateMillis = dateMillis,
                    status = "PRESENT"
                )
            }
            repository.recordAttendanceBatch(list)
        }
    }

    // ----------------- Fee Actions -----------------

    fun recordFee(
        studentId: Long,
        batchId: Long,
        amount: Double,
        monthYear: String,
        method: String,
        receiptNote: String
    ) {
        viewModelScope.launch {
            repository.insertFeeRecord(
                FeeRecordEntity(
                    studentId = studentId,
                    batchId = batchId,
                    monthYear = monthYear,
                    amountPaid = amount,
                    totalDue = 0.0,
                    paymentDateMillis = System.currentTimeMillis(),
                    paymentMethod = method,
                    isPaid = true,
                    receiptNote = receiptNote
                )
            )
        }
    }

    fun deleteFeeRecord(record: FeeRecordEntity) {
        viewModelScope.launch {
            repository.deleteFeeRecord(record)
        }
    }

    // ----------------- Notes Actions -----------------

    fun getNotesForBatch(batchId: Long): Flow<List<NoteEntity>> {
        return repository.getNotesForBatch(batchId)
    }

    fun addNote(batchId: Long, topic: String, homework: String, exam: String) {
        viewModelScope.launch {
            repository.insertNote(
                NoteEntity(
                    batchId = batchId,
                    dateMillis = System.currentTimeMillis(),
                    topicCovered = topic,
                    homeworkAssigned = homework,
                    examInfo = exam
                )
            )
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}
