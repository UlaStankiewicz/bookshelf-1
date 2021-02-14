package com.bookshelf.api.book

import com.bookshelf.api.book.exception.BookNotFoundException
import com.bookshelf.api.book.request.CreateBookRequest
import com.bookshelf.api.book.request.UpdateBookRequest
import com.bookshelf.api.book.response.BookListResponse
import com.bookshelf.api.book.response.BookResponse
import com.bookshelf.api.common.PageInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.util.*

@WebMvcTest(BookController::class)
internal class BookControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    @MockkBean
    private lateinit var bookService: BookService

    @Test
    fun `call with proper page info data`() {
        every { bookService.allBooks(expectedPage) }.returns(pageToReturn)

        mockMvc.get(booksUrl)
            .andExpect {
                status { is2xxSuccessful() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.pageInfo.total") { value(pageToReturn.pageInfo.total) }
                jsonPath("$.pageInfo.currentPage") { value(expectedPage.pageNumber) }
                jsonPath("$.pageInfo.totalPages") { value(pageToReturn.pageInfo.totalPages) }
                jsonPath("$.books[0].id") { value(book.id.toString()) }
                jsonPath("$.books[0].title") { value(book.title) }
                jsonPath("$.books[0].author") { value(book.author) }
                jsonPath("$.books[0].isbn") { value(book.isbn) }
                jsonPath("$.books[0].rating") { value(book.rating) }
                jsonPath("$.books[0].numberOfPages") { value(book.numberOfPages) }
                jsonPath("$.books[0].comments") {
                    isEmpty()
                    isArray()
                }
            }
    }

    @Test
    fun `call service with proper data to save data`() {
        every { bookService.createBook(eq(createRequest)) }.returns(book)
        mockMvc.post(booksUrl) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(createRequest)
        }.andExpect {
            status { is2xxSuccessful() }
            header { string("Location", "$booksUrl/${book.id}") }
            content { string("") }

        }
        verify { bookService.createBook(eq(createRequest)) }
    }

    @Test
    fun `call service with proper data on update`() {
        every { bookService.updateBook(book.id!!, updateRequest) }.returns(Unit)

        mockMvc.put("$booksUrl/${book.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateRequest)
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    @Test
    fun `properly catch exception on update on not existing book`() {
        every { bookService.updateBook(book.id!!, updateRequest) }.throws(BookNotFoundException(book.id!!))

        mockMvc.put("$booksUrl/${book.id}") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(updateRequest)
        }.andExpect {
            status { isNotFound() }
            jsonPath("$.errorMessages.bookId") { value("Book with id: ${book.id!!} bot found") }
        }
    }

    @Test
    fun `properly call delete in service`() {
        every { bookService.deleteBook(book.id!!) }.returns(Unit)

        mockMvc.delete("$booksUrl/${book.id}")
            .andExpect {
                status { is2xxSuccessful() }
            }
    }

    companion object {
        const val booksUrl = "/books"
        val book = Book(
            id = UUID.randomUUID(),
            title = "Testing in Action",
            author = "Life",
            isbn = "ISBN",
            rating = 3,
            numberOfPages = 333
        )
        val createRequest = CreateBookRequest(
            title = book.title,
            author = book.author,
            isbn = book.isbn,
            rating = book.rating,
            numberOfPages = book.numberOfPages
        )
        val updateRequest = UpdateBookRequest(
            title = "updated title",
            author = "updated title",
            isbn = book.isbn,
            rating = book.rating,
            numberOfPages = book.numberOfPages
        )
        val expectedPage = PageRequest.of(0, 20)
        val pageToReturn = BookListResponse(
            pageInfo = PageInfo(total = 1, totalPages = 1, currentPage = 0),
            books = listOf(
                BookResponse(book.id!!, book.title, book.author, book.isbn,  book.numberOfPages, book.rating, setOf())
            )
        )
    }
}
