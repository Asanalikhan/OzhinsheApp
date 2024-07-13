package com.example.ozhinshe.domain.models

data class SeasonResponseItem(
    val id: Int,
    val movieId: Int,
    val number: Int,
    val videos: List<com.example.ozhinshe.domain.models.Video>
)