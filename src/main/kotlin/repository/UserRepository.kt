package com.lourenc.repository


import com.lourenc.models.domain.User
import com.lourenc.models.dto.ProductResponse
import org.mindrot.jbcrypt.BCrypt
import com.lourenc.models.dto.UserRequest
import com.lourenc.models.dto.UserResponse
import com.lourenc.models.dto.UserUpdateRequest
import com.lourenc.models.exceptions.BadRequestException
import com.lourenc.models.exceptions.ConflictException
import com.lourenc.models.tables.ProductsTable
import com.lourenc.models.tables.UsersTable
import com.lourenc.models.tables.UsersTable.avatarUrl
import com.lourenc.models.tables.UsersTable.email
import com.lourenc.models.tables.UsersTable.id
import com.lourenc.models.tables.UsersTable.name
import com.lourenc.models.tables.UsersTable.username
import io.ktor.client.request.request
import io.ktor.server.plugins.NotFoundException
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.neq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.UUID


object UserRepository {

    fun create(request: UserRequest): UserResponse = transaction {
        val emailExists = UsersTable.select { UsersTable.email eq request.email }.count() > 0
        if (emailExists) {
            throw IllegalArgumentException("Email already exists")
        }

        val usernameExists = UsersTable.select { UsersTable.username eq request.username }.count() > 0
        if (usernameExists) {
            throw IllegalArgumentException("Username already exists")
        }

        val newId = UUID.randomUUID()
        UsersTable.insert {
            it[id] = newId
            it[email] = request.email
            it[username] = request.username
            it[name] = request.name
            it[password] = hashPassword(request.password)
            it[avatarUrl] = request.avatarUrl
        }

        UserResponse(
            id = newId,
            name = request.name,
            username = request.username,
            email = request.email
        )
    }

    private fun hashPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())


    fun getAll(
        email: String? = null,
        username: String? = null,
        name: String? = null


    ): List<UserResponse> = transaction {
        var query = UsersTable.selectAll()

        if (!email.isNullOrBlank()) {
            query = query.andWhere { UsersTable.email like "%${email}%" }
        }
        if (!username.isNullOrBlank()) {
            query = query.andWhere { UsersTable.username like "%${username}%" }
        }
        if (!name.isNullOrBlank()) {
            query = query.andWhere { UsersTable.name like "%${name}%" }
        }


        query.map {
            UserResponse(
                id = it[UsersTable.id].value,
                username = it[UsersTable.username],
                name = it[UsersTable.name],
                email = it[UsersTable.email]
            )

        }
    }

    fun update(id: UUID, request: UserUpdateRequest): UserResponse = transaction {
        val existingUser = UsersTable.select { UsersTable.id eq id }.singleOrNull()
            ?: throw NotFoundException("User not found")

        request.email?.let {
            val emailExists = UsersTable.select {
                (UsersTable.email eq it) and (UsersTable.id neq id)
            }.count() > 0

            if (emailExists) throw ConflictException("Email already in use")
        }

        request.username?.let {
            val usernameExists = UsersTable.select {
                (UsersTable.username eq it) and (UsersTable.id eq id)
            }.count() > 0

            if (usernameExists) throw ConflictException("Username already in use")
        }

        UsersTable.update({ UsersTable.id eq id }) {
            request.email?.let { newEmail -> it[email] = newEmail }
            request.username?.let { newUsername -> it[username] = newUsername }
            request.name?.let { newName -> it[name] = newName }
            request.password?.let { newPassword -> it[password] = hashPassword(newPassword) }
        }

        val updated = UsersTable.select { UsersTable.id eq id }.single()

        UserResponse(
            id = updated[UsersTable.id].value,
            email = updated[UsersTable.email],
            username = updated[UsersTable.username],
            name = updated[UsersTable.name]
        )
    }

    fun delete(id: UUID): Boolean = transaction {
        val deleteCount = UsersTable.deleteWhere { UsersTable.id eq id }
        deleteCount > 0
    }
}










