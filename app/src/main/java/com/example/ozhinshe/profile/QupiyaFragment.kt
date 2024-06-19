package com.example.ozhinshe.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

}