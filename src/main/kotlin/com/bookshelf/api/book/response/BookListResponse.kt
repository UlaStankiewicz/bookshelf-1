package com.bookshelf.api.book.response

import com.bookshelf.api.common.PageInfo

data class BookListResponse(
    val pageInfo: PageInfo,
    val books: List<BookResponse>
)
