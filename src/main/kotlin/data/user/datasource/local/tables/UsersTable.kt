package com.lourenc.data.user.datasource.local.tables

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object UsersTable : UUIDTable("users") {
    val email = varchar("email", 255).uniqueIndex()
    val name = varchar("name", 255)
    val password = varchar("password",255 )
    val avatarUrl = varchar("avatarUrl", 500).nullable()
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)

}