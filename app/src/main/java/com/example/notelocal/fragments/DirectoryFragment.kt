package com.example.diploma.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.diploma.model.Dao
import com.example.diploma.model.Directory
import com.example.diploma.model.NoteDatabase
import com.example.note.adapter.DirectoryAdapter
import com.example.note.fragments.OpenDirectoryFragment
import com.example.notelocal.R
import com.example.notelocal.databinding.FragmentDirectoryBinding
import kotlinx.coroutines.launch


class DirectoryFragment() : Fragment() {

    private lateinit var binding: FragmentDirectoryBinding
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var dao: Dao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDirectoryBinding.inflate(inflater, container, false)
        // Show created directories
        showDirectory()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // On click handler on button of adding new directory
        binding.btnCreateDirectory.setOnClickListener {
            val createDirectory = CreateDirectoryFragment(this)
            createDirectory.show(
                requireActivity().supportFragmentManager,
                "CreateDirectoryFragment"
            )
        }

        // On click handler for going to MainFragment
        binding.btnToMain.setOnClickListener {
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentHolder, MainFragment())
                ?.commit()
        }
    }

    // Method of showing created directories
    fun showDirectory() {
        noteDatabase = NoteDatabase.getDatabase(requireContext())
        dao = noteDatabase.Dao()

        binding.rcDirectory.layoutManager = GridLayoutManager(requireContext(), 3)

        val adapter = DirectoryAdapter(listOf())
        binding.rcDirectory.adapter = adapter

        lifecycleScope.launch {
            val directory = dao.getAllDirectory()
            adapter.directory = directory
            adapter.notifyDataSetChanged()
        }

        // On click listener for Directory
        adapter.setOnClickListener(object : DirectoryAdapter.OnClickListener {
            override fun onClick(directory: Directory) {
                activity
                    ?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.fragmentHolder, OpenDirectoryFragment(directory.id))
                    ?.commit()
            }
        })
    }

    companion object {
        fun newInstance() = DirectoryFragment()
    }
}