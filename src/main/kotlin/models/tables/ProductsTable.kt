package com.lourenc.models.tables


import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table
import java.util.UUID


object ProductsTable : UUIDTable("products") {
    val name = varchar("name", 255)
    val category = varchar("category", 255).nullable()
    val unit = varchar("unit", 20)
    val imageUrl = varchar("image_url", 512).nullable()


    init {
        uniqueIndex(name, category, unit)
    }

}