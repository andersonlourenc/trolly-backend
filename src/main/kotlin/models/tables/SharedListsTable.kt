package com.lourenc.models.tables

import org.jetbrains.exposed.sql.Table

object SharedListsTable : Table("shared_lists") {
    val list = reference("list_id", ListsTable)
    val user = reference("user_id", UsersTable)
    override val primaryKey = PrimaryKey(list, user)

}