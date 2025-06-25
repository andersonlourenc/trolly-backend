package com.lourenc.models.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table

object ProductsTable : UUIDTable("products") {
    val name = varchar("name", 255)
    val category = varchar("category", 255)
    val unit = varchar("unit", 255)
}