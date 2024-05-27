package com.quick.note.quicknote

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.quick.note.quicknote.Adaptor.NoteClickDeleteInterface
import com.quick.note.quicknote.Adaptor.NoteClickInterface
import com.quick.note.quicknote.Adaptor.NoteRVAdapter
import com.quick.note.quicknote.db.Note
import com.quick.note.quicknote.viewmodel.NoteViewModal

class MainActivity :  AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    lateinit var viewmodel: NoteViewModal
    lateinit var notesRV: RecyclerView
    lateinit var addFab: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        notesRV= findViewById(R.id.notesRV)
        addFab=findViewById(R.id.idFAB)

        notesRV.layoutManager=LinearLayoutManager(this)

        val noteRVAdapter= NoteRVAdapter(this,this,this)
        notesRV.adapter=noteRVAdapter

        viewmodel= ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModal::class.java)

        viewmodel.allNotes.observe(this, Observer { list->
            list?.let{
                noteRVAdapter.updateList(it)
            }
        })

        addFab.setOnClickListener{
            val intent=Intent(this@MainActivity,AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }

}

  override   fun onNoteClick(note: Note) {
        // opening a new intent and passing a data to it.
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.noteTitle)
        intent.putExtra("noteDescription", note.noteDes)
        intent.putExtra("noteId", note.id)
        startActivity(intent)
        this.finish()
    }
  override   fun onDeleteIconClick(note: Note) {
        // in on note click method we are calling delete
        // method from our view modal to delete our not.
        viewmodel.deletenote(note)
        // displaying a toast message
        Toast.makeText(this, "${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }
}