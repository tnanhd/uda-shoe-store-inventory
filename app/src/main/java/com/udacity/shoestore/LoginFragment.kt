package com.udacity.shoestore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.models.User
import com.udacity.shoestore.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var _binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        _binding.loginButton.setOnClickListener {
            if (!validateInputs()) {
                return@setOnClickListener
            }

            val username = _binding.usernameEdittext.text.toString()
            val password = _binding.passwordEdittext.text.toString()
            if (!authenticate(username, password)) {
                Toast.makeText(
                    requireContext(),
                    "Email or Password is incorrect. You can sign up for new user.",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            if (!_binding.remembermeCheckbox.isChecked) {
                _binding.usernameEdittext.setText("")
                _binding.passwordEdittext.setText("")
            }

            it.findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
        }

        _binding.signupButton.setOnClickListener {
            if (!validateInputs()) {
                return@setOnClickListener
            }

            val username = _binding.usernameEdittext.text.toString()
            val password = _binding.passwordEdittext.text.toString()
            createUser(username, password)

            if (!_binding.remembermeCheckbox.isChecked) {
                _binding.usernameEdittext.setText("")
                _binding.passwordEdittext.setText("")
            }

            it.findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
        }

        _binding.forgotpasswordTextview.setOnClickListener {
            _binding.usernameEdittext.setText(loginViewModel.users.value?.get(0)?.username)
            _binding.passwordEdittext.setText(loginViewModel.users.value?.get(0)?.password)
            Toast.makeText(requireContext(), "Sample user auto filled", Toast.LENGTH_SHORT)
                .show()
        }

        _binding.facebookIcon.setOnClickListener {
            showNotYetImplementedMessage("facebook")
        }

        _binding.githubIcon.setOnClickListener {
            showNotYetImplementedMessage("github")
        }

        _binding.googleIcon.setOnClickListener {
            showNotYetImplementedMessage("google")
        }

        return _binding.root
    }

    private fun showNotYetImplementedMessage(provider: String) {
        Toast.makeText(
            requireContext(),
            "Login with $provider not yet implemented",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun createUser(username: String, password: String) {
        val user = User(username, password)
        if (loginViewModel.addUser(user)) {
            Toast.makeText(requireContext(), "Created user. Signing in", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(requireContext(), "User existed. Signing in", Toast.LENGTH_SHORT)
                .show()

        }
    }

    private fun authenticate(username: String, password: String): Boolean {
        val user = User(username, password)
        return loginViewModel.authenticate(user)
    }

    private fun validateInputs(): Boolean {
        if (_binding.usernameEdittext.text.isEmpty() || _binding.passwordEdittext.text.isEmpty()) {
            Toast.makeText(requireContext(), "Email and Password are required", Toast.LENGTH_LONG)
                .show()
            return false
        }

        if (!isValidEmail(_binding.usernameEdittext.text.toString())) {
            Toast.makeText(requireContext(), "Email is in invalid format", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        )
        return emailPattern.matches(email)
    }
}