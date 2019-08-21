package com.example.mynotes.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes")
data class Note(
    @ColumnInfo(name = "title") var tilte: String,
    @ColumnInfo(name = "content") var text: String,
    @ColumnInfo(name = "created") var created: String,
    @ColumnInfo(name = "is_draft") var isDraft: Boolean,
    @ColumnInfo(name = "timestamp") var timeStamp: Long
    ) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


