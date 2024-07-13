package com.example.ozhinshe.domain.models

data class DescMovies(
    val content: List<com.example.ozhinshe.domain.models.Movy>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: com.example.ozhinshe.domain.models.Pageable,
    val size: Int,
    val sort: com.example.ozhinshe.domain.models.SortX,
    val totalElements: Int,
    val totalPages: Int
)