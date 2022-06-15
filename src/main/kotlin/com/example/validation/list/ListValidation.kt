package com.example.validation.list

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Valid
import org.hibernate.validator.constraints.Length
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@Validated
@RequestMapping("/list")
class ListValidationController {

    @PostMapping
    fun validateListObjects(@RequestBody @Valid listRequest: List<ListRequest>): String =
            "list success"

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(e: ConstraintViolationException): String =
         e.message ?: "Validation failed"

}

data class ListRequest(
        @field:Length(min = 2, max = 4, message = "name must be 2 to 4 chars")
        val name: String = ""
)


