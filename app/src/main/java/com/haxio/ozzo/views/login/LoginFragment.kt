package com.haxio.ozzo.views.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.haxio.ozzo.R
import com.haxio.ozzo.core.DataState
import com.haxio.ozzo.databinding.FragmentLoginBinding
import com.haxio.ozzo.isEmpty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        setListener()
        observeLoginResponse()
        return binding.root
    }

    private fun setListener() {
        with(binding) {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (!etEmail.isEmpty() && !etPassword.isEmpty()) {
                    viewModel.userLogin(email, password)
                }
            }

            btnRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun observeLoginResponse() {
        viewModel.loginResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Error -> {
                    Toast.makeText(context, "Login Failed: ${state.message}", Toast.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    Toast.makeText(context, "Logging in...", Toast.LENGTH_SHORT).show()
                }
                is DataState.Success -> {
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                }
            }
        }
    }
}