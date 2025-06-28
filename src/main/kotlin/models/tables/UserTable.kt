package com.lourenc.models.tables

import org.jetbrains.exposed.dao.id.UUIDTable

object UsersTable : UUIDTable("users") {
    val email = varchar("email", 255).uniqueIndex()
    val username = varchar("username", 255).uniqueIndex()
    val name = varchar("name", 255)
    val password = varchar("password",255 )
    val avatarUrl = varchar("avatarUrl", 500).nullable()

}