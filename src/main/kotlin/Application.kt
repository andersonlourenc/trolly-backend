package com.lourenc

import com.lourenc.config.DataBaseConfig
import com.lourenc.models.tables.ListItemsTable
import com.lourenc.models.tables.ListsTable
import com.lourenc.models.tables.ProductsTable
import com.lourenc.models.tables.SharedListsTable
import com.lourenc.models.tables.SpecialListsTable
import com.lourenc.models.tables.UsersTable
import org.jetbrains.exposed.sql.Database
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.lourenc.routes.productRoutes
import com.lourenc.serialization.UUIDSerializer
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing
import kotlinx.serialization.ContextualSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.addLogger

import java.util.UUID


fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080,
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
                ListsTable,
                ProductsTable,
                ListItemsTable,
                SharedListsTable,
                SpecialListsTable
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

    routing { productRoutes() }

}
