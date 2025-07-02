package com.lourenc.application


import com.lourenc.data.user.datasource.local.tables.UsersTable
import org.jetbrains.exposed.sql.Database
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.lourenc.presentation.product.route.productRoutes
import com.lourenc.presentation.user.route.userRoutes
import com.lourenc.utils.UUIDSerializer
import data.product.datasource.local.tables.ProductsTable
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.addLogger

import java.util.UUID


fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8081,
        module = Application::module).start(wait = true)
}

fun Application.configureDatabase() {
    val dbConfig = environment.config.config("ktor.database")
    val url = dbConfig.property("url").getString()
    val driver = dbConfig.property("driver").getString()
    val user = dbConfig.property("user").getString()
    val password = dbConfig.property("password").getString()

    Database.connect(
        url = url,
        driver = driver,
        user = user,
        password = password
    )

    transaction {
        addLogger(Slf4jSqlDebugLogger)

        SchemaUtils.run {
            create(
                UsersTable,
                ProductsTable,

            )
        }
    }
}

fun Application.module() {
    configureDatabase()

    install(ContentNegotiation) {
        json(
            Json {
                serializersModule = SerializersModule {
                    contextual(UUID::class, UUIDSerializer)
                }
                ignoreUnknownKeys = true
            }
        )
    }

    routing {
        userRoutes()
        productRoutes()
        get("píng") {
            call.respondText("pong")
        }
    }

}
