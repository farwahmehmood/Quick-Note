package com.quick.note.quicknote.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "notesTable")
class Note (
    @ColumnInfo(name="title") var noteTitle: String,
    @ColumnInfo(name="description") var noteDes: String,
    @ColumnInfo(name="timestamp") var TimeStamp: String

){

    @PrimaryKey(autoGenerate = true) var id=0
}