package com.example.diploma.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.diploma.model.Directory
import com.example.diploma.model.NoteDatabase
import androidx.lifecycle.lifecycleScope
import com.example.diploma.model.Dao
import com.example.notelocal.databinding.FragmentCreateDirectoryBinding
import kotlinx.coroutines.launch

class CreateDirectoryFragment(private val directoryFragment: DirectoryFragment) : DialogFragment() {

    private lateinit var binding: FragmentCreateDirectoryBinding
    private lateinit var noteDatabase: NoteDatabase
    private lateinit var dao: Dao
    private lateinit var gestureDetector: GestureDetector

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateDirectoryBinding.inflate(inflater, container, false)

        gestureDetector = GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 != null && e2 != null) {
                    if (e1.y < e2.y && velocityY > 500)
                        dismiss()
                }
                return false
            }
        })

        binding.topBar.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
        directoryFragment.showDirectory()
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get instance of database
        noteDatabase = NoteDatabase.getDatabase(requireContext())

        // Get instance of DirectoryDao
        dao = noteDatabase.Dao()

        // Button of creation new directory
        binding.btnCommitDir.setOnClickListener {
            if (binding.editTextDirectoryName.text.toString() != "") {

                // Write data into variable
                val directory = Directory(
                    name = binding.editTextDirectoryName.text.toString()
                )

                // Insert data from variable directory into database
                lifecycleScope.launch {
                    dao.insertIntoDirectory(directory)
                    directoryFragment.showDirectory()
                    dismiss()
                }

            } else {
                Toast.makeText(context, "Введите название новой папки", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Parameters for this fragment
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, 1000)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }
}