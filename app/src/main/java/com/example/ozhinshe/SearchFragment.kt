package com.example.ozhinshe

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.ozhinshe.adapters.SearchAdapter
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.data.OnItemClickListener
import com.example.ozhinshe.databinding.FragmentSearchBinding
import com.example.ozhinshe.modiedata.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment(), OnItemClickListener {

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
        initRecyclerViewAdapters()
        initRecyclerView(adapter, binding.rcView)
        (activity as HomeActivity).showBottomNavigationView()

        val credentials = "{}"
        val details = "{}"
        val principal = "{}"
        var clicked = false

        binding.apply {
            val textViewIds = arrayOf(telehikaya, seatcom, korkem, multfilm, multserial, anime, tvAndShow, derekti, music, shetel)
            for (textView in textViewIds) {
                textView.setOnClickListener {
                    val sanattarFragment = SanattarFragment().apply {
                        arguments = Bundle().apply { putString("string", textView.text.toString()) }
                    }
                    findNavController().navigate(R.id.action_searchFragment_to_sanattarFragment)
                }
            }
        }
        binding.searchBtnIcon.setOnClickListener {
            clicked = true
            hideKeyboard(it)
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
        binding.apply {
            imageButton.setOnClickListener {
                if(clicked){
                    sanatIzdeu.text = "Санаттар"
                    rcView.visibility = View.GONE
                    relativeLayout.visibility = View.VISIBLE
                    searchBtnText.editText?.setText("")
                    searchBtnText.clearFocus()
                    clicked = false
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
    private fun initRecyclerViewAdapters() {
        adapter = SearchAdapter()
        adapter.setOnItemClickListener1(this)
    }
    override fun onItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putString("key", id.toString())
        findNavController().navigate(R.id.action_searchFragment_to_detailedFragment, bundle)
    }
    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    override fun onSeasonClick(id: Int) {
        TODO("Not yet implemented")
    }
}
