package com.bookshelf.api.book

import com.bookshelf.api.book.exception.BookNotFoundException
import com.bookshelf.api.book.request.CreateBookCommentRequest
import com.bookshelf.api.book.request.CreateBookRequest
import com.bookshelf.api.book.request.UpdateBookRequest
import com.bookshelf.api.book.response.BookListResponse
import com.bookshelf.api.common.ErrorResponse
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping(path = ["/books"])
class BookController(private val bookService: BookService) {

    @GetMapping()
    fun listBooks(@PageableDefault(size = 20) page: Pageable): BookListResponse =
        bookService.allBooks(page)


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody @Validated bookData: CreateBookRequest): ResponseEntity<Unit> {
        val book = bookService.createBook(bookData)

        return ResponseEntity.created(URI.create("/books/${book.id}")).build()
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateBook(
        @PathVariable bookId: UUID,
        @RequestBody @Validated bookData: UpdateBookRequest
    ) {
        bookService.updateBook(bookId, bookData)
    }

    @DeleteMapping("/{bookId}")
    fun deleteBook(@PathVariable bookId: UUID) {
        bookService.deleteBook(bookId)
    }

    @PostMapping("/{bookId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    fun addComment(
        @PathVariable bookId: UUID,
        @RequestBody @Validated commentData: CreateBookCommentRequest
    ) {
        bookService.addComment(bookId, commentData)
    }

    @ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundHandler(exception: BookNotFoundException): ErrorResponse =
        ErrorResponse(mapOf("bookId" to exception.localizedMessage))

}
