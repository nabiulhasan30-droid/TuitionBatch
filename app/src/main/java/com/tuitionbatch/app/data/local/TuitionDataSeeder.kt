package com.tuitionbatch.app.data.local

import com.tuitionbatch.app.data.local.dao.BatchDao
import com.tuitionbatch.app.data.local.dao.StudentDao
import com.tuitionbatch.app.data.local.entity.BatchEntity
import com.tuitionbatch.app.data.local.entity.StudentEntity
import java.util.Calendar

object TuitionDataSeeder {

    suspend fun seedInitialDataIfEmpty(batchDao: BatchDao, studentDao: StudentDao) {
        val existing = batchDao.getActiveBatchesSync()
        if (existing.isNotEmpty()) return

        // 13 Real Batches from TutorSync Database
        val initialBatches = listOf(
            BatchEntity(
                name = "Pagda Batch",
                subject = "General / Combined",
                grade = "Secondary",
                daysOfWeek = listOf(Calendar.MONDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY),
                startTimeHour = 12,
                startTimeMinute = 40,
                durationMinutes = 80,
                monthlyFee = 1500.0,
                location = "Pagda",
                colorHex = "#1E40AF"
            ),
            BatchEntity(
                name = "Beleghata Batch",
                subject = "General / Combined",
                grade = "Secondary",
                daysOfWeek = listOf(Calendar.MONDAY, Calendar.THURSDAY),
                startTimeHour = 16,
                startTimeMinute = 30,
                durationMinutes = 80,
                monthlyFee = 1200.0,
                location = "Beleghata",
                colorHex = "#0284C7"
            ),
            BatchEntity(
                name = "Khamar Batch",
                subject = "General / Combined",
                grade = "Secondary",
                daysOfWeek = listOf(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY),
                startTimeHour = 18,
                startTimeMinute = 15,
                durationMinutes = 75,
                monthlyFee = 1500.0,
                location = "Khamar",
                colorHex = "#059669"
            ),
            BatchEntity(
                name = "Golabari Batch",
                subject = "General / Combined",
                grade = "Secondary",
                daysOfWeek = listOf(Calendar.MONDAY, Calendar.WEDNESDAY, Calendar.FRIDAY, Calendar.SATURDAY),
                startTimeHour = 19,
                startTimeMinute = 45,
                durationMinutes = 120,
                monthlyFee = 1800.0,
                location = "Golabari",
                colorHex = "#D97706"
            ),
            BatchEntity(
                name = "Shasan Radiya",
                subject = "Private Tuition",
                grade = "Student Radiya",
                daysOfWeek = listOf(Calendar.TUESDAY, Calendar.THURSDAY, Calendar.FRIDAY),
                startTimeHour = 6,
                startTimeMinute = 30,
                durationMinutes = 60,
                monthlyFee = 1000.0,
                location = "Shasan",
                colorHex = "#DC2626"
            ),
            BatchEntity(
                name = "Rameswarpur Asifa",
                subject = "Private Tuition",
                grade = "Student Asifa",
                daysOfWeek = listOf(Calendar.TUESDAY, Calendar.THURSDAY),
                startTimeHour = 7,
                startTimeMinute = 45,
                durationMinutes = 60,
                monthlyFee = 1000.0,
                location = "Rameswarpur",
                colorHex = "#7C3AED"
            ),
            BatchEntity(
                name = "Majlishpur Affan",
                subject = "Private Tuition",
                grade = "Student Affan",
                daysOfWeek = listOf(Calendar.TUESDAY, Calendar.SUNDAY),
                startTimeHour = 16,
                startTimeMinute = 30,
                durationMinutes = 80,
                monthlyFee = 1200.0,
                location = "Majlishpur",
                colorHex = "#DB2777"
            ),
            BatchEntity(
                name = "Dugdia Batch",
                subject = "General / Combined",
                grade = "Secondary",
                daysOfWeek = listOf(Calendar.TUESDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY),
                startTimeHour = 18,
                startTimeMinute = 0,
                durationMinutes = 80,
                monthlyFee = 1500.0,
                location = "Dugdia",
                colorHex = "#0D9488"
            ),
            BatchEntity(
                name = "Majlishpur Asif",
                subject = "Private Tuition",
                grade = "Student Asif",
                daysOfWeek = listOf(Calendar.TUESDAY, Calendar.THURSDAY, Calendar.SATURDAY),
                startTimeHour = 21,
                startTimeMinute = 0,
                durationMinutes = 60,
                monthlyFee = 1000.0,
                location = "Majlishpur",
                colorHex = "#4F46E5"
            ),
            BatchEntity(
                name = "Natasha Batch",
                subject = "General / Combined",
                grade = "Secondary",
                daysOfWeek = listOf(Calendar.WEDNESDAY, Calendar.SUNDAY),
                startTimeHour = 17,
                startTimeMinute = 0,
                durationMinutes = 80,
                monthlyFee = 1200.0,
                location = "Natasha",
                colorHex = "#E11D48"
            ),
            BatchEntity(
                name = "Gora Kaka Batch",
                subject = "General / Combined",
                grade = "Secondary",
                daysOfWeek = listOf(Calendar.SATURDAY),
                startTimeHour = 6,
                startTimeMinute = 30,
                durationMinutes = 75,
                monthlyFee = 800.0,
                location = "Gora Kaka",
                colorHex = "#F59E0B"
            ),
            BatchEntity(
                name = "Sumaiya",
                subject = "Private Tuition",
                grade = "Student Sumaiya",
                daysOfWeek = listOf(Calendar.SATURDAY, Calendar.SUNDAY),
                startTimeHour = 9,
                startTimeMinute = 45,
                durationMinutes = 85,
                monthlyFee = 1200.0,
                location = "Home / Private",
                colorHex = "#84CC16"
            ),
            BatchEntity(
                name = "Naznin Batch",
                subject = "General / Combined",
                grade = "Secondary",
                daysOfWeek = listOf(Calendar.SATURDAY),
                startTimeHour = 17,
                startTimeMinute = 0,
                durationMinutes = 100,
                monthlyFee = 1200.0,
                location = "Naznin",
                colorHex = "#6366F1"
            )
        )

        for (b in initialBatches) {
            val batchId = batchDao.insertBatch(b)
            // Add default student entry for named students
            if (b.name.contains("Radiya", ignoreCase = true)) {
                studentDao.insertStudent(StudentEntity(batchId = batchId, name = "Radiya", rollNumber = "01"))
            } else if (b.name.contains("Asifa", ignoreCase = true)) {
                studentDao.insertStudent(StudentEntity(batchId = batchId, name = "Asifa", rollNumber = "01"))
            } else if (b.name.contains("Affan", ignoreCase = true)) {
                studentDao.insertStudent(StudentEntity(batchId = batchId, name = "Affan", rollNumber = "01"))
            } else if (b.name.contains("Asif", ignoreCase = true)) {
                studentDao.insertStudent(StudentEntity(batchId = batchId, name = "Asif", rollNumber = "01"))
            } else if (b.name.contains("Sumaiya", ignoreCase = true)) {
                studentDao.insertStudent(StudentEntity(batchId = batchId, name = "Sumaiya", rollNumber = "01"))
            }
        }
    }
}
