package com.bookshelf.api.book.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class UpdateBookRequest(
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val author: String,
    @field:NotBlank
    val isbn: String,
    @field:Positive
    val numberOfPages: Int,
    @field:Min(0)
    @field:Max(5)
    val rating: Int,
)
