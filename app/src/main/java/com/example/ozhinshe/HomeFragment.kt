package com.example.ozhinshe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainApi: MainApi
    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var adapter: MainPageAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()
        initRcView()

        viewModel.token.observe(viewLifecycleOwner) {token ->
            lifecycleScope.launch {
                try {
                    val list = mainApi.getMovies(token = token)
                    requireActivity().runOnUiThread {
                        adapter.submitList(list)
                        Toast.makeText(activity,"adapter.submitList",Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Log.e("HomeFragment", "Error fetching movies: ${e.message}")
                }
            }
        }
    }
    private fun initRetrofit(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val BASE_URL = "http://api.ozinshe.com"
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).
        client(client).addConverterFactory(GsonConverterFactory.create()).build()
        mainApi = retrofit.create(MainApi::class.java)
        Toast.makeText(activity,"initRetrofit",Toast.LENGTH_SHORT).show()
    }

    private fun initRcView() = with(binding){
        adapter = MainPageAdapter()
        rcView.layoutManager = LinearLayoutManager(context)
        rcView.adapter = adapter
        Toast.makeText(activity,"initRcView",Toast.LENGTH_SHORT).show()
    }

}