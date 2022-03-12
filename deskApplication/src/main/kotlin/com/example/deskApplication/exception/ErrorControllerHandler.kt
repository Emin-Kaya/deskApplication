package com.example.deskApplication.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class ErrorControllerHandler {

    @ExceptionHandler(EntryNotFoundException::class)
    fun handleEntryNotFoundException(ex: EntryNotFoundException): ResponseEntity<*> =
        ResponseEntity(
            ErrorMessageDTO(
                ex.message
            ),
            HttpStatus.NOT_FOUND
        )

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(ex: MethodArgumentTypeMismatchException): ResponseEntity<*> =
        ResponseEntity(
            ErrorMessageDTO(
                "Please check your input parameter"
            ),
            HttpStatus.BAD_REQUEST
        )

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): ResponseEntity<*> =
        ResponseEntity(
            ErrorMessageDTO(
                "Please check your input"
            ),
            HttpStatus.BAD_REQUEST
        )

}

data class ErrorMessageDTO(
    val message: String?
)