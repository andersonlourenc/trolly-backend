package presentation.user.controller

import com.lourenc.presentation.user.dto.UserRequest
import com.lourenc.presentation.user.dto.UserUpdateRequest
import com.lourenc.utils.ErrorMessages.ERROR_DELETING_USERS
import com.lourenc.utils.ErrorMessages.ID_REQUIRED
import com.lourenc.utils.ErrorMessages.INVALID_UUID
import com.lourenc.utils.ErrorMessages.USER_NOT_FOUND
import di.UserModule
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import java.util.UUID


object UserController {

    suspend fun create(call: ApplicationCall) {
        try {
            val request = call.receive<UserRequest>()
            val user = UserModule.createUserUseCase.execute(request)
            call.respond(HttpStatusCode.Created, user)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
        }
    }


    suspend fun getAll(call: ApplicationCall) {
        try {
            val email = call.request.queryParameters["email"]
            val name = call.request.queryParameters["name"]
            val users = UserModule.getAllUsersUseCase.execute(email, name)
            call.respond(HttpStatusCode.OK, users)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to USER_NOT_FOUND))
        }

    }

    suspend fun update(call: ApplicationCall) {
        try {
            val id = call.parameters["id"] ?: return call.respond(
                HttpStatusCode.BadRequest, mapOf("error " to ID_REQUIRED)
            )
            val uuid = runCatching { UUID.fromString(id) }.getOrElse {
                return call.respond(HttpStatusCode.BadRequest, mapOf("error " to INVALID_UUID))
            }
            val request = call.receive<UserUpdateRequest>()
            val updatedUser = UserModule.updateUserUseCase.execute(uuid, request)
            call.respond(HttpStatusCode.OK, updatedUser)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, mapOf("error " to e.message))
        }

    }
        suspend fun delete(call: ApplicationCall) {
            try {
                val id = call.parameters["id"] ?: return call.respond(
                    HttpStatusCode.BadRequest, mapOf("error " to ID_REQUIRED)
                )
                val uuid = runCatching { UUID.fromString(id) }.getOrElse {
                    return call.respond(HttpStatusCode.BadRequest, mapOf("error " to INVALID_UUID))
                }

                UserModule.deleteUserUseCase.execute(uuid)
                    call.respond(HttpStatusCode.NoContent)
                }  catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error " to ERROR_DELETING_USERS))
            }
        }
    }
