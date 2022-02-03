package com.example.mynotes.di

import android.content.Context
import androidx.room.Room
import com.example.mynotes.db.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideNotesDb(
        @ApplicationContext context: Context
    ): NotesDatabase = Room.databaseBuilder(
        context,
        NotesDatabase::class.java,
        "notes.db"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideNotesDao(
        notesDatabase: NotesDatabase
    ) = notesDatabase.notesDao()
}