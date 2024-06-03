package com.example.ozhinshe.data
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)
    @Query("select * from favourites")
    fun getAllItem(): LiveData<List<Item>>
    @Delete
    fun deleteItem(item: Item)
}