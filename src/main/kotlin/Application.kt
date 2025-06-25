package com.lourenc

import com.lourenc.config.DataBaseConfig
import com.lourenc.models.tables.ListItemsTable
import com.lourenc.models.tables.ListsTable
import com.lourenc.models.tables.ProductsTable
import com.lourenc.models.tables.SharedListsTable
import com.lourenc.models.tables.UsersTable
import org.jetbrains.exposed.sql.Database
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


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
        SchemaUtils.run {
            create(
                UsersTable,
                ListsTable,
                ProductsTable,
                ListItemsTable,
                SharedListsTable
            )
        }
    }
}

fun Application.module() {

    DataBaseConfig.connect()

}
