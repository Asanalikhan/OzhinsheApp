package com.example.ozhinshe.domain.models

data class MainPageResponceItem(
    val categoryId: Int,
    val categoryName: String,
    val movies: List<com.example.ozhinshe.domain.models.Movy>
)