package com.example.ozhinshe.domain.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.ozhinshe.domain.data.Item
import com.example.ozhinshe.domain.data.MainApi
import com.example.ozhinshe.domain.data.ServiceBuilder
import com.example.ozhinshe.domain.models.CategoryAgesItem
import com.example.ozhinshe.domain.models.Content
import com.example.ozhinshe.domain.models.ContentX
import com.example.ozhinshe.domain.models.Movy
import com.example.ozhinshe.domain.models.Screenshot
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val mainApi = ServiceBuilder.buildService(MainApi::class.java)
    private val token = ServiceBuilder.getToken()

    private val _movies = MutableLiveData<List<Movy>>()
    val movies: LiveData<List< Movy>> get() = _movies

    private val _genres = MutableLiveData<List<Content>>()
    val genres: LiveData<List<Content>> get() = _genres

    private val _watched = MutableLiveData<List<Movy>>()
    val wathed: LiveData<List<Movy>> get() = _watched

    private val _zhoba = MutableLiveData<List<Movy>>()
    val zhoba: LiveData<List<Movy>> get() = _zhoba

    private val _reality = MutableLiveData<List<Movy>>()
    val reality: LiveData<List< Movy>> get() = _reality

    private val _telehikaya = MutableLiveData<List< Movy>>()
    val telehikaya: LiveData<List< Movy>> get() = _telehikaya

    private val _ages = MutableLiveData<List<CategoryAgesItem>>()
    val ages: LiveData<List< CategoryAgesItem>> get() = _ages

    private val _derekti = MutableLiveData<List< Movy>>()
    val derekti: LiveData<List< Movy>> get() = _derekti

    private val _shetel = MutableLiveData<List< Movy>>()
    val shetel: LiveData<List<Movy>> get() = _shetel

    private val _idfordetailed = MutableLiveData<Int>()
    val idfordetailed: LiveData<Int> get() = _idfordetailed

    private val _detailed = MutableLiveData< Movy>()
    val detailed: LiveData< Movy> get() = _detailed

    private val _uqsasgenre = MutableLiveData<List<ContentX>>()
    val uqsasgenre: LiveData<List< ContentX>> get() = _uqsasgenre

    private val _screenshot = MutableLiveData<List<Screenshot>>()
    val screenshot: LiveData<List< Screenshot>> get() = _screenshot

    private var _moviesbookmark = MutableLiveData<List< Movy>>()
    val moviesbookmark: LiveData<List< Movy>> get() = _moviesbookmark

    init {
        fetchHomeFragment()
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

    fun getAllData(listItems: List<Item>) {
        val deferredMovies = mutableListOf<Deferred< Movy>>()
        for (item in listItems) {
            deferredMovies.add(
                viewModelScope.async(Dispatchers.IO) {
                    val response = mainApi.getMovie(item.id?.toInt(), "Bearer $token")
                    response
                }
            )
        }
        val allMovies = viewModelScope.async(Dispatchers.IO) {
            val movies = mutableListOf< Movy>()
            deferredMovies.forEach { deferredMovie ->
                movies.add(deferredMovie.await())
            }
            movies
        }
        viewModelScope.launch(Dispatchers.Main) {
            _moviesbookmark.value = allMovies.await()
        }
    }
}
