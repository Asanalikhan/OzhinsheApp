package com.example.ozhinshe

import android.annotation.SuppressLint
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
import com.bumptech.glide.Glide
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.databinding.FragmentDetailedBinding
import com.example.ozhinshe.modiedata.Movy
import com.example.ozhinshe.modiedata.UqsasGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailedFragment : Fragment(){

    private lateinit var binding: FragmentDetailedBinding
    private lateinit var mainApi: MainApi
    private lateinit var responce: Movy
    private lateinit var responce1: UqsasGenre
    private lateinit var adapter: UqsasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()

        val sharedPreferences = requireContext().getSharedPreferences("Authotification", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token_key", null)
        binding.imageButton.setOnClickListener{
            (activity as? HomeActivity)?.replaceFragment(HomeFragment())
        }
        val bundle = arguments
        val id = bundle?.getString("key")?.toInt()
        adapter = UqsasAdapter()
        initRecyclerView(adapter, binding.rcView2)

        lifecycleScope.launch(Dispatchers.IO) {
            try{
                responce = mainApi.getMovie(id = id, token = "Bearer $token")
                responce1 = mainApi.uqsasMovies(direction = "DESC", genreId = id, token = "Bearer $token")
                lifecycleScope.launch(Dispatchers.Main) {
                    binding.cvMovieName.text = responce.name
                    binding.cvMovieDesc.text = "" + responce.year + "." + responce.movieType + "." + responce.seasonCount + " сезон," + responce.seriesCount + " серия."
                    val into = Glide.with(binding.root).load(responce.poster.link)
                        .into(binding.imageView2)
                    binding.textView.text = responce.description
                    binding.director.text = responce.director
                    binding.producer.text = responce.producer
                    binding.bolimder.text = "" + responce.seasonCount + " сезон," + responce.seriesCount + " серия"
                    adapter.submitList(responce1.content)
                }
            }catch (e: Exception){
                Log.e("DetailedFragment", "Exception: ${e.message}")
                e.printStackTrace()
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
    private fun initRecyclerView(adapter: RecyclerView.Adapter<*>, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }
}
