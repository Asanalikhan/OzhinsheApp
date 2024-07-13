package com.example.ozhinshe.presentation.profile

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.ozhinshe.R
import com.example.ozhinshe.databinding.TilFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Locale

class TilFragment: BottomSheetDialogFragment() {

    private var _binding: TilFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TilFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when(loadLocale()){
            "en" -> {updateDrawables(binding.english)}
            "kk" -> {updateDrawables(binding.qazaqsha)}
            "ru" -> {updateDrawables(binding.ruskiy)}
        }

        binding.english.setOnClickListener {
            updateDrawables(binding.english)
            setLocale("en")
            findNavController().popBackStack()
        }
        binding.qazaqsha.setOnClickListener {
            updateDrawables(binding.qazaqsha)
            setLocale("kk")
            findNavController().popBackStack()
        }
        binding.ruskiy.setOnClickListener {
            updateDrawables(binding.ruskiy)
            setLocale("ru")
            findNavController().popBackStack()
        }

    }

    private fun setLocale(localeName: String) {
        val locale = Locale(localeName)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

        val sharedPreferences = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("My_Lang", localeName)
            apply()
        }
        requireActivity().recreate()
        findNavController().navigate(R.id.profileFragment)
    }
    private fun loadLocale(): String {
        val sharedPreferences = requireContext().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        return language.toString()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}