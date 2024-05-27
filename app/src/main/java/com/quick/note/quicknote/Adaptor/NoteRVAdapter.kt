package com.quick.note.quicknote.Adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.quick.note.quicknote.MainActivity
import com.quick.note.quicknote.R
import com.quick.note.quicknote.db.Note

class NoteRVAdapter(
    val context: Context,
    val noteClickDeleteInterface: NoteClickDeleteInterface,
    val noteClickInterface: NoteClickInterface

):
   RecyclerView.Adapter<NoteRVAdapter.ViewHolder>(){
    private val allNotes = ArrayList<Note>()

       inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
           val noteTv= itemView.findViewById<TextView>(R.id.idTVNote)
           val dateTV = itemView.findViewById<TextView>(R.id.idTVDate)
           val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)

       }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_rv_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allNotes.size


    }
fun updateList(newList: List<Note>){
    allNotes.clear()
    allNotes.addAll(newList)
    notifyDataSetChanged()
}

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder.noteTv.setText(allNotes.get(position).noteTitle)
        holder.dateTV.setText("Last Updated : "+ allNotes.get(position).TimeStamp)

        holder.deleteIV.setOnClickListener{
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    // Delete selected note from database
                    noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()




        }

        holder.itemView.setOnClickListener{
            noteClickInterface.onNoteClick(allNotes.get(position))
        }
    }


}

interface NoteClickDeleteInterface{
    fun onDeleteIconClick(note: Note)
}

interface NoteClickInterface{
    fun onNoteClick(note: Note)
}




