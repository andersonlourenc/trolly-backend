package com.lourenc.models.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table

object ListItemsTable : UUIDTable("list_items") {
    val list = reference("list_id", ListsTable)
    val product = reference("product_id", ProductsTable)
    val quantity = float("quantity")
    val brand = varchar("brand", 255).nullable()
    val price = varchar("price", 255).nullable()
}