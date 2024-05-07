package com.example.ozhinshe.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class MainDB: RoomDatabase() {

    companion object{
        fun getDb(context: Context): MainDB{
            return Room.databaseBuilder(context.applicationContext, MainDB::class.java, "favourite.db").build()
        }
    }

}