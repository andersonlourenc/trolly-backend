package presentation.user.mapper

import com.lourenc.domain.user.model.User
import com.lourenc.presentation.user.dto.UserRequest
import com.lourenc.presentation.user.dto.UserResponse
import com.lourenc.utils.hashPassword
import java.time.LocalDateTime
import java.util.UUID

fun UserRequest.toDomain(): User {
    return User(
        id = UUID.randomUUID(), // ou gere no repositório
        name = this.name,
        email = this.email,
        password = hashPassword(this.password),
        avatarUrl = this.avatarUrl,
        createdAt = LocalDateTime.now()


    )
}

fun User.toResponse(): UserResponse {
    return UserResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        avatarUrl = this.avatarUrl,
        createdAt = this.createdAt?.toString()



    )
}
