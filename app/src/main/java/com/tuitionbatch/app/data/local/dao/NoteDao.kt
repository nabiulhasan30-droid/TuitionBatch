package com.tuitionbatch.app.data.local.dao

import androidx.room.*
import com.tuitionbatch.app.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE batchId = :batchId ORDER BY dateMillis DESC")
    fun getNotesForBatch(batchId: Long): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}
