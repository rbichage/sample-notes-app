package com.example.mynotes.fragments


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.db.NotesDatabase
import com.example.mynotes.models.Note
import com.example.mynotes.utils.getCurrenDate
import com.example.mynotes.utils.hideSoftKeyboard
import com.example.mynotes.utils.toast
import kotlinx.android.synthetic.main.fragment_edit_note.*
import kotlinx.android.synthetic.main.note_item.note_title
import kotlinx.coroutines.launch
import java.util.logging.Logger


class EditNoteFragment : BaseFragment() {

    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_note, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
            setDisplayHomeAsUpEnabled(true)
            title = "Note Details"

            arguments?.let {
                note = EditNoteFragmentArgs.fromBundle(it).note

                Logger.getLogger("note_title").warning(note?.text)
                note_title.text = note?.tilte
                note_content.setText(note?.text)
            }


        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_details_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_settings -> {
                Toast.makeText(context, "Settings", Toast.LENGTH_SHORT).show()
            }

            R.id.menu_save -> {

                hideSoftKeyboard(requireActivity(), requireView())

                val noteTitle: String = note_title.text.toString().trim()

                if (noteTitle.isEmpty()) {
                    note_title.error = "This is required"
                    note_title.requestFocus()
                    return false
                }

                val noteContent: String = note_content.text.toString().trim()

                if (noteContent.isEmpty()) {
                    note_content.error = "This is required"
                    note_content.requestFocus()
                    return false
                }


                launch {
                    val updatedNote = Note(
                        noteTitle,
                        noteContent,
                        getCurrenDate(),
                        false,
                        System.currentTimeMillis()
                    )


                    context?.let {

                        if (note == null) {
                            NotesDatabase.invoke(it)
                                .notesDao()
                                .newNote(updatedNote)
                            it.toast("Note saved")
                        } else {
                            updatedNote.id = note!!.id
                            NotesDatabase.invoke(it).notesDao().updateNote(updatedNote)
                            it.toast("Note updated")
                        }

                        val action =
                            EditNoteFragmentDirections.actionEditNoteFragment2ToAddNewNote()
                        Navigation.findNavController(requireView()).navigate(action)
                    }
                }
            }

            R.id.menu_delete -> run {
                hideSoftKeyboard(requireActivity(), requireView())

                if (note == null) {
                    context?.toast("Cannot delete empty note")
                } else
                    note?.let { deleteNote(it) }

            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun deleteNote(note: Note) {

        AlertDialog.Builder(requireContext()).apply {
            setMessage("Delete note?")
            setPositiveButton("YES") { dialogInterface, i ->
                run {
                    launch {
                        NotesDatabase.invoke(requireContext())
                            .notesDao()
                            .delete(note)
                        dialogInterface.dismiss()
                        requireContext().toast("deleted")

                        val action =
                            EditNoteFragmentDirections.actionEditNoteFragment2ToAddNewNote()
                        Navigation.findNavController(requireView()).navigate(action)

                    }
                }
            }

            setNegativeButton("NO") { dialogInterface, i ->
                dialogInterface.dismiss()

            }
            show()
        }


    }


}
