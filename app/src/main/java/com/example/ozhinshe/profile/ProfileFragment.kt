package com.example.ozhinshe.profile

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.ozhinshe.HomeActivity
import com.example.ozhinshe.HomeFragment
import com.example.ozhinshe.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadEmail()

        val tilFragment = TilFragment()
        val exitFragment = ExitFragment()

        binding.til.setOnClickListener {
            tilFragment.show(childFragmentManager, "BottomDialog")
        }
        binding.exitButton.setOnClickListener {
            exitFragment.show(childFragmentManager, "BottomDialog")
        }
        binding.zhekeDerek.setOnClickListener {
            (activity as? HomeActivity)?.replaceFragment(ZhekeDerekterFragment())
        }
        binding.password.setOnClickListener {
            (activity as? HomeActivity)?.replaceFragment(QupiyaFragment())
        }

        binding.qarangyRezhim.setOnCheckedChangeListener {_,  isCheked ->
            if (isCheked) setDarkTheme()
            else setLightTheme()
            setTheme(isCheked)
        }

        binding.qarangyRezhim.isChecked = getTheme()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadEmail(){
        val sharedPreferences = requireContext().getSharedPreferences("Profile", Context.MODE_PRIVATE)
        binding.tvEmailText.text = Editable.Factory.getInstance().newEditable(sharedPreferences.getString("email", ""))
    }
    private fun setLightTheme(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    private fun setDarkTheme(){
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
    private fun getTheme():Boolean{
        val sharedPreferences = requireContext().getSharedPreferences("Theme", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isDarkTheme", false)
    }
    private fun setTheme(isDard: Boolean){
        val sharedPreferences = requireContext().getSharedPreferences("Theme", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isDarkTheme", isDard)
        editor.apply()
    }
}