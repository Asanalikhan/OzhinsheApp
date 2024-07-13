package com.example.ozhinshe.domain.data

import android.util.Log
import androidx.lifecycle.LiveData

class ItemRepository(private val dao: Dao) {
    val getAllData: LiveData<List<Item>> = dao.getAllItem()

    suspend fun addItem(item: Item) {
        dao.insertItem(item)
    }
    suspend fun removeItem(item: Item) {
        val dbItem = item.id?.let { dao.getItemById(it) }
        if (dbItem != null) {
            dao.deleteItem(dbItem)
        } else {
            Log.e("ItemRepository", "Item not found in the database")
        }
    }
    suspend fun getItem(item: Item): Boolean {
        val dbItem = item.id?.let { dao.getItemById(it) }
        return dbItem != null
    }
}
