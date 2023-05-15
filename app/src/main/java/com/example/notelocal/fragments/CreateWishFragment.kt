package com.example.diploma.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.diploma.model.Dao
import com.example.diploma.model.NoteDatabase
import com.example.diploma.model.Wish
import com.example.notelocal.databinding.FragmentCreateWishBinding
import kotlinx.coroutines.launch

class CreateWishFragment(private val wishListFragment: WishListFragment) : DialogFragment() {

    private lateinit var binding: FragmentCreateWishBinding
    private lateinit var noteDatabase: NoteDatabase
    lateinit var dao: Dao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateWishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteDatabase = NoteDatabase.getDatabase(requireContext())

        dao = noteDatabase.Dao()

        // Create new wish
        binding.btnCommitWish.setOnClickListener {
            if (binding.editTextWishName.text.toString() != "") {
                val wish = Wish(
                    name = binding.editTextWishName.text.toString()
                )

                lifecycleScope.launch {
                    dao.insertIntoWish(wish)
                    wishListFragment.showWishes()
                    dismiss()
                }
            }
        }
    }

    // Parameters for this fragment
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 1000)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }
}