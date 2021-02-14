package com.bookshelf.api.book

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookCommentRepository : JpaRepository<BookComment, UUID> {
    fun findTop5BookCommentByBookOrderByCreatedDateDesc(book: Book): Set<BookComment>
}
