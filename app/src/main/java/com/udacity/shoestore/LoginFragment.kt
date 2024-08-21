package com.udacity.shoestore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.udacity.shoestore.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var _binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        _binding.loginButton.setOnClickListener {
            validateInputs()
            it.findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
        }
        _binding.signupButton.setOnClickListener {
            validateInputs()
            it.findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
        }
        return _binding.root
    }

    private fun validateInputs() {
        if (_binding.usernameEdittext.text.isEmpty() || _binding.passwordEdittext.text.isEmpty()) {
            Toast.makeText(requireContext(), "Email and Password are required", Toast.LENGTH_LONG).show()
            return
        }
    }
}