package com.lourenc.models.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object ListsTable : UUIDTable("lists") {
    val owner = reference("owner_id", UsersTable)
    val title = varchar("title", 255)
    val description = text("description").nullable()
    val imageUrl = varchar("imageUrl", 255).nullable()
    val createdAt = datetime("created_at")
    val budget = decimal("budget", 10, 2).nullable()
}
