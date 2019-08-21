package com.example.mynotes.interfaces

import androidx.room.*
import com.example.mynotes.models.Note

@Dao

interface NotesDao{
    @Query("select * from notes order by timestamp desc")
    suspend fun getAllNotes(): List<Note>

    @Delete
    suspend fun delete(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun newNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)
}


