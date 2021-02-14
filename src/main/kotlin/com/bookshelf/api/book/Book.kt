package com.bookshelf.api.book

import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.util.*
import javax.persistence.*

@Entity
@Table
data class Book(
    @Id @GeneratedValue val id: UUID? = null,
    val title: String,
    val author: String,
    val isbn: String,
    val numberOfPages: Int,
    val rating: Int,
    @OneToMany(
        mappedBy = "book",
        fetch = FetchType.LAZY
    )
    val comments: List<BookComment> = listOf()
)
