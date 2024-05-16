package com.example.ozhinshe.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class Item (
    @PrimaryKey(autoGenerate = true)
    var key: Int? = null,
    @ColumnInfo(name = "id")
    var id: Int?,
)