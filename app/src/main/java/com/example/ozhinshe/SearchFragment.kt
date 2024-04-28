package com.example.ozhinshe

import android.content.ContentValues.TAG
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
import com.example.ozhinshe.adapters.SearchAdapter
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.databinding.FragmentSearchBinding
import com.example.ozhinshe.modiedata.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var mainApi: MainApi
    private lateinit var adapter: SearchAdapter
    private lateinit var response: SearchResponse
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()
        var token = getToken()
        adapter = SearchAdapter()
        initRecyclerView(adapter, binding.rcView)


        val credentials = "{}"
        val details = "{}"
        val principal = "{}"

        binding.apply {
            val textViewIds = arrayOf(telehikaya, seatcom, korkem, multfilm, multserial, anime, tvAndShow, derekti, music, shetel)
            for (textView in textViewIds) {
                var query = ""
                textView.setOnClickListener {
                    when (textView) {
                        telehikaya -> query = getString(R.string.telehikaya)
                        seatcom -> query = getString(R.string.seatcom)
                        korkem -> query = getString(R.string.korkem)
                        multfilm -> query = getString(R.string.multfilm)
                        multserial -> query = getString(R.string.multserial)
                        anime -> query = getString(R.string.anime)
                        tvAndShow -> query = getString(R.string.tv_and_show)
                        derekti -> query = getString(R.string.derekti)
                        music -> query = getString(R.string.music)
                        shetel -> query = getString(R.string.shetel)
                    }
                    binding.sanatIzdeu.text = "Іздеу нәтижелері"
                    binding.rcView.visibility = View.VISIBLE
                    binding.relativeLayout.visibility = View.GONE
                    lifecycleScope.launch(Dispatchers.IO) {
                        try {
                            val response = mainApi.search(query, credentials, details, principal, "Bearer $token")
                            lifecycleScope.launch(Dispatchers.Main) {
                                adapter.submitList(response)
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "Error fetching search results: ${e.message}", e)
                        }
                    }
                }
            }
        }

        binding.searchBtnIcon.setOnClickListener {
            val query = binding.searchBtnText.editText?.text.toString().trim()
            binding.sanatIzdeu.text = "Іздеу нәтижелері"
            binding.rcView.visibility = View.VISIBLE
            binding.relativeLayout.visibility = View.GONE
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val response = mainApi.search(query, credentials, details, principal, "Bearer $token")
                    lifecycleScope.launch(Dispatchers.Main) {
                        adapter.submitList(response)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Error fetching search results: ${e.message}", e)
                }
            }
        }
    }
    fun initRetrofit(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val BASE_URL = "http://api.ozinshe.com"
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build()
        mainApi = retrofit.create(MainApi::class.java)
    }
    fun getToken(): String{
        val sharedPreferences = requireContext().getSharedPreferences("Authotification", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token_key", null)
        return token.toString()
    }
    private fun initRecyclerView(adapter: RecyclerView.Adapter<*>, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }
}
