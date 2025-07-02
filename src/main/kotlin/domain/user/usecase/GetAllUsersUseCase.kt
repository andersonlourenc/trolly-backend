package com.lourenc.domain.user.usecase

import com.lourenc.presentation.user.dto.UserResponse
import data.user.repository.UserRepositoryImpl
import domain.user.repository.UserRepository


class GetAllUsersUseCase(private val userRepository: UserRepository) {
    fun execute(email: String?, name: String?): List<UserResponse> {
        return userRepository.getAll(email, name)
    }
}
