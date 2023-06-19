package com.example.mynotes.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentHomeBinding
import com.example.mynotes.model.Notes
import com.example.mynotes.ui.adapter.NotesAdapter
import com.example.mynotes.view_model.NotesViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val viewModel: NotesViewModel by viewModels()
    var oldNotes = arrayListOf<Notes>()
    lateinit var newList: ArrayList<Notes>
    lateinit var adapter: NotesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager


        viewModel.getNotes().observe(viewLifecycleOwner) {
            oldNotes = it as ArrayList<Notes>
            adapter = NotesAdapter(context, it)
            binding.rcvAllNotes.adapter = adapter
        }

        binding.removeFilter.setOnClickListener {
            viewModel.getNotes().observe(viewLifecycleOwner) {
                oldNotes = it as ArrayList<Notes>
                adapter = NotesAdapter(context, it)
                binding.rcvAllNotes.adapter = adapter
            }
        }

        binding.filterLow.setOnClickListener {
            viewModel.getLowNotes().observe(viewLifecycleOwner) {
                oldNotes = it as ArrayList<Notes>
                adapter = NotesAdapter(context, it)
                binding.rcvAllNotes.adapter = adapter
            }

        }

        binding.filterMedium.setOnClickListener {
            viewModel.getMediumNotes().observe(viewLifecycleOwner) {
                oldNotes = it as ArrayList<Notes>
                adapter = NotesAdapter(context, it)
                binding.rcvAllNotes.adapter = adapter
            }

        }

        binding.filterHigh.setOnClickListener {
            viewModel.getHighNotes().observe(viewLifecycleOwner) {
                oldNotes = it as ArrayList<Notes>
                adapter = NotesAdapter(context, it)
                binding.rcvAllNotes.adapter = adapter
            }
        }

        binding.btnAddNotes.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        return binding.root

    }

//    @Deprecated("Deprecated in Java")
   /* override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {

        menuInflater.inflate(R.menu.find_menu, menu)
        val item =  menu.findItem(R.id.search)
        val searchView = item.actionView as android.widget.SearchView
        searchView.queryHint = "Enter Notes Here..."
        super.onCreateOptionsMenu(menu, menuInflater)
    }*/

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.find_menu, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.queryHint = "Search for something"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search query submit here
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                Log.d("TAG", "onQueryTextChange: $newText")
                notesFilter(newText)
                return true
            }
        })
    }

    private fun notesFilter(newText: String?) {
        newList = arrayListOf<Notes>()
        for(i in oldNotes){
            if(i.title.contains(newText!!) || i.subTitle.contains(newText)){
                newList.add(i)
            }
        }
        adapter.filtering(newList)
    }

}