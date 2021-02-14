package com.bookshelf.api.common

data class ErrorResponse(
    val errorMessages: Map<String, String>
)
