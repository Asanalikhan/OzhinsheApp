package com.example.ozhinshe.data

import com.example.ozhinshe.modiedata.Movy

data class MainPageResponceItem(
    val categoryId: Int,
    val categoryName: String,
    val movies: List<Movy>
)