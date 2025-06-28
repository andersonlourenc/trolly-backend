package com.lourenc.routes

import com.lourenc.application.UserService

import com.lourenc.models.dto.UserRequest
import com.lourenc.models.dto.UserUpdateRequest
import com.lourenc.models.exceptions.BadRequestException
import com.lourenc.models.exceptions.ConflictException
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

import java.lang.Exception
import java.util.UUID

fun Route.userRoutes() {
    route("/users") {

        post {
            try {
                val request = call.receive<UserRequest>()
                val user = UserService.create(request)
                call.respond(HttpStatusCode.Created, user)
            } catch (e: ConflictException) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Conflict")
            } catch (e: BadRequestException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Bad Request")
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.InternalServerError, e.message ?: "Unexpected error")
            }
        }

        get {
            val email = call.request.queryParameters["email"]
            val username = call.request.queryParameters["username"]
            val name = call.request.queryParameters["name"]


            try {
                val users = UserService.getAll(email, username, name)
                call.respond(users)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.InternalServerError, e.message ?: "Erro ao encontrar usuário")
            }
        }

        put("{id}") {
            try {
                val id = UUID.fromString(call.parameters["id"])
                val request = call.receive< UserUpdateRequest>()
                val updated = UserService.update(id, request)
                call.respond(HttpStatusCode.OK, updated)
            } catch (e: NotFoundException) {
                call.respond(HttpStatusCode.NotFound, e.message ?: "User not found")
            } catch (e: ConflictException) {
                call.respond(HttpStatusCode.Conflict, e.message ?: "Conflict")
            } catch (e: BadRequestException) {
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Invalid request")
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.InternalServerError, e.message ?: "Unexpected error")
            }
        }

        delete("{id}") {
            val idParam = call.parameters["id"]

            val userId = try {
                UUID.fromString(idParam)
            } catch (e: IllegalArgumentException) {
                return@delete call.respond(HttpStatusCode.BadRequest, e.message ?: "Invalid UUID format")
            }

            try {
                UserService.deleteUser(userId)
                call.respond(HttpStatusCode.NoContent)
            } catch (e: NotFoundException) {
                call.respond(HttpStatusCode.NotFound, e.message ?: "User not found")
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.InternalServerError, e.message ?: "Internal server error")
            }
        }
    }
}