package com.bookshelf.api.book.exception

import java.util.*

class BookNotFoundException(bookId: UUID) : RuntimeException("Book with id: $bookId bot found")
