package com.bookshelf.api.common

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target( AnnotationTarget.FIELD)
@Constraint(validatedBy = [IsbnValidator::class])
annotation class ISBN(
    val message: String = "It isn't ISBN",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],

    )

class IsbnValidator : ConstraintValidator<ISBN, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        return when {
            else -> true
        }
    }
}
