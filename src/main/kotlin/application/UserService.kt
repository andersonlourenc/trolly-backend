package com.lourenc.application


import com.lourenc.models.dto.UserRequest
import com.lourenc.models.dto.UserResponse
import com.lourenc.repository.UserRepository
import io.ktor.server.plugins.BadRequestException
import com.lourenc.models.domain.User
import com.lourenc.models.dto.UserUpdateRequest
import com.lourenc.models.tables.UsersTable.password
import io.ktor.server.plugins.NotFoundException
import java.util.UUID


object UserService {

    fun deleteUser(UserId: UUID) {
        val deleted = UserRepository.delete(UserId)
        if (!deleted) {
            throw NotFoundException("User with ID ${UserId} not found")
        }
    }


    fun update(id: UUID, request: UserUpdateRequest): UserResponse {
        validateUpdateRequest(request)
        return UserRepository.update(id, request)
    }
    private fun validateUpdateRequest(request: UserUpdateRequest) {
        request.email?.let {
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
            if (!it.matches(emailRegex)) throw BadRequestException("Invalid email format")
        }

        request.password?.let {
            if (it.length < 8) throw BadRequestException("Password must be at least 8 characters")
            if (!it.any { c -> c.isUpperCase() }) throw BadRequestException("Password must contain an uppercase letter")
            if (!it.any { c -> c.isDigit() }) throw BadRequestException("Password must contain a digit")
        }
    }

    fun getAll(
        email: String? = null,
        username: String? = null,
        name: String? = null
    ): List<UserResponse> {
        return UserRepository.getAll(email, username, name).map {
            UserResponse(
                id = it.id,
                name = it.name,
                username = it.username,
                email = it.email,

            )
        }
    }

    fun create(request: UserRequest): UserResponse {
        validateRequest(request)

        return UserRepository.create(request)
}

    private fun validateRequest(request: UserRequest) {
        if (request.name.isBlank()) throw BadRequestException("Name cannot be blank")
        if (request.username.isBlank())
            throw BadRequestException("Username cannot be blank")

        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$".toRegex()
        if (request.email.isBlank() || !request.email.matches(emailRegex))
            throw BadRequestException("Invalid email format")

        if (request.password.length < 8)
            throw BadRequestException("Password must be 8 characters long")
        if (!request.password.any { it.isUpperCase() })
            throw BadRequestException("Password must contain at least one uppercase letter")
        if (!request.password.any { it.isDigit() })
            throw BadRequestException("Password must contain at least one special character")


    }

}


