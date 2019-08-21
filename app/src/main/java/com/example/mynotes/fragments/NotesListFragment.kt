package com.example.mynotes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.R
import com.example.mynotes.adapters.NotesListAdapter
import com.example.mynotes.db.NotesDatabase
import com.example.mynotes.models.Note
import kotlinx.android.synthetic.main.fragment_notes_list.*
import kotlinx.android.synthetic.main.fragment_notes_list.view.*
import kotlinx.coroutines.launch


class NotesListFragment : BaseFragment() {

    lateinit var recyclerView: RecyclerView
    var notesList : List<Note> = ArrayList()
    lateinit var tvNoNotes: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_notes_list, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.notes_recycler
        tvNoNotes = view.tv_no_notes

        launch {
            context?.let {
                notesList = NotesDatabase.invoke(it)
                    .notesDao()
                    .getAllNotes()

                recyclerView.adapter = NotesListAdapter(notesList, it)
                recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                recyclerView.hasFixedSize()

            }

            if (notesList.isEmpty()){
                tvNoNotes.visibility = View.VISIBLE
            }

        }
        fab_new_note.setOnClickListener {
            val action = NotesListFragmentDirections.actionAddNewNoteToEditNoteFragment2()
            Navigation.findNavController(it).navigate(action)
        }

    }




}