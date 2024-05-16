package com.example.ozhinshe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.data.MainDB
import com.example.ozhinshe.databinding.FragmentBookmarkBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var mainApi: MainApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()
        val token = getToken()
        val db = MainDB.getDb(requireContext())

        binding.forTesting.text = db.getDao().getAllItem().toString()

//        lifecycleScope.launch(Dispatchers.IO) {
//            var response = mainApi.getMovie(id, )
//        }

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
}