package com.example.ozhinshe.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.ozhinshe.HomeActivity
import com.example.ozhinshe.R
import com.example.ozhinshe.databinding.FragmentQupiyaBinding

class QupiyaFragment : Fragment() {

    private lateinit var binding: FragmentQupiyaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQupiyaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.errorMsg.visibility = View.GONE
        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.savePassword.setOnClickListener {
            if(isPasswordValid()){
                val sharedPreferences = requireContext().getSharedPreferences("Profile", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("password", binding.textInputLayout1.editText?.text.toString().trim())
                editor.apply()
                Toast.makeText(requireContext(), "Құпия сөз сәтті сақталды", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isPasswordValid():Boolean {
        val password1 = binding.textInputLayout1.editText?.text.toString().trim()
        val password2 = binding.textInputLayout2.editText?.text.toString().trim()
        if(password1.length >= 6 && password2 == password1){
            return true
        }else {
            binding.errorMsg.visibility = View.VISIBLE
        }
        return false
    }
}