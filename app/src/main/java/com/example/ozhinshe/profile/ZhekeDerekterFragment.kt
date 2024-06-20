package com.example.ozhinshe.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ozhinshe.HomeActivity
import com.example.ozhinshe.HomeFragment
import com.example.ozhinshe.R
import com.example.ozhinshe.databinding.FragmentZhekeDerekterBinding

class ZhekeDerekterFragment : Fragment() {

    private lateinit var binding: FragmentZhekeDerekterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentZhekeDerekterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButton.setOnClickListener {
            (activity as? HomeActivity)?.replaceFragment(ProfileFragment())
        }

        loadData()

        binding.saveData.setOnClickListener {
            saveData()
        }

    }
    private fun saveData(){
        val profileSharedPreferences = requireContext().getSharedPreferences("Profile", Context.MODE_PRIVATE)
        val editor = profileSharedPreferences.edit()
        editor.apply{
            putString("name", binding.fullName.text.toString())
            putString("email", binding.tvEmailText.text.toString())
            putString("phonenumber", binding.phoneNumber.text.toString())
            putString("date", binding.birthDate.text.toString())
            apply()
            Toast.makeText(requireContext(), "Жеке деректер сәтті сақталды", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadData(){
        val profileSharedPreferences = requireContext().getSharedPreferences("Profile", Context.MODE_PRIVATE)

        val name = profileSharedPreferences.getString("name", "")
        val email = profileSharedPreferences.getString("email", "")
        val phone = profileSharedPreferences.getString("phonenumber", "")
        val birth = profileSharedPreferences.getString("date", "")

        binding.fullName.text = Editable.Factory.getInstance().newEditable(name)
        binding.tvEmailText.text = Editable.Factory.getInstance().newEditable(email)
        binding.phoneNumber.text = Editable.Factory.getInstance().newEditable(phone)
        binding.birthDate.text = Editable.Factory.getInstance().newEditable(birth)

    }
}