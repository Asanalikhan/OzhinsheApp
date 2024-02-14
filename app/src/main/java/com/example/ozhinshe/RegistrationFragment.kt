package com.example.ozhinshe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.core.view.isEmpty
import androidx.navigation.fragment.findNavController
import com.example.ozhinshe.databinding.FragmentRegistrationBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        binding.imageButton.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_onBoardFragment)
        }
        binding.toLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_authorizationFragment)
        }

        val inputLayoutEmail = binding.textInputLayout
        val inputLayoutPassword = binding.textInputLayout2
        val inputLayoutPassword2 = binding.textInputLayout3
        binding.btnRegistration.setOnClickListener {
            val email = inputLayoutEmail.editText?.text.toString().trim()
            val password = inputLayoutPassword.editText?.text.toString().trim()
            val password2 = inputLayoutPassword2.editText?.text.toString().trim()
            if(isValidEmail(email)){
                inputLayoutEmail.error = null
            }
            else{
                inputLayoutEmail.error ="Қате формат"
            }
            if(password.length >= 6){
                inputLayoutPassword.error = null
            }
            else{
                inputLayoutPassword.error ="Қате формат"
            }
            if(password2 == password){
                inputLayoutPassword2.error = null
            }
            else{
                inputLayoutPassword2.error = "Құпия сөздер тең емес"
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z][a-zA-Z0-9._-]*@[a-z]+\\.[a-z]+\$"
        return email.matches(emailRegex.toRegex())
    }
}
