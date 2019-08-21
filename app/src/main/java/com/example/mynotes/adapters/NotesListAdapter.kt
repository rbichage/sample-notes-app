package com.example.mynotes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.fragments.NotesListFragmentDirections
import com.example.mynotes.models.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NotesListAdapter(private val notes: List<Note>, val context: Context) :
    RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.note_title.text = notes[position].tilte
        holder.itemView.note_body.text = notes[position].text
        holder.itemView.date_created.text = notes[position].created

        holder.itemView.setOnClickListener {
            val action = NotesListFragmentDirections.actionAddNewNoteToEditNoteFragment2()
            action.note =  notes[position]
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount() = notes.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}