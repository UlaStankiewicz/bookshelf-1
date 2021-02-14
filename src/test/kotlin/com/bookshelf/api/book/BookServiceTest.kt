package com.bookshelf.api.book

import com.bookshelf.api.book.response.BookListResponse
import com.bookshelf.api.book.response.BookResponse
import com.bookshelf.api.common.PageInfo
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockKExtension::class)
internal class BookServiceTest() {
    @MockK
    lateinit var bookRepository: BookRepository;
    @MockK
    lateinit var bookCommentRepository: BookCommentRepository;
    @InjectMockKs
    lateinit var bookService: BookService;

    @Test
    fun `find all books get also comments for found book`() {
        val books = listOf(book)
        val booksResponseList = listOf(bookResponse)
        val page = PageRequest.of(0, 20)
        val booksPage = PageImpl(books, page, 0)
        val pageInfo = PageInfo(books.size.toLong(), page.pageNumber, 1)
        val expectedPage = BookListResponse(pageInfo, booksResponseList)

        every { bookRepository.findAll(page) } returns booksPage
        every { bookCommentRepository.findTop5BookCommentByBookOrderByCreatedDateDesc(book) } returns comments

        assertThat(bookService.allBooks(page)).isEqualTo(expectedPage)
    }

    companion object {
        val book = Book(
            id = UUID.randomUUID(),
            title = "Testing in Action",
            author = "Life",
            isbn = "ISBN",
            rating = 3,
            numberOfPages = 333
        )
        val comments = setOf(
            BookComment(book = book, text = "Comment", id = UUID.randomUUID(), createdDate = LocalDateTime.now())
        )
        val bookResponse = BookResponse(
            id = book.id!!,
            title = book.title,
            isbn = book.isbn,
            rating = book.rating,
            numberOfPages = book.numberOfPages,
            author = book.author,
            comments = comments
        )
    }
}
