package com.example.mynotes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynotes.interfaces.NotesDao
import com.example.mynotes.models.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object{
        @Volatile var instance : NotesDatabase? = null
        private val LOCK = Any()


        operator  fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDb(context).also {
                instance = it
            }
        }
        private fun buildDb(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NotesDatabase::class.java,
            "notes.db"
        ).build()

    }


}