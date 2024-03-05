package com.example.ozhinshe

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.databinding.FragmentHomeBinding
import com.example.ozhinshe.modiedata.MovieResponce
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainApi: MainApi
    private lateinit var adapter: MainPageAdapter
    private lateinit var adapter1: WatchedMovieAdapter
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
        initRcView1()

        val sharedPreferences = requireContext().getSharedPreferences("Authotification", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token_key", null)
        lifecycleScope.launch {
            try {
                val response: MovieResponce = mainApi.getMovies(token = "Bearer $token")
                adapter.submitList(response[1].movies)
                adapter1.submitList1(response[0].movies)
            } catch (e: Exception) {
                Log.e("HomeFragment2", "Exception: ${e.message}")
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
    }
    private fun initRcView() = with(binding) {
        adapter = MainPageAdapter()
        rcView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcView.adapter = adapter
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rcView)
    }
    private fun initRcView1() = with(binding) {
        adapter1 = WatchedMovieAdapter()
        rcView1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rcView1.adapter = adapter1
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rcView1)
    }
}