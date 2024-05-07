package com.example.ozhinshe.data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    fun insertItem(item: Item)
    @Query("Select * from favourites")
    fun getAllItem(): Flow<List<Item>>
    @Delete
    fun deleteItem(item: Item)
}