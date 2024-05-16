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
import com.example.ozhinshe.adapters.ScreenshotAdapter
import com.example.ozhinshe.adapters.UqsasAdapter
import com.example.ozhinshe.data.Item
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.data.MainDB
import com.example.ozhinshe.data.OnItemClickListener
import com.example.ozhinshe.databinding.FragmentDetailedBinding
import com.example.ozhinshe.modiedata.Movy
import com.example.ozhinshe.modiedata.Screenshot
import com.example.ozhinshe.modiedata.UqsasGenre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailedFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentDetailedBinding
    private lateinit var mainApi: MainApi
    private lateinit var responce: Movy
    private lateinit var responce1: UqsasGenre
    private lateinit var responce2: List<Screenshot>
    private lateinit var adapter: UqsasAdapter
    private lateinit var adapter1: ScreenshotAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()

        val sharedPreferences = requireContext().getSharedPreferences("Authotification", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token_key", null)

        val bundle = arguments
        val id = bundle?.getString("key")?.toInt()
        val db = MainDB.getDb(requireContext())

        binding.apply {
            moreBtn.setOnClickListener {
                if (id != null) onSeasonClick(id)
            }
            imageButton.setOnClickListener{
                (activity as? HomeActivity)?.replaceFragment(HomeFragment())
            }
            barlygy.setOnClickListener {
                val sanattarFragment = SanattarFragment().apply {
                    arguments = Bundle().apply { putString("string", "Ұқсас телехикаялар")
                    putBoolean("string1", true)}
                }
                (activity as? HomeActivity)?.replaceFragment(sanattarFragment)
            }

        }

        binding.imageButton2.setOnClickListener {// while onclick fun will add item to db by its id, it would to refactor
            binding.imageButton2.background.setTint(R.color.button2)
            val item = Item(null, id!!)
            Thread{
                db.getDao().insertItem(item)
            }.start()
        }

        adapter = UqsasAdapter(childFragmentManager)
        adapter.setOnItemClickListener(this)
        adapter1 = ScreenshotAdapter()
        initRecyclerView(adapter, binding.rcView2)
        initRecyclerView(adapter1, binding.rcView)
        lifecycleScope.launch(Dispatchers.IO) {
            try{
                responce = mainApi.getMovie(id = id, token = "Bearer $token")
                responce1 = mainApi.uqsasMovies(direction = "DESC", genreId = id, token = "Bearer $token")
                responce2 = mainApi.getScreenshots(id = id, token = "Bearer $token")
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
                    adapter1.submitList(responce2)
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

    override fun onItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putString("key", id.toString())
        val detailedFragment = DetailedFragment()
        detailedFragment.arguments = bundle
        (activity as? HomeActivity)?.replaceFragment(detailedFragment)
    }
    override fun onSeasonClick(id: Int) {
        val bolimFragment = BolimFragment()
        val bundle = Bundle()
        bundle.putInt("id", id)
        bundle.putInt("seriesCount", responce.seriesCount)
        bundle.putString("posterLink", responce.screenshots[0].link)
        bolimFragment.arguments = bundle
        (activity as? HomeActivity)?.replaceFragment(bolimFragment)
    }
}
