package com.lourenc.domain.user.usecase

import com.lourenc.presentation.user.dto.UserRequest
import com.lourenc.presentation.user.dto.UserResponse
import com.lourenc.domain.user.validation.UserValidator

import domain.user.repository.UserRepository

class CreateUserUseCase(private val userRepository: UserRepository) {

    fun execute(request: UserRequest): UserResponse {
        UserValidator.validateCreate(request)
        return userRepository.create(request)
    }
}
