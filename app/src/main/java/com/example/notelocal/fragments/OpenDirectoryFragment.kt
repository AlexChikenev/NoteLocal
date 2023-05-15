package com.example.note.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diploma.adapters.NoteAdapter
import com.example.diploma.adapters.ProjectAdapter
import com.example.diploma.fragments.CreateNoteInProjectFragment
import com.example.diploma.fragments.DirectoryFragment
import com.example.diploma.fragments.MainFragment
import com.example.diploma.model.Dao
import com.example.diploma.model.NoteDatabase
import com.example.diploma.model.Project
import com.example.notelocal.R
import com.example.notelocal.databinding.FragmentOpenDirectoryBinding
import kotlinx.coroutines.launch

class OpenDirectoryFragment(
    private val directoryId: Int?
) : Fragment() {

    private lateinit var binding: FragmentOpenDirectoryBinding
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var dao: Dao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentOpenDirectoryBinding.inflate(inflater, container, false)
        // Get instance of database
        noteDatabase = NoteDatabase.getDatabase(requireContext())
        // Get instance of dao
        dao = noteDatabase.Dao()

        // Show directory name
        lifecycleScope.launch {
            val directoryName = dao.getDirectoryName(directoryId)
            binding.nameOfDirectory.text = "Заметки" + " " + "/" + " " + directoryName.toString()
        }
        showCreatedProject()
        showCreatedNotes()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Show create project fragment
        binding.btnAddProject.setOnClickListener {
            val createProject = CreateProjectFragment(this, directoryId)
            createProject.show(
                requireActivity().supportFragmentManager,
                "CreateProjectFragment"
            )
        }

        // Show create note fragment
        binding.btnCreateNote.setOnClickListener {
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentHolder, CreateNoteFragment(directoryId, null))
                ?.commit()
        }

        // Go back
        binding.btnGoBack.setOnClickListener {
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentHolder, DirectoryFragment())
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

    // Method for showing created projects
    fun showCreatedProject() {
        binding.rcOpenDirectory.layoutManager = LinearLayoutManager(requireContext())

        val projectAdapter = ProjectAdapter(listOf())
        binding.rcOpenDirectory.adapter = projectAdapter

        lifecycleScope.launch {
            val projects = dao.getProjectsByDirectoryId(directoryId)
            projectAdapter.directoryToProject = projects
            projectAdapter.notifyDataSetChanged()
        }

        // Click on project item
        projectAdapter.setOnClickListener(object : ProjectAdapter.OnClickListener {
            override fun onClick(project: Project) {
                activity
                    ?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(
                        R.id.fragmentHolder,
                        CreateNoteInProjectFragment(project.id, directoryId)
                    )
                    ?.commit()
            }
        })
    }

    fun showCreatedNotes() {
        binding.rcOpenDirectoryNotes.layoutManager = LinearLayoutManager(requireContext())

        val noteAdapter = NoteAdapter(listOf())
        binding.rcOpenDirectoryNotes.adapter = noteAdapter

        // Show content from Project which contains directoryId like in Directory
        lifecycleScope.launch {
            try {
                val notes = dao.getAllNotesByDirectoryId(directoryId)
                noteAdapter.directoryToNote = notes
                noteAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Log.d("Exception", "${e.message}")
            }
        }
    }
}
//    companion object {
//        fun newInstance() = OpenDirectoryFragment()
//    }