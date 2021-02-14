package com.bookshelf.api.common

data class PageInfo(
    val total: Long,
    val currentPage: Int,
    val totalPages: Int
)
