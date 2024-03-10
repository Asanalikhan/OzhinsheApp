package com.example.ozhinshe

import android.os.Bundle
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.UpdateAppearance
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ozhinshe.databinding.FragmentDetailedBinding

class DetailedFragment : Fragment() {

    private lateinit var binding: FragmentDetailedBinding
    private var isTextVisible = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val textView: TextView = binding.textView
//        val button: Button = binding.buttonToggle
//
//        val longText = getString(R.string.your_long_text)
//        val alphaValues = floatArrayOf(1f, 0.8f, 0.6f, 0.4f, 0f)
//
//        val spannable = SpannableString(longText)
//        var startIndex = 0
//
//        for ((index, value) in alphaValues.withIndex()) {
//            val lineBreakIndex = findLineBreak(longText, startIndex)
//            val endIndex = if (index == alphaValues.size - 1) longText.length else lineBreakIndex
//            spannable.setSpan(AlphaSpan(value), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//            startIndex = lineBreakIndex + 1
//        }
//
//        textView.text = spannable

        // Click listener for the button to toggle text visibility
//        button.setOnClickListener {
//            toggleTextVisibility(textView, button)
//        }
    }

    private fun toggleTextVisibility(textView: TextView, button: Button) {
        isTextVisible = !isTextVisible

        if (isTextVisible) {
            textView.maxLines = Integer.MAX_VALUE
            button.text = "Collapse"
        } else {
            textView.maxLines = 5
            button.text = "Expand"
        }
    }

    private fun findLineBreak(text: String, startIndex: Int): Int {
        return text.indexOf("\n", startIndex)
    }
}

class AlphaSpan(private val alpha: Float) : CharacterStyle(), UpdateAppearance {

    override fun updateDrawState(tp: TextPaint) {
        tp.alpha = (alpha * 255).toInt()
    }
}
