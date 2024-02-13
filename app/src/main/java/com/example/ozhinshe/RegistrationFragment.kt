package com.example.ozhinshe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ozhinshe.databinding.FragmentRegistrationBinding

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
    }
}