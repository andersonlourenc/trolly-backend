package com.lourenc.models.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption


object ListItemsTable : UUIDTable("list_items") {
    val list = reference("list_id", ListsTable, onDelete = ReferenceOption.CASCADE)
    val product = reference("product_id", ProductsTable, onDelete = ReferenceOption.CASCADE)
    val quantity = float("quantity")
    val brand = varchar("brand", 255).nullable()
    val price = varchar("price", 255).nullable()
}