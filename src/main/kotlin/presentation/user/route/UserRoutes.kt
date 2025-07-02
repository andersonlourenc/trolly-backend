package com.lourenc.presentation.user.route


import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import presentation.user.controller.UserController


fun Route.userRoutes() {
    route("/users") {
        post { UserController.create(call) }
        get { UserController.getAll(call) }
        put("{id}") { UserController.update(call) }
        delete("{id}") { UserController.delete(call) }
    }
}