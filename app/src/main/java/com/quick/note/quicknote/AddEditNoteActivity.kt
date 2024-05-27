package com.quick.note.quicknote

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.quick.note.quicknote.db.Note
import com.quick.note.quicknote.viewmodel.NoteViewModal
import java.text.SimpleDateFormat
import java.util.Date

class AddEditNoteActivity : AppCompatActivity() {
    lateinit var noteTitleEdt: EditText
    lateinit var noteEdt: EditText
    lateinit var saveBtn: Button

    lateinit var viewModel: NoteViewModal
    var noteId=-1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_edit_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel= ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModal::class.java)

        noteTitleEdt = findViewById(R.id.idEdtNoteName)
        noteEdt = findViewById(R.id.idEdtNoteDesc)
        saveBtn = findViewById(R.id.idBtn)

        val noteType = intent.getStringExtra("noteType")
        if (noteType.equals("Edit")) {
            // on below line we are setting data to edit text.
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDescription = intent.getStringExtra("noteDescription")
            noteId = intent.getIntExtra("noteId", -1)
            saveBtn.setText("Update Note")
            noteTitleEdt.setText(noteTitle)
            noteEdt.setText(noteDescription)
        } else {
            saveBtn.setText("Save Note")
        }

        saveBtn.setOnClickListener{
            val noteTitle=noteTitleEdt.text.toString()
            val noteDes=noteEdt.text.toString()
            if(noteType.equals("Edit")){
                if(noteTitle.isNotEmpty() && noteDes.isNotEmpty()) {
                    val sdf= SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentdateandtime: String= sdf.format(Date())
                    val updatedNote= Note(noteTitle,noteDes,currentdateandtime)
                    updatedNote.id=noteId
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(this,"note Updated..",Toast.LENGTH_LONG).show()
                }
            }else{
                if(noteTitle.isNotEmpty() && noteDes.isNotEmpty()){
                    val sdf= SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentdateandtime : String= sdf.format(Date())
                    viewModel.addNote(Note(noteTitle,noteDes,currentdateandtime))

                    Toast.makeText(this, "$noteTitle Added", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}