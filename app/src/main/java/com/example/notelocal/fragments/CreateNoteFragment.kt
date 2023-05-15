package com.example.note.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.diploma.fragments.CreateNoteInProjectFragment
import com.example.diploma.fragments.MainFragment
import com.example.diploma.model.Dao
import com.example.diploma.model.Note
import com.example.diploma.model.NoteDatabase
import com.example.notelocal.R
import com.example.notelocal.databinding.FragmentCreateNoteBinding
import kotlinx.coroutines.launch

class CreateNoteFragment(
    private val directoryId: Int?,
    private val projectId: Int?
) : Fragment() {

    private lateinit var binding: FragmentCreateNoteBinding
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var dao: Dao
    private lateinit var note: Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        noteDatabase = NoteDatabase.getDatabase(requireContext())
        dao = noteDatabase.Dao()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGoBack.setOnClickListener {

            if(projectId == null){
                activity
                    ?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.fragmentHolder, OpenDirectoryFragment(directoryId))
                    ?.commit()
            }else{
                activity
                    ?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.fragmentHolder, CreateNoteInProjectFragment(projectId, directoryId))
                    ?.commit()
            }
        }

        binding.btnCommitNote.setOnClickListener {
            if (projectId == null) {
                note = Note(
                    directoryId = this.directoryId,
                    projectId = null,
                    name = binding.noteName.text.toString(),
                    content = binding.noteContent.text.toString()
                )
                activity
                    ?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.fragmentHolder, OpenDirectoryFragment(directoryId))
                    ?.commit()
            }else{
                note = Note(
                    directoryId = directoryId,
                    projectId = projectId,
                    name = binding.noteName.text.toString(),
                    content = binding.noteContent.text.toString()
                )
                activity
                    ?.supportFragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.fragmentHolder, CreateNoteInProjectFragment(projectId, directoryId))
                    ?.commit()
            }
            lifecycleScope.launch {
                dao.insertIntoNote(note)
            }
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

//    companion object {
//        fun newInstance() = CreateNoteFragment(null)
//    }
}