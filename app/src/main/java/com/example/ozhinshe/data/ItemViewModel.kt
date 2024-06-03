package com.example.ozhinshe.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application): AndroidViewModel(application) {
    private val getAllData: LiveData<List<Item>>
    private val repository: ItemRepository

    init{
        val itemDao = MainDB.getDb(application).getDao()
        repository = ItemRepository(itemDao)
        getAllData = repository.getAllData
    }

    fun addItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(item)
        }
    }
}