package com.lourenc.domain.user.usecase


import io.ktor.server.plugins.NotFoundException
import com.lourenc.utils.ErrorMessages
import data.user.repository.UserRepositoryImpl
import java.util.UUID

class DeleteUserUseCase(private val userRepository: UserRepositoryImpl) {
    fun execute(userId: UUID) {
        val deleted = userRepository.delete(userId)
        if (!deleted) {
            throw NotFoundException(ErrorMessages.USER_NOT_FOUND)

        }
    }
}
