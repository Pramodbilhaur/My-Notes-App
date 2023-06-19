package com.example.mynotes.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentCreateNotesBinding
import com.example.mynotes.model.Notes
import com.example.mynotes.view_model.NotesViewModel
import java.util.Date

class CreateNotesFragment : Fragment() {

    lateinit var binding: FragmentCreateNotesBinding
    var priority: String = "1"
    val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)
        binding.pGreen.setImageResource(R.drawable.baseline_done_24)
        with(binding) {

            pGreen.setOnClickListener {
                priority = "1"
                pGreen.setImageResource(R.drawable.baseline_done_24)
                pRed.setImageResource(0)
                pYellow.setImageResource(0)
            }

            pYellow.setOnClickListener {
                priority = "2"
                pYellow.setImageResource(R.drawable.baseline_done_24)
                pRed.setImageResource(0)
                pGreen.setImageResource(0)
            }

            pRed.setOnClickListener {
                priority = "3"
                pRed.setImageResource(R.drawable.baseline_done_24)
                pGreen.setImageResource(0)
                pYellow.setImageResource(0)
            }

        }

        binding.btnSaveNotes.setOnClickListener {
            createNotes(it)
        }

        return binding.root
    }

    private fun createNotes(it: View?) {
        with(binding) {
            val title = edtTitle.text
            val subtitle = edtSubTitle.text
            val notes = edtNotes.text
            val d = Date()
            val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy", d.time)
            val data = Notes(
                id = null,
                title = title.toString(),
                subTitle = subtitle.toString(),
                notes = notes.toString(),
                date = notesDate.toString(),
                priority
            )
            viewModel.addNotes(data)
            Toast.makeText(context, "Note has been created.", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it!!).navigate(R.id.action_createNotesFragment_to_homeFragment)
        }

    }
}