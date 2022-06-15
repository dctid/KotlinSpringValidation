package com.example.validation.custom

import jakarta.validation.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.KClass

@RestController
@RequestMapping("/custom")
class CustomValidationController {

    @PostMapping
    fun validateListObjects(@RequestBody @Valid customRequest: CustomRequest): String =
        "custom success"
}

data class CustomRequest(
    @field:NullOrProperty(message = "Cannot be blank")
    val value: String? = ""
)

@Target(AnnotationTarget.FIELD)
@MustBeDocumented
@Constraint(validatedBy = [NullOrPropertyValidator::class])
annotation class NullOrProperty(
    val message: String = "{javax.validation.constraints.NotBlank.message}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)


@Service
class CustomValidationService(
    @Value("\${validation.request.value:defaultValue}")
    val validationValue: String
) {
    fun validateInTheService(value: String?): Boolean =
        if (value == null)
            true
        else
            value == validationValue
}


class NullOrPropertyValidator(
    @Autowired
    private val customValidationService: CustomValidationService
) : ConstraintValidator<NullOrProperty, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean =
        customValidationService.validateInTheService(value)
}
