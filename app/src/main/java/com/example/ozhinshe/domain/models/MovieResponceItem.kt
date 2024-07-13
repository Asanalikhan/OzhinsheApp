package com.example.ozhinshe.domain.models

data class MovieResponceItem(
    val categoryId: Int,
    val categoryName: String,
    val movies: List<com.example.ozhinshe.domain.models.Movy>
)