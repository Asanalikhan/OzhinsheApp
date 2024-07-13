package com.example.ozhinshe.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ozhinshe.domain.data.Item
import com.example.ozhinshe.domain.data.ItemRepository
import com.example.ozhinshe.domain.data.MainDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application): AndroidViewModel(application) {
    val getAllData: LiveData<List<Item>>
    private val repository: ItemRepository

    init {
        val itemDao = MainDB.getDb(application).getDao()
        repository = ItemRepository(itemDao)
        getAllData = repository.getAllData
    }
    fun addItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(item)
        }
    }
    fun deleteItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeItem(item)
        }
    }
    suspend fun getItem(item: Item): Boolean {
        return repository.getItem(item)
    }
}
