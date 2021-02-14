package com.bookshelf.api.book

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.*

interface BookRepository : JpaRepository<Book, UUID> {
}
