package com.lourenc.domain.user.usecase

import com.lourenc.presentation.user.dto.UserUpdateRequest
import com.lourenc.presentation.user.dto.UserResponse
import com.lourenc.domain.user.validation.UserValidator
import domain.user.repository.UserRepository
import java.util.UUID

class UpdateUserUseCase(private val userRepository: UserRepository) {
    fun execute(id: UUID, request: UserUpdateRequest): UserResponse {
        UserValidator.validateUpdate(request)
        return userRepository.update(id, request)
    }
}
