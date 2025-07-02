package data.product.datasource.local.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object ProductsTable : UUIDTable("products") {
    val name = varchar("name", 255)
    val category = varchar("category", 255).nullable()
    val unit = varchar("unit", 20)
    val imageUrl = varchar("imageUrl", 255).nullable()
}