package domain.user.repository

import com.lourenc.domain.user.model.User
import com.lourenc.presentation.user.dto.UserRequest
import com.lourenc.presentation.user.dto.UserResponse
import com.lourenc.presentation.user.dto.UserUpdateRequest
import java.util.UUID

interface UserRepository {
    fun create(request: UserRequest): UserResponse
    fun getAll(name: String?, email: String?): List<UserResponse>
    fun update(id: UUID, request: UserUpdateRequest): UserResponse
    fun delete(id: UUID): Boolean

}