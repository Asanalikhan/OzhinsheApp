package com.example.ozhinshe.modiedata

data class SeasonResponseItem(
    val id: Int,
    val movieId: Int,
    val number: Int,
    val videos: List<Video>
)