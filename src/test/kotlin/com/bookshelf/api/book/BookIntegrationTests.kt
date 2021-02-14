package com.bookshelf.api.book

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.Rollback


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookIntegrationTests(
    @Autowired val bookRepository: BookRepository,
    @Autowired val bookCommentRepository: BookCommentRepository
    ) {

    @AfterEach
    fun clean() {
        bookRepository.deleteAll()
        bookCommentRepository.deleteAll()
    }

    @Test
    @Rollback(false)
    fun `get all books list with 5 comments`(@Autowired  restTemplate: TestRestTemplate){
        val savedBook = bookRepository.save(book)
        repeat(10) {
            bookCommentRepository.save(comment.copy(book = savedBook))
        }

        val response = restTemplate.getForEntity<String>("/books")

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    companion object {
        val book = Book(
            title = "Testing in Action",
            author = "Life",
            isbn = "ISBN",
            rating = 3,
            numberOfPages = 333
        )
        val comment = BookComment(
            book = book,
            text = "text"
        )
    }
}
