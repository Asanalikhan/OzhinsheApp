package com.example.ozhinshe.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ozhinshe.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tilFragment = TilFragment()
        val exitFragment = ExitFragment()
        binding.til.setOnClickListener {
            tilFragment.show(childFragmentManager, "BottomDialog")
        }
        binding.exitButton.setOnClickListener {
            exitFragment.show(childFragmentManager, "BottomDialog")
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}