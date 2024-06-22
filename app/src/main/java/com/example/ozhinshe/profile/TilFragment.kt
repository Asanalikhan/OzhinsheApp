package com.example.ozhinshe.profile

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.ozhinshe.R
import com.example.ozhinshe.databinding.TilFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TilFragment: BottomSheetDialogFragment() {

    private lateinit var binding: TilFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TilFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.english.setOnClickListener { updateDrawables(binding.english) }
        binding.qazaqsha.setOnClickListener { updateDrawables(binding.qazaqsha) }
        binding.ruskiy.setOnClickListener { updateDrawables(binding.ruskiy) }

    }

    private fun updateDrawables(selectedTextView: TextView) {
        val checkDrawable = R.drawable.check
        val notCheckDrawable = R.drawable.not_check

        setDrawable(binding.english, if (selectedTextView == binding.english) checkDrawable else notCheckDrawable)
        setDrawable(binding.qazaqsha, if (selectedTextView == binding.qazaqsha) checkDrawable else notCheckDrawable)
        setDrawable(binding.ruskiy, if (selectedTextView == binding.ruskiy) checkDrawable else notCheckDrawable)
    }

    private fun setDrawable(textView: TextView, drawableId: Int) {
        textView.setCompoundDrawablesWithIntrinsicBounds(
            0,  // Drawable left
            0,  // Drawable top
            drawableId,  // Drawable right (end)
            0   // Drawable bottom
        )
    }
}