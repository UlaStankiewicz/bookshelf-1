package com.bookshelf.api.book.response


import com.bookshelf.api.book.BookComment
import java.util.*


data class BookResponse(
    val id: UUID,
    val title: String,
    val author: String,
    val isbn: String,
    val numberOfPages: Int,
    val rating: Int,
    val comments: Set<BookComment>
)
