package com.example.ozhinshe.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ozhinshe.databinding.ExitFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExitFragment: BottomSheetDialogFragment() {

    private lateinit var binding: ExitFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ExitFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.yes.setOnClickListener {
            exit()
            requireActivity().finishAffinity()
        }
        binding.no.setOnClickListener {
            dismiss()
        }
    }
    private fun exit(){
        val sharedPreferences = requireContext().getSharedPreferences("Authotification", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("token_key", "loged out")
        editor.apply()
    }
}