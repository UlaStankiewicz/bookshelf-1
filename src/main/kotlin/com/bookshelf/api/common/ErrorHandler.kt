package com.bookshelf.api.common

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleValidationError(ex: MethodArgumentNotValidException): ErrorResponse {
        val errors = ex.bindingResult
            .fieldErrors
            .map { it.field to it.defaultMessage!! }
            .toMap()

        return ErrorResponse(errorMessages = errors)
    }
}
