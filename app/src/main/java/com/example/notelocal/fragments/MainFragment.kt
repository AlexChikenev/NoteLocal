package com.example.diploma.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.diploma.model.Dao
import com.example.diploma.model.NoteDatabase
import com.example.notelocal.R
import com.example.notelocal.databinding.FragmentMainBinding
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var dao: Dao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container,false)

        noteDatabase = NoteDatabase.getDatabase(requireContext())
        dao = noteDatabase.Dao()

        lifecycleScope.launch {
//            dao.deleteAllFromDirectory()
//            dao.deleteAllFromDProject()
//            dao.deleteAllFromNote()
//            dao.resetInProject()
//            dao.resetInDirectory()
//            dao.resetInNote()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnToNotes.setOnClickListener{
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentHolder, DirectoryFragment())
                ?.commit()

        }

        binding.btnToWishList.setOnClickListener{
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentHolder, WishListFragment())
                ?.commit()
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}