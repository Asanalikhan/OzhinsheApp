package com.example.ozhinshe

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
import com.example.ozhinshe.adapters.*
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.data.OnItemClickListener
import com.example.ozhinshe.databinding.FragmentHomeBinding
import com.example.ozhinshe.decoration.CardDecoration
import com.example.ozhinshe.modiedata.*
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainApi: MainApi
    private lateinit var adapter: MainPageAdapter
    private lateinit var adapter1: WatchedMovieAdapter
    private lateinit var adapter2: TrendAdapter
    private lateinit var adapter3: ForYouAdapter
    private lateinit var adapter4: GenresAdapter
    private lateinit var adapter5: ZhobalarAdapter
    private lateinit var adapter6: RealityAdapter
    private lateinit var adapter7: TelehikayaAdapter
    private lateinit var adapter8: AgesAdapter
    private lateinit var adapter9: DerektiAdapter
    private lateinit var adapter10: ShetelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()
        initRecyclerViewAdapters()
        initRecyclerViews()
        setOffSetToRecView()
        (activity as HomeActivity).showBottomNavigationView()

        val sharedPreferences = requireContext().getSharedPreferences("Authotification", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token_key", null)
        lifecycleScope.launch {
            try {
                val response: MovieResponce = mainApi.getMovies(token = "Bearer $token")
                val responce1: GenresResponce = mainApi.getGenres(token = "Bearer $token")
                val responce2: DescMovies = mainApi.descMovies(token = "Bearer $token")
                val responce3: DescMovies = mainApi.genreMovies(direction = "DESC", genreId = 31, token = "Bearer $token")
                val responce4: DescMovies = mainApi.genreMovies(direction = "ASC", genreId = 5, token = "Bearer $token")
                val responce5: CategoryAges = mainApi.getCategoryAges(token = "Bearer $token")
                adapter.submitList(response[1].movies)
                adapter1.submitList1(response[0].movies)
                adapter2.submitList(response[0].movies)
                adapter3.submitList(response[1].movies)
                adapter4.submitList(responce1.content)
                adapter5.submitList(responce2.content)
                adapter6.submitList(responce3.content)
                adapter7.submitList(responce4.content)
                adapter8.submitList(responce5.toMutableList())
                adapter9.submitList(response[3].movies)
                adapter10.submitList(response[4].movies)
            } catch (e: Exception) {
                Log.e("HomeFragment2", "Exception: ${e.message}")
            }
        }
        binding.apply {
            barlygy1.setOnClickListener { toSanat(1)}
            barlygy2.setOnClickListener { toSanat(2)}
            barlygy3.setOnClickListener { toSanat(3)}
            barlygy4.setOnClickListener { toSanat(4)}
            barlygy5.setOnClickListener { toSanat(5)}
            barlygy6.setOnClickListener { toSanat(6)}
            barlygy7.setOnClickListener { toSanat(7)}
        }
    }

    private fun initRetrofit(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val BASE_URL = "http://api.ozinshe.com"
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mainApi = retrofit.create(MainApi::class.java)
    }

    private fun initRecyclerViews() {
        initRecyclerView(adapter, binding.rcView)
        initRecyclerView(adapter1, binding.rcView1)
        initRecyclerView(adapter2, binding.rcView2)
        initRecyclerView(adapter3, binding.rcView3)
        initRecyclerView(adapter4, binding.rcView4)
        initRecyclerView(adapter5, binding.rcView5)
        initRecyclerView(adapter6, binding.rcView6)
        initRecyclerView(adapter7, binding.rcView7)
        initRecyclerView(adapter8, binding.rcView8)
        initRecyclerView(adapter9, binding.rcView9)
        initRecyclerView(adapter10, binding.rcView10)
    }

    private fun initRecyclerViewAdapters() {
        adapter = MainPageAdapter(childFragmentManager)
        adapter.setOnItemClickListener(this)
        adapter1 = WatchedMovieAdapter(childFragmentManager)
        adapter1.setOnItemClickListener(this)
        adapter2 = TrendAdapter(childFragmentManager)
        adapter2.setOnItemClickListener(this)
        adapter3 = ForYouAdapter(childFragmentManager)
        adapter3.setOnItemClickListener(this)
        adapter4 = GenresAdapter()
        adapter5 = ZhobalarAdapter(childFragmentManager)
        adapter5.setOnItemClickListener(this)
        adapter6 = RealityAdapter(childFragmentManager)
        adapter6.setOnItemClickListener(this)
        adapter7 = TelehikayaAdapter(childFragmentManager)
        adapter7.setOnItemClickListener(this)
        adapter8 = AgesAdapter()
        adapter9 = DerektiAdapter(childFragmentManager)
        adapter9.setOnItemClickListener(this)
        adapter10 = ShetelAdapter(childFragmentManager)
        adapter10.setOnItemClickListener(this)
    }
    private fun setOffSetToRecView(){
        binding.rcView.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 40f.toInt()))
        binding.rcView1.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt()))
        binding.rcView2.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt()))
        binding.rcView3.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt()))
        binding.rcView4.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt()))
        binding.rcView5.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt()))
        binding.rcView6.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt()))
        binding.rcView7.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt()))
        binding.rcView8.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt()))
        binding.rcView9.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt()))
        binding.rcView10.addItemDecoration(CardDecoration(rightOffset = 24f.toInt(), leftOffSet = 24f.toInt(), topOffset = 24f.toInt()))
    }
    private fun initRecyclerView(adapter: RecyclerView.Adapter<*>, recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }

    override fun onItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putString("key", id.toString())
        findNavController().navigate(R.id.action_homeFragment_to_detailedFragment, bundle)
    }

    fun toSanat(int : Int){
        val sanattarFragment = SanattarFragment().apply {
            arguments = Bundle().apply { putInt("int", int) }
        }
        findNavController().navigate(R.id.action_homeFragment_to_sanattarFragment, sanattarFragment.arguments)
    }

    override fun onSeasonClick(id: Int) {
        // TODO: Not yet implemented
    }
}
