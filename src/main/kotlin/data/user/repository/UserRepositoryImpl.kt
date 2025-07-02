package data.user.repository

import com.lourenc.data.user.datasource.local.tables.UsersTable
import com.lourenc.exceptions.ConflictException
import com.lourenc.presentation.user.dto.UserRequest
import com.lourenc.presentation.user.dto.UserResponse
import com.lourenc.presentation.user.dto.UserUpdateRequest
import com.lourenc.utils.ErrorMessages.EMAIL_AREADY_EXISTS
import domain.user.repository.UserRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.mindrot.jbcrypt.BCrypt
import java.util.UUID

class UserRepositoryImpl : UserRepository {

    override fun create(request: UserRequest): UserResponse = transaction {
        val emailExists = UsersTable.select { UsersTable.email eq request.email }.count() > 0
        if (emailExists) {
            throw IllegalArgumentException(EMAIL_AREADY_EXISTS)
        }


        val newId = UUID.randomUUID()
        UsersTable.insert {
            it[id] = newId
            it[email] = request.email
            it[name] = request.name
            it[password] = hashPassword(request.password)
            it[avatarUrl] = request.avatarUrl


        }
        val inserted = UsersTable.select { UsersTable.id eq newId }.single()

        UserResponse(
            id = inserted[UsersTable.id].value,
            email = inserted[UsersTable.email],
            name = inserted[UsersTable.name],
            avatarUrl = inserted[UsersTable.avatarUrl],
            createdAt = inserted[UsersTable.createdAt].toString()

        )
    }

    private fun hashPassword(password: String): String = BCrypt.hashpw(password,
        BCrypt.gensalt())


    override fun getAll(
        email: String?,
        name: String?
    ): List<UserResponse> = transaction {
        var query = UsersTable.selectAll()

        if (!email.isNullOrBlank()) {
            query = query.andWhere { UsersTable.email like "%${email}%" }
        }
        if (!name.isNullOrBlank()) {
            query = query.andWhere { UsersTable.name like "%${name}%" }
        }


        query.map {
            UserResponse(
                id = it[UsersTable.id].value,
                name = it[UsersTable.name],
                email = it[UsersTable.email],
                avatarUrl = it[UsersTable.avatarUrl],
                createdAt = it[UsersTable.createdAt].toString()
            )

        }
    }

    override fun update(id: UUID, request: UserUpdateRequest): UserResponse = transaction {

        request.email?.let {
            val emailExists = UsersTable.select {
                (UsersTable.email eq it) and (UsersTable.id neq id)
            }.count() > 0

            if (emailExists) throw ConflictException(EMAIL_AREADY_EXISTS)
        }

        UsersTable.update({ UsersTable.id eq id }) {
            request.email?.let { newEmail -> it[email] = newEmail }
            request.name?.let { newName -> it[name] = newName }
            request.password?.let { newPassword -> it[password] = hashPassword(newPassword) }
        }

        val updated = UsersTable.select { UsersTable.id eq id }.single()

        UserResponse(
            id = updated[UsersTable.id].value,
            email = updated[UsersTable.email],
            name = updated[UsersTable.name],
            avatarUrl = updated[UsersTable.avatarUrl],
            createdAt = updated[UsersTable.createdAt].toString()
        )
    }

   override fun delete(id: UUID): Boolean = transaction {
        val deleteCount = UsersTable.deleteWhere { UsersTable.id eq id }
        deleteCount > 0
    }
}