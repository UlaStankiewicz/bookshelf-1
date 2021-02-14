package com.bookshelf.api.book

import com.bookshelf.api.book.exception.BookNotFoundException
import com.bookshelf.api.book.request.CreateBookCommentRequest
import com.bookshelf.api.book.request.CreateBookRequest
import com.bookshelf.api.book.request.UpdateBookRequest
import com.bookshelf.api.book.response.BookListResponse
import com.bookshelf.api.book.response.BookResponse
import com.bookshelf.api.common.PageInfo
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val commentRepository: BookCommentRepository
) {

    @Transactional
    fun allBooks(page: Pageable): BookListResponse {
        val books = bookRepository.findAll(page)

        return BookListResponse(
            pageInfo = PageInfo(books.totalElements, page.pageNumber, books.totalPages),
            books = books.content.map {
                BookResponse(
                    it.id!!,
                    it.title,
                    it.author,
                    it.isbn,
                    it.numberOfPages,
                    it.rating,
                    commentRepository.findTop5BookCommentByBookOrderByCreatedDateDesc(it)
                )
            }
        )
    }

    @Transactional
    fun createBook(bookData: CreateBookRequest): Book = bookRepository.save(bookData.toBook())

    @Transactional
    fun deleteBook(bookId: UUID) = bookRepository.deleteById(bookId)

    @Transactional
    fun updateBook(bookId: UUID, bookData: UpdateBookRequest) {
        val book = bookRepository.findByIdOrNull(bookId) ?: throw BookNotFoundException(bookId)

        bookRepository.save(book.updateData(bookData))
    }

    @Transactional
    fun addComment(bookId: UUID, commentData: CreateBookCommentRequest) {
        val book = bookRepository.findByIdOrNull(bookId) ?: throw BookNotFoundException(bookId)

        commentRepository.save(BookComment(text = commentData.text, book = book))
    }

}

private fun CreateBookRequest.toBook(): Book = Book(
    title = this.title,
    author = this.author,
    isbn = this.isbn,
    numberOfPages = this.numberOfPages,
    rating = this.rating
)

private fun Book.updateData(bookData: UpdateBookRequest): Book = this.copy(
    title = bookData.title,
    author = bookData.author,
    numberOfPages = numberOfPages,
    rating = bookData.rating,
    isbn = bookData.isbn
)

