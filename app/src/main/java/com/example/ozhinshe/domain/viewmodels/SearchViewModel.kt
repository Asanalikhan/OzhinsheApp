package com.example.ozhinshe.domain.viewmodels

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.ozhinshe.domain.data.MainApi
import com.example.ozhinshe.domain.data.ServiceBuilder
import com.example.ozhinshe.domain.models.SearchResponce

class SearchViewModel(application: Application): AndroidViewModel(application) {

    private val mainApi = ServiceBuilder.buildService(MainApi::class.java)
    private val token = ServiceBuilder.getToken()

    private val _search = MutableLiveData<SearchResponce>()
    val search: LiveData<SearchResponce> get() = _search

    fun getMovie(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = mainApi.search(query, "{}", "{}", "{}", "Bearer $token")
                viewModelScope.launch(Dispatchers.Main) {
                    _search.value = response
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching search results: ${e.message}", e)
            }
        }
    }
}