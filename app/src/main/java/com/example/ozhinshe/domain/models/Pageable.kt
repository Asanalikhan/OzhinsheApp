package com.example.ozhinshe.domain.models

data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: com.example.ozhinshe.domain.models.SortX,
    val unpaged: Boolean
)