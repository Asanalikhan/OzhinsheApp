package com.example.ozhinshe.domain.models

data class ContentX(
    val categories: List<com.example.ozhinshe.domain.models.Category>,
    val categoryAges: List<com.example.ozhinshe.domain.models.CategoryAge>,
    val createdDate: String,
    val description: String,
    val director: String,
    val favorite: Boolean,
    val genres: List<com.example.ozhinshe.domain.models.Genre>,
    val id: Int,
    val keyWords: String,
    val lastModifiedDate: String,
    val movieType: String,
    val name: String,
    val poster: com.example.ozhinshe.domain.models.Poster,
    val producer: String,
    val screenshots: List<com.example.ozhinshe.domain.models.Screenshot>,
    val seasonCount: Int,
    val seriesCount: Int,
    val timing: Int,
    val trend: Boolean,
    val video: com.example.ozhinshe.domain.models.Video,
    val watchCount: Int,
    val year: Int
)