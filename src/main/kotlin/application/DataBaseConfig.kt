package com.lourenc.app


import com.lourenc.data.user.datasource.local.tables.UsersTable
import data.product.datasource.local.tables.ProductsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DataBaseConfig {
    fun connect() {
        Database.Companion.connect(
            url = "jdbc:postgresql://localhost:5432/trolly",
            driver = "org.postgresql.Driver",
            user = "admin",
            password = "admin123"
        )

        transaction {
            SchemaUtils.create(
                UsersTable,
                ProductsTable,

            )
        }
    }
}