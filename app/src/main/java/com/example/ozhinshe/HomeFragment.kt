package com.example.ozhinshe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.ozhinshe.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        lifecycleScope.launch {
//            try{
//                val adapter = ExampleRecyclerAdapter()
//                val responce = RetrofitService.apiService.getAll()
//                val listEmoji: List<EmojiResponceItem> = responce.toList()
//                binding.rcClassmates.adapter = adapter
//                adapter.submitList(listEmoji)
//            }catch (e: Exception){
//                Log.d("aaa", e.message.toString())
//            }
//        }

    }

}