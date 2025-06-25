package com.lourenc.config

import com.lourenc.models.tables.ListItemsTable
import com.lourenc.models.tables.ListsTable
import com.lourenc.models.tables.ProductsTable
import com.lourenc.models.tables.SharedListsTable
import com.lourenc.models.tables.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DataBaseConfig {
    fun connect() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/trolly",
            driver = "org.postgresql.Driver",
            user = "admin",
            password = "admin123"
        )

        transaction {
            SchemaUtils.create(
                UsersTable,
                ListsTable,
                SharedListsTable,
                ProductsTable,
                ListItemsTable
            )
        }
    }
}