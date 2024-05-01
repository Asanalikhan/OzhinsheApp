package com.example.ozhinshe

import android.content.ContentValues
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
import com.example.ozhinshe.adapters.BarlygyAdapter
import com.example.ozhinshe.adapters.SearchAdapter
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.data.OnItemClickListener
import com.example.ozhinshe.databinding.FragmentSanattarBinding
import com.example.ozhinshe.modiedata.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SanattarFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentSanattarBinding
    private lateinit var mainApi: MainApi
    private lateinit var adapter: SearchAdapter
    private lateinit var response: SearchResponse
    private lateinit var adapter1: BarlygyAdapter
    private var itemClickListener: OnItemClickListener? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSanattarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()
        val token = getToken()
        initRecyclerViewAdapters()
        initRecyclerView(adapter, binding.rcView)
        initRecyclerView(adapter1, binding.rcView1)

        val credentials = "{}"
        val details = "{}"
        val principal = "{}"
        val query: String? = arguments?.getString("string")
        val forWhat: Boolean? = arguments?.getBoolean("string1")

        query?.let {
            binding.cvMovieName.text = it
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    if(forWhat == true){
                        val responce1 = mainApi.uqsasMovies(direction = "DESC", genreId = id, token = "Bearer $token")
                        lifecycleScope.launch(Dispatchers.Main) {
                            adapter1.submitList(responce1.content)
                        }
                    }
                    else{
                        val response = mainApi.search(it, credentials, details, principal, "Bearer $token")
                        lifecycleScope.launch(Dispatchers.Main) {
                            adapter.submitList(response)
                        }
                    }

                } catch (e: Exception) {
                    Log.e(ContentValues.TAG, "Error fetching search results: ${e.message}", e)
                }
            }
        }
        binding.imageButton.setOnClickListener {
            (activity as? HomeActivity)?.replaceFragment(SearchFragment())
        }
    }
    fun initRetrofit(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val BASE_URL = "http://api.ozinshe.com"
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(
            GsonConverterFactory.create()).build()
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
    override fun onItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putString("key", id.toString())
        val detailedFragment = DetailedFragment()
        detailedFragment.arguments = bundle
        (activity as? HomeActivity)?.replaceFragment(detailedFragment)
    }
    override fun onSeasonClick(id: Int) {
        TODO("not implemented")
    }
    private fun initRecyclerViewAdapters() {
        adapter = SearchAdapter()
        adapter1 = BarlygyAdapter()
        adapter.setOnItemClickListener(this)
        adapter1.setOnItemClickListener(this)
    }
}