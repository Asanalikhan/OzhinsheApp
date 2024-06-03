package com.example.ozhinshe

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.ozhinshe.data.Item
import com.example.ozhinshe.data.ItemViewModel
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.databinding.FragmentBookmarkBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var mainApi: MainApi
    private lateinit var itemViewModel: ItemViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        itemViewModel.getAllData.observe(viewLifecycleOwner, Observer { items ->
            getAllData(items)
        })


//        lifecycleScope.launch(Dispatchers.IO) {
//            var response = mainApi.getMovie(id, )
//        }
    }

    fun getAllData(listItems: List<Item>){
        var listItem = listItems
        initRetrofit()
        val token = getToken()
//        lifecycleScope.launch(Dispatchers.IO) {
//            var response = mainApi.getMovie(listItem.)
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