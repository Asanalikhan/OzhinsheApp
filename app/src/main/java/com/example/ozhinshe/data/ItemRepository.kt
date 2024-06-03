package com.example.ozhinshe.data

import androidx.lifecycle.LiveData

class ItemRepository(private val dao: Dao) {

    val getAllData: LiveData<List<Item>> = dao.getAllItem()

    suspend fun addItem(item: Item){
        dao.insertItem(item)
    }
}