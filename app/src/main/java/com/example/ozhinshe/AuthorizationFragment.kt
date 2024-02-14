package com.example.ozhinshe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ozhinshe.databinding.FragmentAuthorizationBinding

class AuthorizationFragment : Fragment() {

    private lateinit var binding: FragmentAuthorizationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorizationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_registrationFragment)
        }
        binding.btnAuthorization.setOnClickListener {
            findNavController().navigate(R.id.action_authorizationFragment_to_onBoardFragment)
        }

        val inputEmail = binding.textInputLayout
        val inputPassworld = binding.textInputLayout2
        binding.btnAuthorization.setOnClickListener {
            val email = inputEmail.editText?.text.toString().trim()
            val password = inputPassworld.editText?.text.toString().trim()

            if(isValidEmail(email)){
                inputEmail.error = null
            }
            else{
                inputEmail.error = "Қате формат"
            }
            if(password.length >= 6){
                inputPassworld.error = null
            }
            else{
                inputPassworld.error = "Қате формат"
            }
        }
    }
    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[a-zA-Z][a-zA-Z0-9._-]*@[a-z]+\\.[a-z]+\$"
        return email.matches(emailRegex.toRegex())
    }
}