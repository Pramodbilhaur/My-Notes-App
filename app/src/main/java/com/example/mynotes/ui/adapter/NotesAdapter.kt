package com.example.mynotes.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.databinding.ItemNotesBinding
import com.example.mynotes.model.Notes
import com.example.mynotes.ui.adapter.NotesAdapter.NotesViewHolder
import com.example.mynotes.ui.fragments.HomeFragmentDirections

class NotesAdapter(val context: Context?, var notes: List<Notes>) :
    RecyclerView.Adapter<NotesViewHolder>() {

fun filtering(newList: ArrayList<Notes>) {
notes = newList
    notifyDataSetChanged()
}

    inner class NotesViewHolder(val binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        with(holder.binding){
            notesTitle.text = notes[position].title
            notesSubtitle.text= notes[position].subTitle
            notesDate.text = notes[position].date
            when(notes[position].priority){
                "1" -> {viewPriority.setBackgroundResource(R.drawable.green_dot)}
                "2" -> {viewPriority.setBackgroundResource(R.drawable.yellow_dot)}
                "3" -> {viewPriority.setBackgroundResource(R.drawable.red_dot)}
            }
            root.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToEditNotesFragment(notes[position])
                Navigation.findNavController(it!!).navigate(action)
            }
        }
    }
}