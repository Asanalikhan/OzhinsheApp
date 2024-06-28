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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.ozhinshe.adapters.BarlygyAdapter
import com.example.ozhinshe.adapters.Derekti2Adapter
import com.example.ozhinshe.adapters.ItsForYouAdapter
import com.example.ozhinshe.adapters.RealityShowAdapter
import com.example.ozhinshe.adapters.SearchAdapter
import com.example.ozhinshe.adapters.Shetel2Adapter
import com.example.ozhinshe.adapters.Telehikaya2Adapter
import com.example.ozhinshe.adapters.TrendtegiAdapter
import com.example.ozhinshe.adapters.ZhanaZobaAdapter
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.data.OnItemClickListener
import com.example.ozhinshe.databinding.FragmentSanattarBinding
import com.example.ozhinshe.modiedata.DescMovies
import com.example.ozhinshe.modiedata.MovieResponce
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
    private lateinit var adapter1: BarlygyAdapter
    private lateinit var adapter2: TrendtegiAdapter
    private lateinit var adapter3: ItsForYouAdapter
    private lateinit var adapter4: ZhanaZobaAdapter
    private lateinit var adapter5: RealityShowAdapter
    private lateinit var adapter6: Telehikaya2Adapter
    private lateinit var adapter7: Derekti2Adapter
    private lateinit var adapter8: Shetel2Adapter

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

        val credentials = "{}"
        val details = "{}"
        val principal = "{}"

        val query: String? = arguments?.getString("string")
        val forWhat: Boolean? = arguments?.getBoolean("string1")
        val int: Int? = arguments?.getInt("int")

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
        lifecycleScope.launch(Dispatchers.IO) {
            val response: MovieResponce = mainApi.getMovies(token = "Bearer $token")
            val responce2: DescMovies = mainApi.descMovies(token = "Bearer $token")
            val responce3: DescMovies =mainApi.genreMovies(direction = "DESC", genreId = 31, token = "Bearer $token")
            val responce4: DescMovies = mainApi.genreMovies(direction = "ASC", genreId = 5, token = "Bearer $token")
            lifecycleScope.launch(Dispatchers.Main) {
                when(int){
                    1 -> {
                        adapter2.submitList(response[0].movies)
                    }
                    2 -> {
                        adapter3.submitList(response[1].movies)
                    }
                    3 -> {
                        adapter4.submitList(responce2.content)
                    }
                    4 -> {
                        adapter5.submitList(responce3.content)
                    }
                    5 -> {
                        adapter6.submitList(responce4.content)
                    }
                    6 -> {
                        adapter7.submitList(response[3].movies)
                    }
                    7 -> {
                        adapter8.submitList(response[4].movies)
                    }
                }
            }
        }

        (activity as HomeActivity).hideBottomNavigationView()

        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
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
        findNavController().navigate(R.id.action_sanattarFragment_to_detailedFragment, bundle)
    }
    override fun onSeasonClick(id: Int) {
        TODO("not implemented")
    }
    private fun initRecyclerViewAdapters() {
        adapter = SearchAdapter()
        adapter1 = BarlygyAdapter()
        adapter.setOnItemClickListener(this)
        initRecyclerView(adapter, binding.rcView)
        adapter1.setOnItemClickListener(this)
        initRecyclerView(adapter1, binding.rcView1)
        adapter2 = TrendtegiAdapter()
        adapter2.setOnItemClickListener(this)
        initRecyclerView(adapter2, binding.rcView2)
        adapter3 = ItsForYouAdapter()
        adapter3.setOnItemClickListener(this)
        initRecyclerView(adapter3, binding.rcView3)
        adapter4 = ZhanaZobaAdapter()
        adapter4.setOnItemClickListener(this)
        initRecyclerView(adapter4, binding.rcView4)
        adapter5 = RealityShowAdapter()
        adapter5.setOnItemClickListener(this)
        initRecyclerView(adapter5, binding.rcView5)
        adapter6 = Telehikaya2Adapter()
        adapter6.setOnItemClickListener(this)
        initRecyclerView(adapter6, binding.rcView6)
        adapter7 = Derekti2Adapter()
        adapter7.setOnItemClickListener(this)
        initRecyclerView(adapter7, binding.rcView7)
        adapter8 = Shetel2Adapter()
        adapter8.setOnItemClickListener(this)
        initRecyclerView(adapter8, binding.rcView8)
    }
}