package com.lourenc.routes

import com.lourenc.application.ProductService
import com.lourenc.models.domain.Product
import com.lourenc.models.dto.ProductRequest
import com.lourenc.repository.ProductRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import java.util.UUID


fun Route.productRoutes() {
    route("/products") {

        put("{id}") {
            val idParam = call.parameters["id"]
            if (idParam == null) {
                call.respond(HttpStatusCode.BadRequest, "ID não informado")
                return@put
            }

            val id = UUID.fromString(idParam)
            val request = call.receive<ProductRequest>()

            val updatedProduct = ProductRepository.update(id, request)
            call.respond(updatedProduct)
        }

        get {
            val name = call.request.queryParameters["name"]
            val category = call.request.queryParameters["category"]
            val unit = call.request.queryParameters["unit"]

            try {
                val products = ProductService.getAll(name, category, unit)
                call.respond(products)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.InternalServerError,
                    "Erro ao buscar produtos"
                )
            }
        }

        post {
            try {
                val request = call.receive<ProductRequest>()
                val product = ProductService.createOrGetProduct(request)
                call.respond(HttpStatusCode.Created, product)
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.InternalServerError,
                    "Erro ao buscar porduto")
            }
        }
    }
}