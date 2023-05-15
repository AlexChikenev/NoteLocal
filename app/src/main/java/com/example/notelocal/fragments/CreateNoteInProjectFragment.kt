package com.example.diploma.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diploma.adapters.NoteAdapter
import com.example.diploma.model.Dao
import com.example.diploma.model.NoteDatabase
import com.example.note.fragments.CreateNoteFragment
import com.example.note.fragments.OpenDirectoryFragment
import com.example.notelocal.R
import com.example.notelocal.databinding.FragmentCreateNoteInProjectBinding
import kotlinx.coroutines.launch

class CreateNoteInProjectFragment(
    private val projectId: Int?,
    private val directoryId: Int?
) : Fragment() {

    private lateinit var binding: FragmentCreateNoteInProjectBinding
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var dao: Dao
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNoteInProjectBinding.inflate(inflater, container, false)
        // Get instance of database
        noteDatabase = NoteDatabase.getDatabase(requireContext())
        // Get instance of dao
        dao = noteDatabase.Dao()
        lifecycleScope.launch {
            binding.nameOfDirectory.text = "Заметки" + " " + "/" + " " + dao.getDirectoryName(directoryId) + " " + "/" + " " + dao.getProjectName(projectId)
        }
        showCreatedNotes()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCreateNote.setOnClickListener {
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentHolder, CreateNoteFragment(directoryId, projectId))
                ?.commit()
        }

        binding.btnGoBack.setOnClickListener{
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentHolder, OpenDirectoryFragment(directoryId))
                ?.commit()
        }

        // Go to main
        binding.btnToMain.setOnClickListener{
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentHolder, MainFragment())
                ?.commit()
        }
    }

    fun showCreatedNotes(){
        lifecycleScope.launch {
            try {
                val notes = dao.getAllNotesByProjectId(projectId)
                noteAdapter = NoteAdapter(notes)
                binding.rcOpenDirectoryNotes.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = noteAdapter
                }

            } catch (e: Exception) {
                Log.d("Exception", "${e.message}")
            }
        }
    }


//    companion object {
//        fun newInstance() = CreateNoteInProjectFragment()
//    }
}