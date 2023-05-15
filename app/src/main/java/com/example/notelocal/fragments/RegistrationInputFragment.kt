package com.example.diploma.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.notelocal.databinding.FragmentRegistrationInputBinding

class RegistrationInputFragment: Fragment() {
    private lateinit var binding: FragmentRegistrationInputBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationInputBinding.inflate(inflater, container, false)
        return binding.root
    }
}