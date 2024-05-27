package com.quick.note.quicknote.Repository

import androidx.lifecycle.LiveData
import com.quick.note.quicknote.db.Note
import com.quick.note.quicknote.db.NotesDao

class NoteRepository (private val notesDao: NotesDao){

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insert(note: Note){
        notesDao.insert(note)
    }

    suspend fun deleteNote(note: Note){
        notesDao.delete(note)
    }

    suspend fun updatenotes(note: Note){
        notesDao.update(note)
    }
}