package com.lourenc.domain.user.validation

import com.lourenc.presentation.user.dto.UserRequest
import com.lourenc.presentation.user.dto.UserUpdateRequest
import com.lourenc.utils.ErrorMessages.INVALID_EMAIL
import com.lourenc.utils.ErrorMessages.INVALID_PASSWORD_8
import com.lourenc.utils.ErrorMessages.INVALID_PASSWORD_DIGIT
import com.lourenc.utils.ErrorMessages.INVALID_PASSWORD_UPPERCASE
import com.lourenc.utils.ErrorMessages.NAME_CANNOT_BE_BLACK
import io.ktor.server.plugins.BadRequestException

object UserValidator {

    fun validateCreate(request: UserRequest) {
        if (request.name.isBlank()) throw BadRequestException(NAME_CANNOT_BE_BLACK)

        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        if (request.email.isBlank() || !request.email.matches(emailRegex))
            throw BadRequestException(INVALID_EMAIL)

        if (request.password.length < 8)
            throw BadRequestException(INVALID_PASSWORD_8)

        if (!request.password.any { it.isUpperCase() })
            throw BadRequestException(INVALID_PASSWORD_UPPERCASE)
        if (!request.password.any { it.isDigit() })
            throw BadRequestException(INVALID_PASSWORD_DIGIT)
    }

    fun validateUpdate(request: UserUpdateRequest) {
        request.email?.let {
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
            if (!it.matches(emailRegex)) throw BadRequestException(INVALID_EMAIL)
        }

        request.password?.let {
            if (it.length < 8)
                throw BadRequestException(INVALID_PASSWORD_8)
            if (!it.any { c -> c.isUpperCase() })
                throw BadRequestException(INVALID_PASSWORD_UPPERCASE)
            if (!it.any { c -> c.isDigit() })
                throw BadRequestException(INVALID_PASSWORD_DIGIT)
        }
    }
}
