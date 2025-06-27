package com.lourenc.models.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object SpecialListsTable : UUIDTable("special_lists") {
    val title = varchar("title", 255)
    val description = text("description").nullable()
    val imageUrl = text("image_url").nullable()
    val type = varchar("type", 50)
}