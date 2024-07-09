package com.example.ozhinshe

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.ozhinshe.data.MainApi
import com.example.ozhinshe.modiedata.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var mainApi: MainApi
    private val sharedPreferences = getApplication<Application>().getSharedPreferences("Authotification", Context.MODE_PRIVATE)
    private val token = sharedPreferences.getString("token_key", null)

    private val _movies = MutableLiveData<List<Movy>>()
    val movies: LiveData<List<Movy>> get() = _movies

    private val _genres = MutableLiveData<List<Content>>()
    val genres: LiveData<List<Content>> get() = _genres

    private val _watched = MutableLiveData<List<Movy>>()
    val wathed: LiveData<List<Movy>> get() = _watched

    private val _zhoba = MutableLiveData<List<Movy>>()
    val zhoba: LiveData<List<Movy>> get() = _zhoba

    private val _reality = MutableLiveData<List<Movy>>()
    val reality: LiveData<List<Movy>> get() = _reality

    private val _telehikaya = MutableLiveData<List<Movy>>()
    val telehikaya: LiveData<List<Movy>> get() = _telehikaya

    private val _ages = MutableLiveData<List<CategoryAgesItem>>()
    val ages: LiveData<List<CategoryAgesItem>> get() = _ages

    private val _derekti = MutableLiveData<List<Movy>>()
    val derekti: LiveData<List<Movy>> get() = _derekti

    private val _shetel = MutableLiveData<List<Movy>>()
    val shetel: LiveData<List<Movy>> get() = _shetel

    private val _idfordetailed = MutableLiveData<Int>()
    val idfordetailed: LiveData<Int> get() = _idfordetailed

    private val _detailed = MutableLiveData<Movy>()
    val detailed: LiveData<Movy> get() = _detailed

    private val _uqsasgenre = MutableLiveData<List<ContentX>>()
    val uqsasgenre: LiveData<List<ContentX>> get() = _uqsasgenre

    private val _screenshot = MutableLiveData<List<Screenshot>>()
    val screenshot: LiveData<List<Screenshot>> get() = _screenshot

    init {
        initRetrofit()
        fetchHomeFragment()
    }

    private fun initRetrofit() {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val BASE_URL = "http://api.ozinshe.com"
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mainApi = retrofit.create(MainApi::class.java)
    }

    private fun fetchHomeFragment() {

        if (token != null) {
            viewModelScope.launch {
                try {
                    val response = mainApi.getMovies(token = "Bearer $token")
                    val response1 = mainApi.getGenres(token = "Bearer $token")
                    val response2 = mainApi.descMovies(token = "Bearer $token")
                    val response3 = mainApi.genreMovies(direction = "DESC", genreId = 31, token = "Bearer $token")
                    val response4 = mainApi.genreMovies(direction = "ASC", genreId = 5, token = "Bearer $token")
                    val response5 = mainApi.getCategoryAges(token = "Bearer $token")

                    _movies.value = response[1].movies
                    _watched.value = response[0].movies
                    _genres.value = response1.content
                    _zhoba.value = response2.content
                    _reality.value = response3.content
                    _telehikaya.value = response4.content
                    _ages.value = response5.toMutableList()
                    _derekti.value = response[3].movies
                    _shetel.value = response[4].movies
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Exception: ${e.message}")
                }
            }
        }
    }

    fun setIdDetaied(id: Int){
        _idfordetailed.value = id
        viewModelScope.launch {
            val responce = mainApi.getMovie(id = id, token = "Bearer $token")
            val responce1 = mainApi.uqsasMovies(direction = "DESC", genreId = id, token = "Bearer $token")
            val responce2 = mainApi.getScreenshots(id = id, token = "Bearer $token")

            _detailed.value = responce
            _uqsasgenre.value = responce1.content
            _screenshot.value = responce2
        }
    }
//    fun setSanat(int: Int){
//
//        viewModelScope.launch(Dispatchers.IO) {
//            val response: MovieResponce = mainApi.getMovies(token = "Bearer $token")
//            val responce2: DescMovies = mainApi.descMovies(token = "Bearer $token")
//            val responce3: DescMovies =mainApi.genreMovies(direction = "DESC", genreId = 31, token = "Bearer $token")
//            val responce4: DescMovies = mainApi.genreMovies(direction = "ASC", genreId = 5, token = "Bearer $token")
//            viewModelScope.launch(Dispatchers.Main) {
//                when(int){
//                    1 -> {
//                        _trend.value = response[0].movies
//                    }
//                    2 -> {
//                        _forsanat.value = response[1].movies
//                    }
//                    3 -> {
//                        _zhana.value = responce2.content
//                    }
//                    4 -> {
//                        _show.value = responce3.content
//                    }
//                    5 -> {
//                        _telhik.value = responce4.content
//                    }
//                    6 -> {
//                        _derek.value = response[3].movies
//                    }
//                    7 -> {
//                        adapter8.submitList(response[4].movies)
//                    }
//                }
//            }
//        }
//    }
}
