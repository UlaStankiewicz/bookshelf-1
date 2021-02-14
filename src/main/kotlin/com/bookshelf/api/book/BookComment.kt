package com.bookshelf.api.book

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table
data class BookComment(
    @Id @GeneratedValue val id: UUID? = null,
    val text: String,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnore
    val book: Book,
    @CreatedDate val createdDate: LocalDateTime? = LocalDateTime.now()
)
