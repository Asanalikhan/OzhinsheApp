package com.example.ozhinshe.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class MainDB: RoomDatabase() {

    abstract fun getDao(): Dao
    companion object {
        @Volatile
        private var INSTANCE: MainDB? = null
        fun getDb(context: Context): MainDB{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDB::class.java,
                    "favourites"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}