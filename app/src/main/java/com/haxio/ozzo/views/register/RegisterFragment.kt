package com.haxio.ozzo.views.register

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
import com.haxio.ozzo.data.models.UserRegistration
import com.haxio.ozzo.databinding.FragmentRegisterBinding
import com.haxio.ozzo.isEmpty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setListeners()
        registrationResponse()
        return binding.root
    }

    private fun setListeners() {
        with(binding) {
            btnRegister.setOnClickListener {
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if (!etName.isEmpty() && !etEmail.isEmpty() && !etPassword.isEmpty()) {
                    val user = UserRegistration(
                        name = name,
                        email = email,
                        password = password,
                        userType = "Seller",
                        userId = ""
                    )
                    viewModel.userRegistration(user)
                }
            }

            btLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun registrationResponse() {
        viewModel.registrationResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Error -> {
                    Toast.makeText(context, "Registration Failed: ${state.message}", Toast.LENGTH_LONG).show()
                }
                is DataState.Loading -> {
                    Toast.makeText(context, "Creating account...", Toast.LENGTH_SHORT).show()
                }
                is DataState.Success -> {
                    Toast.makeText(context, "Account created successfully!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }
        }
    }
}