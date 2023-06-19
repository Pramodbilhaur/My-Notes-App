package com.example.mynotes.ui.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentEditNotesBinding
import com.example.mynotes.model.Notes
import com.example.mynotes.view_model.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class EditNotesFragment : Fragment() {
    val notes by navArgs<EditNotesFragmentArgs>()
    lateinit var binding: FragmentEditNotesBinding
    val viewModel: NotesViewModel by viewModels()
    var priority = "1"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)

        with(binding){
            edtTitle.setText(notes.data.title)
            edtSubTitle.setText(notes.data.subTitle)
            edtNotes.setText(notes.data.notes)

            when(notes.data.priority){
                "1"-> {
                    priority = "1"
                    pGreen.setImageResource(R.drawable.baseline_done_24)
                    pRed.setImageResource(0)
                    pYellow.setImageResource(0)
                }
                "2"-> {
                    priority = "2"
                    pYellow.setImageResource(R.drawable.baseline_done_24)
                    pRed.setImageResource(0)
                    pGreen.setImageResource(0)
                }
                "3"-> {
                    priority = "3"
                    pRed.setImageResource(R.drawable.baseline_done_24)
                    pGreen.setImageResource(0)
                    pYellow.setImageResource(0)
                }
            }

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
            btnEditSaveNotes.setOnClickListener {
                updateNotes(it)
            }
        }
        return binding.root
    }

    private fun updateNotes(it: View) {
        with(binding) {
            val title = edtTitle.text
            val subtitle = edtSubTitle.text
            val note = edtNotes.text
            val d = Date()
            val notesDate: CharSequence = DateFormat.format("MMMM d, yyyy", d.time)
            val data = Notes(
                id = notes.data.id,
                title = title.toString(),
                subTitle = subtitle.toString(),
                notes = note.toString(),
                date = notesDate.toString(),
                priority
            )
            viewModel.updateNotes(data)
            Navigation.findNavController(it).navigate(R.id.action_editNotesFragment_to_homeFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                val bottomSheet: BottomSheetDialog =
                    BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
                bottomSheet.setContentView(R.layout.dialog_delete)
                bottomSheet.show()

                bottomSheet.findViewById<TextView>(R.id.dialog_yes)?.setOnClickListener {
                    viewModel.deleteNotes(notes.data.id!!)
                    bottomSheet.dismiss()
                    Navigation.findNavController(binding.root).navigate(R.id.action_editNotesFragment_to_homeFragment)
                }

                bottomSheet.findViewById<TextView>(R.id.dialog_no)?.setOnClickListener {
                    bottomSheet.dismiss()
                }
//                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    }