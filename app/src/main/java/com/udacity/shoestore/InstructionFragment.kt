package com.udacity.shoestore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.udacity.shoestore.databinding.FragmentInstructionBinding

class InstructionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentInstructionBinding.inflate(inflater, container, false)
        binding.nextButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_instructionFragment_to_productListFragment)
        )
        return binding.root
    }
}