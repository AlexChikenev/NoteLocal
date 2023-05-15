package com.example.diploma.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diploma.adapters.WishAdapter
import com.example.diploma.model.Dao
import com.example.diploma.model.NoteDatabase
import com.example.notelocal.R
import com.example.notelocal.databinding.FragmentWishListBinding
import kotlinx.coroutines.launch

class WishListFragment : Fragment() {

    private lateinit var binding: FragmentWishListBinding
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var dao: Dao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWishListBinding.inflate(inflater, container, false)
        showWishes()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCreateWish.setOnClickListener {
            val createWish = CreateWishFragment(this)
            createWish.show(
                requireActivity().supportFragmentManager,
                "CreateWishFragment"
            )
        }

        binding.back.setOnClickListener{
            activity
                ?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragmentHolder, MainFragment())
                ?.commit()
        }
    }

    fun showWishes() {
        noteDatabase = NoteDatabase.getDatabase(requireContext())
        dao = noteDatabase.Dao()

        binding.rcWishList.layoutManager = LinearLayoutManager(requireContext())

        val adapter = WishAdapter(listOf())
        binding.rcWishList.adapter = adapter

        lifecycleScope.launch {
            val wish = dao.getAllWishes()
            adapter.wish = wish
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance() = WishListFragment()
    }
}