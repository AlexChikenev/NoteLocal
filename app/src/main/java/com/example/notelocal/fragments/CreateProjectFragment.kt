package com.example.note.fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.diploma.model.Dao
import com.example.diploma.model.NoteDatabase
import com.example.diploma.model.Project
import com.example.notelocal.databinding.FragmentCreateProjectBinding
import kotlinx.coroutines.launch
class CreateProjectFragment(
    private val openDirectory: OpenDirectoryFragment,
    private val id: Int?
) : DialogFragment() {
    private lateinit var binding: FragmentCreateProjectBinding
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var dao: Dao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateProjectBinding.inflate(inflater, container, false)

        // Get instance of database
        noteDatabase = NoteDatabase.getDatabase(requireContext())

        // Get instance of dao
        dao = noteDatabase.Dao()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button for project creation
        binding.btnCommitProject.setOnClickListener {
            // Check data
            if (binding.projectName.text.toString() != "") {
                // Write data into variable
                val project = Project(
                    directoryId = this.id,
                    name = binding.projectName.text.toString()
                )

                // Insert data from variable to table project
                try {
                    lifecycleScope.launch {
                        dao.insertIntoProject(project)
                        openDirectory.showCreatedProject()
                    }
                    dismiss()
                } catch (e: Exception) {
                    Log.d("Error in CreateProjectFragment", "$e")
                }
            } else {
                Toast.makeText(context, "Введите название своего проекта", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 1000)
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }
}